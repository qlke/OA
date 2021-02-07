package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.AssetUseRecord;
import com.evistek.oa.entity.Asset;
import com.evistek.oa.entity.Employee;
import com.evistek.oa.service.*;
import com.evistek.oa.utils.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/11
 */
@RestController
@RequestMapping("/api/v1")
public class AssetApi {
    private AssetService assetService;
    private AssetUseRecordService assetUseRecordService;
    private OrganizationService organizationService;
    private DepartmentService departmentService;
    private AssetTypeService assetTypeService;
    private EmployeeService employeeService;

    public AssetApi(AssetService assetService, AssetUseRecordService assetUseRecordService, OrganizationService organizationService, DepartmentService departmentService, AssetTypeService assetTypeService, EmployeeService employeeService) {
        this.assetService = assetService;
        this.assetUseRecordService = assetUseRecordService;
        this.organizationService = organizationService;
        this.departmentService = departmentService;
        this.assetTypeService = assetTypeService;
        this.employeeService = employeeService;
    }

    /**
     * 查找资产列表
     *
     * @param id       资产id（查看资产详情）
     * @param assetId  资产id（查找资产使用记录）
     * @param number   资产编号
     * @param type     资产类型
     * @param status   资产状态
     * @param page     页脚
     * @param pageSize 每页数量
     * @param token    用户token
     * @return 返回资产列表/根据资产id查找资产详情/根据assetId查找资产使用记录
     */
    @RequestMapping(value = "/findAsset", method = RequestMethod.GET)
    public ResponseData findAsset(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "assetId", required = false) String assetId,
            @RequestParam(value = "number", required = false) String number,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "status", required = false, defaultValue = "-1") int status,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
            @RequestParam(value = "token", required = true) String token
    ) {
        if (id != null) {
            Asset asset = this.assetService.findAssetById(id);
            Map map = MapUtil.getMap(0, null);
            map.put("organizations", this.organizationService.findFatherOrg(asset.getOrganizationId(), new ArrayList<>()));
            map.put("departments", this.departmentService.findFatherDepartment(asset.getDepartmentId(), new ArrayList<>()));
            map.put("assetTypes", this.assetTypeService.findFatherAssetType(asset.getTypeId(), new ArrayList<>()));
            map.put("asset", asset);
            return ResponseData.build(ResponseCode.API_SUCCESS, map);
        }
        if (assetId != null) {
            return ResponseData.build(ResponseCode.API_SUCCESS,
                    this.assetUseRecordService.findAssetUseRecord(assetId));
        }
        return ResponseData.build(ResponseCode.API_SUCCESS, MapUtil.getMap(
                this.assetService.findAssetTotal(number, type, status),
                this.assetService.findAsset(number, type, status, page, pageSize)));
    }

    /**
     * 添加资产
     *
     * @param asset   资产信息
     * @param token   用户token
     * @param request HTTP请求
     * @return 添加成功返回成功信息/失败返回失败信息
     */
    @Operation(description = "add asset")
    @RequestMapping(value = "/addAsset", method = RequestMethod.POST)
    public ResponseBase addAsset(
            @RequestBody(required = true) Asset asset,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Employee employee = employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        asset.setCreateUser(employee.getName());
        asset.setUpdateUser(employee.getName());
        return ResponseBase.build(this.assetService.addAsset(asset));
    }

    /**
     * 删除资产
     *
     * @param id      资产id
     * @param token   用户token
     * @param request HTTP请求
     * @return
     */
    @Operation(description = "delete asset")
    @RequestMapping(value = "/deleteAsset", method = RequestMethod.DELETE)
    public ResponseBase deleteAsset(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseBase.build(this.assetService.deleteAssetById(id));
    }

    /**
     * 编辑资产
     *
     * @param asset   资产信息
     * @param token   用户token
     * @param request HTTP请求
     * @return
     */
    @Operation(description = "update asset")
    @RequestMapping(value = "/updateAsset", method = RequestMethod.PUT)
    public ResponseBase updateAsset(
            @RequestBody(required = true) Asset asset,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        asset.setUpdateUser(this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token)).getName());
        return ResponseBase.build(this.assetService.updateAssetById(asset));
    }

    /**
     * 分配资产
     *
     * @param assetUseRecord 资产使用记录信息
     * @param ids            资产id（可多个）
     * @param token          用户token
     * @param request        HTTP请求
     * @return
     */
    @Operation(description = "allot asset")
    @RequestMapping(value = "/allotAsset", method = RequestMethod.POST)
    public ResponseBase allotAsset(
            @RequestBody(required = true) AssetUseRecord assetUseRecord,
            @RequestParam(value = "ids", required = true) String ids,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        assetUseRecord.setStatus(1);
        return ResponseBase.build(this.assetUseRecordService
                .addAssetUseRecord(assetUseRecord, SplitString.getList(ids)));
    }

    /**
     * 交还资产
     *
     * @param assetUseRecord 资产使用记录信息
     * @param ids            资产id（可多个）
     * @param token          用户token
     * @param request        HTTP请求
     * @return
     */
    @Operation(description = "return asset")
    @RequestMapping(value = "/returnAsset", method = RequestMethod.PUT)
    public ResponseBase returnAsset(
            @RequestBody(required = true) AssetUseRecord assetUseRecord,
            @RequestParam(value = "ids", required = true) String ids,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        assetUseRecord.setStatus(0);
        return ResponseBase.build(this.assetUseRecordService
                .updateAssetUseRecordByAssetId(assetUseRecord, SplitString.getList(ids)));
    }
}
