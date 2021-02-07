package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.Department;
import com.evistek.oa.entity.Employee;
import com.evistek.oa.service.*;
import com.evistek.oa.utils.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/6
 */
@RestController
@RequestMapping("/api/v1")
public class EmploymentApi {
    private EmployeeService employeeService;
    private OrganizationService organizationService;
    private DepartmentService departmentService;
    private AssetService assetService;
    private TempEmployeeService tempEmployeeService;
    private AuthorityService authorityService;

    public EmploymentApi(EmployeeService employeeService, OrganizationService organizationService,
                         DepartmentService departmentService, AssetService assetService, TempEmployeeService tempEmployeeService, AuthorityService authorityService) {
        this.employeeService = employeeService;
        this.organizationService = organizationService;
        this.departmentService = departmentService;
        this.assetService = assetService;
        this.tempEmployeeService = tempEmployeeService;
        this.authorityService = authorityService;
    }

    /**
     * 员工登录
     *
     * @param email    员工邮箱
     * @param password 登录密码
     * @param request  HTTP请求
     * @return 响应员工、权限、token信息
     */
    @Operation(description = "employee login")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseBase userLogin(
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password,
            HttpServletRequest request
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(email);
        if (employee != null && password.equals(employee.getPassword())) {
            Map map = MapUtil.getMap(0, null);
            map.put("employee", employee);
            map.put("token", JwtUtil.getToken(employee.getEmail()));
            map.put("authority", this.authorityService.findAuthorityByEId(employee.getId()));
            return ResponseData.build(ResponseCode.API_SUCCESS, map);
        }
        return ResponseBase.build(ResponseCode.API_EMAIL_OR_PWD_ERROR);
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
    public ResponseBase updatePassword(
            @RequestParam(value = "oldPassword", required = true) String oldPassword,
            @RequestParam(value = "newPassword", required = true) String newPassword,
            @RequestParam(value = "token", required = true) String token
    ) {
        Employee employee = employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        if (!employee.getPassword().equals(oldPassword)) {
            return ResponseBase.build(ResponseCode.API_PASSWORD_ERROR);
        }
        employee.setPassword(newPassword);
        return ResponseBase.build(this.employeeService.updateEmployeeById(employee, null));
    }

    @Operation(description = "update employee")
    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    public ResponseBase updateUser(
            @RequestBody(required = true) Employee employee,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseBase.build(this.employeeService.updateEmployeeById(employee, null));
    }


    @RequestMapping(value = "/findTempEmployee", method = RequestMethod.GET)
    public ResponseBase findTempEmployee(@RequestParam(value = "token", required = true) String token) {
        return ResponseData.build(ResponseCode.API_SUCCESS, this.tempEmployeeService.findTempEmployee());
    }

    /**
     * 查找员工列表
     *
     * @param departmentId 部门id（获取部门下的员工）
     * @param id           员工id(查看员工详情)
     * @param name         员工姓名（模糊查询）
     * @param page         页脚
     * @param pageSize     每页数量
     * @param token        用户token
     * @return
     */
    @RequestMapping(value = "/findEmployee", method = RequestMethod.GET)
    public ResponseBase findEmployee(
            @RequestParam(value = "departmentId", required = false) String departmentId,
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "token", required = true) String token
    ) {
        //查看员工详情
        if (id != null) {
            Employee emp = this.employeeService.findEmployeeById(id);
            Map map = MapUtil.getMap(0, null);
            map.put("organizations", this.organizationService.findFatherOrg(emp.getOrganizationId(),
                    new ArrayList<>()));
            map.put("departments", this.departmentService.findFatherDepartment(emp.getDepartmentId(),
                    new ArrayList<>()));
            map.put("employee", emp);
            return ResponseData.build(ResponseCode.API_SUCCESS, map);
        }
        //获取当前用户的有效数据
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        List<Employee> employeesList = this.employeeService.findEmployeeByPId(employee.getPositions());
        if (employeesList.size() != 0) {
            List<Employee> resultEmployee = new ArrayList<>();
            //获取部门下的员工
            if (departmentId != null) {
                //获取当前部门的所有子部门
                List<Department> departments = this.departmentService.findChildren(departmentId);
                List<String> departmentIds = new ArrayList<>();
                if (departments.size() != 0) {
                    for (Department dep : departments) {
                        departmentIds.add(dep.getId());
                    }
                }
                departmentIds.add(departmentId);
                for (Employee em : employeesList) {
                    if (departmentIds.contains(em.getDepartmentId())) {
                        resultEmployee.add(em);
                    }
                }
                return ResponseData.build(ResponseCode.API_SUCCESS,
                        MapUtil.getMap(resultEmployee.size(), PageHelper.empPages(resultEmployee, page, pageSize)));
            }
            //根据员工姓名模糊查找
            if (name != null) {
                Pattern pattern = Pattern.compile(name);
                for (Employee e : employeesList) {
                    Matcher matcher = pattern.matcher(e.getName());
                    if (matcher.find()) {
                        resultEmployee.add(e);
                    }
                }
                return ResponseData.build(ResponseCode.API_SUCCESS,
                        MapUtil.getMap(resultEmployee.size(), PageHelper.empPages(resultEmployee, page, pageSize)));
            }
        }
        return ResponseData.build(ResponseCode.API_SUCCESS,
                MapUtil.getMap(employeesList.size(),
                        PageHelper.empPages(employeesList, page, pageSize)));
    }

    /**
     * 添加员工
     *
     * @param employee   员工信息
     * @param positionId 职位id（可多个）
     * @param token      用户token
     * @param request    HTTP请求
     * @return
     */
    @Operation(description = "add employee")
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public ResponseBase addEmployee(
            @RequestBody(required = true) Employee employee,
            @RequestParam(value = "positionId", required = true) String positionId,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        if (employee.getOrganizationId() == null) {
            return ResponseBase.build(ResponseCode.ORGANIZATION_NOT_FOUND);
        }
        Employee emp = employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        employee.setCreateUser(emp.getName());
        employee.setUpdateUser(emp.getName());
        return ResponseBase.build(this.employeeService.addEmployee(employee, positionId));
    }

    /**
     * 删除员工
     *
     * @param id      员工id
     * @param token   用户token
     * @param request HTTP请求
     * @return
     */
    @Operation(description = "delete employee")
    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.DELETE)
    public ResponseBase deleteEmployee(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        String employeeId = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token)).getId();
        //不能删除自己
        if (employeeId.equals(id)) {
            return ResponseBase.build(ResponseCode.EMPLOYEE_NOT_DELETE_ONESELF);
        }
        return ResponseBase.build(this.employeeService.deleteEmployeeById(id));
    }

    @Operation(description = "update employee")
    @RequestMapping(value = "/updateEmployee", method = RequestMethod.PUT)
    public ResponseBase updateEmployee(
            @RequestBody(required = true) Employee employee,
            @RequestParam(value = "positionId", required = true) String positionId,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Employee emp = employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        employee.setUpdateUser(emp.getName());
        return ResponseBase.build(this.employeeService.updateEmployeeById(employee, positionId));
    }

    /**
     * 查找员工资产
     *
     * @param id    员工id
     * @param token 用户token
     * @return 返回员工资产
     */
    @RequestMapping(value = "/findEmployeeAsset", method = RequestMethod.GET)
    public ResponseBase findEmployeeAsset(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "token", required = true) String token
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS, this.assetService.findAssetByEmployeeId(id));
    }
}
