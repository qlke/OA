package com.evistek.oa.controller.api.v1;

import com.evistek.oa.entity.*;
import com.evistek.oa.service.AuthorityService;
import com.evistek.oa.service.DepartmentService;
import com.evistek.oa.service.EmployeeService;
import com.evistek.oa.service.OrganizationService;
import com.evistek.oa.utils.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/4
 */
@RestController
@RequestMapping("/api/v1")
public class AuthorityApi {
    private AuthorityService authorityService;
    private OrganizationService organizationService;
    private DepartmentService departmentService;
    private EmployeeService employeeService;

    public AuthorityApi(AuthorityService authorityService, OrganizationService organizationService, DepartmentService departmentService, EmployeeService employeeService) {
        this.authorityService = authorityService;
        this.organizationService = organizationService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    /**
     * 查找权限列表
     *
     * @param token 用户token
     * @return 返回用户权限列表
     */
    @RequestMapping(value = "/findAuthority", method = RequestMethod.GET)
    public ResponseBase findAuthority(
            @RequestParam(value = "positionId", required = false) String positionId,
            @RequestParam(value = "token", required = true) String token
    ) {
        if (positionId != null) {
            List<Authority> authorities = authorityService.findAuthorityByPId(positionId);
            return ResponseData.build(ResponseCode.API_SUCCESS, authorities);
        }
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        List<Authority> authorities = authorityService.findAuthorityByEId(employee.getId());
        return ResponseData.build(ResponseCode.API_SUCCESS, authorities);
    }

    /**
     * 查找数据权限
     *
     * @param positionId 职位id
     * @param token      用户token
     * @return 返回数据权限
     */
    @RequestMapping(value = "/findDataAuthority", method = RequestMethod.GET)
    public ResponseBase findDataAuthority(
            @RequestParam(value = "positionId", required = true) String positionId,
            @RequestParam(value = "token", required = true) String token
    ) {
        List<Organization> organizations = new ArrayList<>();
        List<Department> departments = new ArrayList<>();
        List<DataAuthority> roleDataAuthorities = this.authorityService.findRoleDataAuthorityByPId(positionId);
        for (DataAuthority r : roleDataAuthorities) {
            if (r.getOrganizationId() != null) {
                organizations.addAll(organizationService.findFatherOrg(r.getOrganizationId(), new ArrayList<>()));
            }
            if (r.getDepartmentId() != null) {
                departments.addAll(departmentService.findFatherDepartment(r.getDepartmentId(), new ArrayList<>()));
            }
        }
        Map map = MapUtil.getMap(0, null);
        if (departments.size() != 0) {
            departments = departments.stream()
                    .filter(RemoveRepetitionUtil.distinctByKey(Department::getName)).collect(Collectors.toList());
            for (Department dep : departments) {
                organizations.addAll(organizationService.findFatherOrg(dep.getOrganizationId(), new ArrayList<>()));
            }
            map.put("departments", departments);
        }
        map.put("organizations", organizations.stream()
                .filter(RemoveRepetitionUtil.distinctByKey(Organization::getName)).collect(Collectors.toList()));
        return ResponseData.build(ResponseCode.API_SUCCESS, map);
    }
}
