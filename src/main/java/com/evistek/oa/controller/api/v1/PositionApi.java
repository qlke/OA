package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.Position;
import com.evistek.oa.service.PositionService;
import com.evistek.oa.utils.MapUtil;
import com.evistek.oa.utils.ResponseBase;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.ResponseData;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/6
 */
@RestController
@RequestMapping("/api/v1")
public class PositionApi {
    private PositionService positionService;

    public PositionApi(PositionService positionService) {
        this.positionService = positionService;
    }

    @RequestMapping(value = "/findPosition", method = RequestMethod.GET)
    public ResponseBase findPosition(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        if (id != null) {
            return ResponseData.build(ResponseCode.API_SUCCESS, this.positionService.findPositionById(id));
        }
        return ResponseData.build(ResponseCode.API_SUCCESS, MapUtil.getMap(
                this.positionService.findPositionTotal(name),
                this.positionService.findPosition(name, page, pageSize)));
    }

    /**
     * 新增职位
     *
     * @param position    职位信息
     * @param authorityId 权限id(多个)
     * @param orgId       组织机构id(可多个)
     * @param depId       部门id(可多个)
     * @param token       用户token
     * @param request     HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "add position")
    @RequestMapping(value = "/addPosition", method = RequestMethod.POST)
    public ResponseBase addPosition(
            @RequestBody(required = true) Position position,
            @RequestParam(value = "authorityId", required = true) String authorityId,
            @RequestParam(value = "organizationId", required = false) String orgId,
            @RequestParam(value = "departmentId", required = false) String depId,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseBase.build(this.positionService.addPosition(position, authorityId, orgId, depId));
    }

    @Operation(description = "delete position")
    @RequestMapping(value = "/deletePosition", method = RequestMethod.DELETE)
    public ResponseBase deletePosition(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseBase.build(this.positionService.deletePosition(id));

    }

    @Operation(description = "update position")
    @RequestMapping(value = "/updatePosition", method = RequestMethod.PUT)
    public ResponseBase updatePosition(
            @RequestBody(required = true) Position position,
            @RequestParam(value = "authorityId", required = true) String authorityId,
            @RequestParam(value = "organizationId", required = false) String orgId,
            @RequestParam(value = "departmentId", required = false) String depId,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseBase.build(this.positionService.updatePosition(position, authorityId, orgId, depId));
    }
}
