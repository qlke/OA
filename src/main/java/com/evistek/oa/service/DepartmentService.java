package com.evistek.oa.service;

import com.evistek.oa.entity.Department;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/5
 */
public interface DepartmentService {
    List<Department> findDepartmentByOrganizationId(String organizationId);

    Department findDepartmentById(String id);

    List<Department> findFatherDepartment(String fatherId, List<Department> resultList);

    List<Department> findChildren(String fatherId);

    ResponseCode addDepartment(Department departments);

    ResponseCode deleteDepartmentById(String id);

    ResponseCode updateDepartmentById(Department departments);

    ResponseCode hasAuthority(String organizationId, String departmentId);
}