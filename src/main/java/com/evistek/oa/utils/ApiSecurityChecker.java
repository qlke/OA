package com.evistek.oa.utils;

import com.evistek.oa.dao.AuthorityDao;
import com.evistek.oa.dao.EmployeeDao;
import com.evistek.oa.entity.ApiAuthority;
import com.evistek.oa.entity.Authority;
import com.evistek.oa.entity.Employee;
import com.evistek.oa.service.ApiAuthorityService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/16
 */
@Component
public class ApiSecurityChecker {
    private String api;
    private String apiMethod;
    private EmployeeDao employeeDao;
    private ApiAuthorityService apiAuthorityService;
    private AuthorityDao authorityDao;


    public ApiSecurityChecker(EmployeeDao employeeDao, ApiAuthorityService apiAuthorityService, AuthorityDao authorityDao) {
        this.employeeDao = employeeDao;
        this.apiAuthorityService = apiAuthorityService;
        this.authorityDao = authorityDao;
    }

    public synchronized ResponseCode checker(HttpServletRequest request) {
        String token = request.getParameter("token");
        if (token == null) {
            return ResponseCode.API_TOKEN_IS_NULL;
        }
        Employee employee = this.employeeDao.findEmployeeByEmail(JwtUtil.getEmail(token));
        if (employee == null) {
            return ResponseCode.API_TOKEN_EXPIRED_OR_ERROR;
        }
        //API调用权限
        this.api = request.getRequestURI();
        this.apiMethod = request.getMethod();
        int index = this.api.indexOf("/api");
        this.api = this.api.substring(index);

        if (!hasApiAuthority(employee.getId(), this.api, this.apiMethod)) {
            return ResponseCode.API_NOT_PERMISSION;
        }
        return ResponseCode.API_SUCCESS;
    }

    private boolean hasApiAuthority(String employeeId, String api, String method) {
        List<Authority> authorities = this.authorityDao.findAuthorityByEId(employeeId);
        if (authorities.isEmpty() || authorities == null) {
            return false;
        }
        //如果在数据库中api_authority表中没有API的权限设置
        String apiAuthority = null;
        ApiAuthority apiAuth = this.apiAuthorityService.findApiAuthority(api, method);
        if (apiAuth == null) {
            return true;
        }
        apiAuthority = apiAuth.getAuthority();
        if (apiAuthority == null) {
            return true;
        }
        for (Authority auth : authorities) {
            if (apiAuthority.equals(auth.getIdentify())) {
                return true;
            }
        }
        return false;
    }
}
