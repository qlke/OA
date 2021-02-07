package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.Department;
import com.evistek.oa.service.AssetService;
import com.evistek.oa.service.DepartmentService;
import com.evistek.oa.service.OrganizationService;
import com.evistek.oa.utils.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/5
 */
@RestController
@RequestMapping("/api/v1")
public class DepartmentApi {
    private DepartmentService departmentService;
    private OrganizationService organizationService;
    private SystemUtil systemUtil;
    private AssetService assetService;

    public DepartmentApi(DepartmentService departmentService, OrganizationService organizationService, SystemUtil systemUtil, AssetService assetService) {
        this.departmentService = departmentService;
        this.organizationService = organizationService;
        this.systemUtil = systemUtil;
        this.assetService = assetService;
    }

    /**
     * 查找部门列表
     *
     * @param organizationId 组织机构id
     * @param id             部门id
     * @param token          用户token
     * @return
     */
    @RequestMapping(value = "/findDepartment", method = RequestMethod.GET)
    public ResponseBase findDepartment(
            @RequestParam(value = "organizationId", required = false) String organizationId,
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "token", required = true) String token
    ) {
        if (organizationId != null) {
            String orgId = systemUtil.getOrganizationId(token);
            //校验是否有权限访问该组织机构下的部门
            ResponseCode responseCode = this.organizationService.hasAuthority(organizationId, orgId);
            if (responseCode == ResponseCode.API_SUCCESS || organizationId.equals(orgId)) {
                return ResponseData.build(ResponseCode.API_SUCCESS,
                        this.departmentService.findDepartmentByOrganizationId(organizationId));
            }
            return ResponseBase.build(responseCode);
        }
        if (id != null) {
            String orgId = systemUtil.getOrganizationId(token);
            ResponseCode responseCode = this.departmentService.hasAuthority(orgId, id);
            if (responseCode == ResponseCode.API_SUCCESS || orgId.equals(id)) {
                Department department = this.departmentService.findDepartmentById(id);
                Map map = MapUtil.getMap(0, null);
                map.put("organizations", organizationService.findFatherOrg(department.getOrganizationId(),
                        new ArrayList<>()));
                map.put("departmentFathers", departmentService.findFatherDepartment(department.getFatherId(),
                        new ArrayList<>()));
                map.put("departments", department);
                return ResponseData.build(ResponseCode.API_SUCCESS, map);
            }
            return ResponseBase.build(responseCode);
        }
        return ResponseData.build(ResponseCode.API_SUCCESS, this.departmentService
                .findDepartmentByOrganizationId(systemUtil.getOrganizationId(token)));
    }

    /**
     * 新增部门
     *
     * @param department 部门信息
     * @param token      用户token
     * @param request    HTTP请求
     * @return
     */
    @Operation(description = "add department")
    @RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
    public ResponseBase addDepartment(
            @RequestBody(required = true) Department department,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        if (department.getOrganizationId() == null) {
            return ResponseBase.build(ResponseCode.ORGANIZATION_NOT_FOUND);
        }
        return ResponseBase.build(this.departmentService.addDepartment(department));
    }

    /**
     * 编辑部门
     *
     * @param department 部门信息
     * @param token      用户token
     * @param request    HTTP请求
     * @return
     */
    @Operation(description = "update department")
    @RequestMapping(value = "/updateDepartment", method = RequestMethod.PUT)
    public ResponseBase updateDepartment(
            @RequestBody(required = true) Department department,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        if (department.getOrganizationId() == null && department.getId().equals(department.getFatherId())) {
            return ResponseBase.build(ResponseCode.ORGANIZATION_NOT_FOUND);
        }
        ResponseCode responseCode = this.departmentService
                .hasAuthority(systemUtil.getOrganizationId(token), department.getId());
        if (responseCode.getCode() != ResponseCode.API_SUCCESS.getCode()) {
            return ResponseBase.build(responseCode);
        }
        return ResponseBase.build(this.departmentService.updateDepartmentById(department));
    }

    /**
     * 删除部门
     *
     * @param id      部门id
     * @param token   用户token
     * @param request HTTP请求
     * @return
     */
    @Operation(description = "delete department")
    @RequestMapping(value = "/deleteDepartment", method = RequestMethod.DELETE)
    public ResponseBase deleteDepartment(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        ResponseCode responseCode = this.departmentService
                .hasAuthority(systemUtil.getOrganizationId(token), id);
        if (responseCode != ResponseCode.API_SUCCESS) {
            return ResponseBase.build(responseCode);
        }
        return ResponseBase.build(this.departmentService.deleteDepartmentById(id));
    }

    /**
     * 查找部门资产
     *
     * @param id    部门id
     * @param token 用户token
     * @return 返回部门资产
     */
    @RequestMapping(value = "/findDepartmentAsset", method = RequestMethod.GET)
    public ResponseBase findDepartmentAsset(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "token", required = true) String token
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS, this.assetService.findAssetByDepartmentId(id));
    }
}
