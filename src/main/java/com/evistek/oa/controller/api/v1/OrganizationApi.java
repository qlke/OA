package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.Organization;
import com.evistek.oa.service.OrganizationService;
import com.evistek.oa.utils.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/10
 */
@RestController
@RequestMapping("/api/v1")
public class OrganizationApi {
    private OrganizationService organizationService;
    private SystemUtil systemUtil;

    public OrganizationApi(OrganizationService organizationService, SystemUtil systemUtil) {
        this.organizationService = organizationService;
        this.systemUtil = systemUtil;
    }

    /**
     * 查找组织机构列表
     *
     * @param id    组织机构id
     * @param token 用户token
     * @return
     */
    @RequestMapping(value = "/findOrganization", method = RequestMethod.GET)
    public ResponseBase findOrganization(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "token", required = true) String token
    ) {
        if (id != null) {
            return ResponseData.build(ResponseCode.API_SUCCESS,
                    this.organizationService.findOrganizationById(id));
        }
        return ResponseData.build(ResponseCode.API_SUCCESS,
                this.organizationService.findOrganization(systemUtil.getOrganizationId(token)));
    }

    /**
     * 新增组织机构
     *
     * @param organization 组织机构信息
     * @param token        用户token
     * @param request      HTTP请求
     * @return
     */
    @Operation(description = "add organization")
    @RequestMapping(value = "/addOrganization", method = RequestMethod.POST)
    public ResponseBase addOrganization(
            @RequestBody(required = true) Organization organization,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Organization org = this.organizationService.findOrganizationById(organization.getFatherId());
        if (org == null) {
            return ResponseBase.build(ResponseCode.ORGANIZATION_FATHER_NOT_FOUND);
        }
        return ResponseBase.build(this.organizationService.addOrganization(organization));
    }

    /**
     * 删除组织机构
     *
     * @param id      组织机构id
     * @param token   用户token
     * @param request HTTP请求
     * @return
     */
    @Operation(description = "delete organization")
    @RequestMapping(value = "/deleteOrganization", method = RequestMethod.DELETE)
    public ResponseBase deleteOrganization(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        String orgId = systemUtil.getOrganizationId(token);
        ResponseCode responseCode = this.organizationService.hasAuthority(id, orgId);
        if (responseCode == ResponseCode.API_SUCCESS || id.equals(orgId)) {
            return ResponseBase.build(this.organizationService.deleteOrganizationById(id));
        }
        return ResponseBase.build(responseCode);
    }

    /**
     * 编辑组织机构
     *
     * @param organization 组织机构信息
     * @param token        用户token
     * @param request      HTTP请求
     * @return
     */
    @Operation(description = "update organization")
    @RequestMapping(value = "/updateOrganization", method = RequestMethod.PUT)
    public ResponseBase updateOrganization(
            @RequestBody(required = true) Organization organization,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        String orgId = systemUtil.getOrganizationId(token);
        ResponseCode responseCode = this.organizationService.hasAuthority(organization.getId(), orgId);
        if (responseCode == ResponseCode.API_SUCCESS || orgId.equals(organization.getId())) {
            return ResponseBase.build(this.organizationService.updateOrganizationById(organization));
        }
        return ResponseBase.build(responseCode);
    }
}
