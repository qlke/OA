package com.evistek.oa.utils;

import com.evistek.oa.service.EmployeeService;
import org.springframework.stereotype.Component;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/17
 */
@Component
public class SystemUtil {
    private EmployeeService employeeService;

    public SystemUtil(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public String getOrganizationId(String token) {
        return this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token)).getOrganizationId();
    }
}
