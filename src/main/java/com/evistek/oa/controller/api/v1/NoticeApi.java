package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.Department;
import com.evistek.oa.entity.Employee;
import com.evistek.oa.entity.Notice;
import com.evistek.oa.entity.Organization;
import com.evistek.oa.service.DepartmentService;
import com.evistek.oa.service.EmployeeService;
import com.evistek.oa.service.NoticeService;
import com.evistek.oa.service.OrganizationService;
import com.evistek.oa.utils.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/10
 */
@RestController
@RequestMapping("/api/v1")
public class NoticeApi {
    private NoticeService noticeService;
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private OrganizationService organizationService;

    public NoticeApi(NoticeService noticeService, EmployeeService employeeService, DepartmentService departmentService, OrganizationService organizationService) {
        this.noticeService = noticeService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.organizationService = organizationService;
    }

    /**
     * 查找所有已发布通知列表
     *
     * @param page     页脚
     * @param pageSize 每页数量
     * @param token    用户token
     * @return 响应已发布的通知列表
     */
    @RequestMapping(value = "/findAllPublishNotice", method = RequestMethod.GET)
    public ResponseBase findAllPublishNotice(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
            @RequestParam(value = "token", required = true) String token
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS,
                this.noticeService.findAllPublishNotice(page, pageSize));
    }

    /**
     * 查找发布给当前员工（组织机构、部门、个人）的所有通知
     *
     * @param page     页脚
     * @param pageSize 每页数量
     * @param token    用户token
     * @return 响应发布给当前员工的通知列表
     */
    @RequestMapping(value = "/findPublishNotice", method = RequestMethod.GET)
    public ResponseBase findPublishNotice(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
            @RequestParam(value = "token", required = true) String token
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        List<String> objectIds = new ArrayList<>();
        objectIds.add(employee.getId());
        if(!employee.getDepartmentId().isEmpty()){
            objectIds.add(employee.getDepartmentId());
        }
        objectIds.add(employee.getOrganizationId());
        List<Department> departments = this.departmentService.findChildren(employee.getDepartmentId());
        for (Department dep : departments) {
            objectIds.add(dep.getId());
        }
        List<Organization> organizations = this.organizationService.findChildren(employee.getOrganizationId());
        for (Organization org : organizations) {
            objectIds.add(org.getId());
        }
        return ResponseData.build(ResponseCode.API_SUCCESS,
                this.noticeService.findPublishNotice(objectIds, page, pageSize));
    }

    /**
     * 查找当前员工起草的所有通知
     *
     * @param id       公告id
     * @param page     页脚
     * @param pageSize 每页数量
     * @param token    用户token
     * @return 响应当前员工起草的通知列表
     */
    @RequestMapping(value = "/findNotice", method = RequestMethod.GET)
    public ResponseBase findNotice(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
            @RequestParam(value = "token", required = true) String token
    ) {
        if (id != null) {
            return ResponseData.build(ResponseCode.API_SUCCESS, this.noticeService.findNoticeById(id));
        }
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        return ResponseData.build(ResponseCode.API_SUCCESS,
                this.noticeService.findNotice(employee.getId(), page, pageSize));
    }

    /**
     * 起草公告
     *
     * @param notice 起草的公告信息
     * @param token  用户token
     * @return 响应操作信息
     */
    @RequestMapping(value = "/addNotice", method = RequestMethod.POST)
    public ResponseBase addNotice(
            @RequestBody(required = true) Notice notice,
            @RequestParam(value = "token", required = true) String token
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        notice.setUserId(employee.getId());
        notice.setCreateUser(employee.getName());
        notice.setUpdateUser(employee.getName());
        return ResponseBase.build(this.noticeService.addNotice(notice));
    }

    /**
     * 发布公告
     *
     * @param id        公告id
     * @param objectIds 发布对象id
     * @param token     用户token
     * @param request   HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "publish notice")
    @RequestMapping(value = "/publishNotice", method = RequestMethod.POST)
    public ResponseBase publishNotice(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "objectIds", required = true) String objectIds,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseBase.build(this.noticeService.updatePublishById(id, objectIds));
    }

    /**
     * 编辑公告
     *
     * @param notice  公告信息
     * @param token   用户token
     * @param request HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "update notice")
    @RequestMapping(value = "/updateNotice", method = RequestMethod.PUT)
    public ResponseBase updateNotice(
            @RequestBody(required = true) Notice notice,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        notice.setUpdateUser(employee.getName());
        return ResponseBase.build(this.noticeService.updateNoticeById(notice));
    }

    /**
     * 重新发布公告
     *
     * @param notice    公告信息
     * @param objectIds 对象id
     * @param token     用户token
     * @param request   HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "republish notice")
    @RequestMapping(value = "/republishNotice", method = RequestMethod.POST)
    public ResponseBase republishNotice(
            @RequestBody(required = true) Notice notice,
            @RequestParam(value = "objectIds", required = true) String objectIds,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        notice.setUpdateUser(employee.getName());
        return ResponseBase.build(this.noticeService.republishNotice(notice, objectIds));
    }

    /**
     * 删除公告
     *
     * @param id      公告id
     * @param token   用户token
     * @param request HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "delete notice")
    @RequestMapping(value = "/deleteNotice", method = RequestMethod.DELETE)
    public ResponseBase deleteNotice(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseBase.build(this.noticeService.deleteNoticeById(id));
    }
}
