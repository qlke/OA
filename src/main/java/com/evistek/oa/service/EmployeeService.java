package com.evistek.oa.service;

import com.evistek.oa.entity.Employee;
import com.evistek.oa.entity.Position;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/4
 */
public interface EmployeeService {
    Employee findEmployeeByEmail(String email);

    List<Employee> findChildren(String id);

    Employee findEmployeeById(String id);

    ResponseCode addEmployee(Employee employee, String positionId);

    ResponseCode deleteEmployeeById(String id);

    ResponseCode updateEmployeeById(Employee employee, String positionId);

    List<Employee> findEmployeeByPId(List<Position> list);
}
