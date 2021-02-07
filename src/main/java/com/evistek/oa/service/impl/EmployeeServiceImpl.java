package com.evistek.oa.service.impl;

import com.evistek.oa.dao.*;
import com.evistek.oa.entity.Employee;
import com.evistek.oa.entity.Position;
import com.evistek.oa.entity.TempEmployee;
import com.evistek.oa.service.EmployeeService;
import com.evistek.oa.utils.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/4
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeDao employeeDao;
    private DepartmentDao departmentDao;
    private EmployeePositionDao employeePositionDao;
    private AssetUseRecordDao assetUseRecordDao;
    private TempEmployeeDao tempEmployeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao, DepartmentDao departmentDao, EmployeePositionDao employeePositionDao, AssetUseRecordDao assetUseRecordDao, TempEmployeeDao tempEmployeeDao) {
        this.employeeDao = employeeDao;
        this.departmentDao = departmentDao;
        this.employeePositionDao = employeePositionDao;
        this.assetUseRecordDao = assetUseRecordDao;
        this.tempEmployeeDao = tempEmployeeDao;
    }

    @Override
    public Employee findEmployeeByEmail(String email) {
        return this.employeeDao.findEmployeeByEmail(email);
    }

    @Override
    public Employee findEmployeeById(String id) {
        return this.employeeDao.findEmployeeById(id);
    }

    @Override
    @Transactional
    public ResponseCode addEmployee(Employee employee, String positionId) {
        employee.setId(UuidUtil.getUUID());
        employee.setPassword(MD5Util.md5());
        //邮件不能重复
        if (this.employeeDao.findEmployeeByEmail(employee.getEmail()) != null) {
            return ResponseCode.EMPLOYEE_EMAIL_ALREADY_EXIST;
        }
        //手机号码不能重复
        if (this.employeeDao.findEmployeeByPhone(employee.getPhone()) != null) {
            return ResponseCode.EMPLOYEE_PHONE_ALREADY_EXIST;
        }
        int result = this.employeeDao.addEmployee(employee);
        if (result == 0) {
            return ResponseCode.API_ADD_FAILED;
        }
        result = addTempEmployee(employee);
        if (result == 0) {
            throw new RuntimeException("add tempEmployee failed.");
        }
        //关联员工职位
        Map map = MapUtil.getMap(0, SplitString.getList(positionId));
        map.put("employeeId", employee.getId());
        result = this.employeePositionDao.addEmployeePosition(map);
        if (result == 0) {
            throw new RuntimeException("add employeePosition failed.");
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    @Transactional
    public ResponseCode deleteEmployeeById(String id) {
        if (this.assetUseRecordDao.findAssetUseRecordByRED(id).size() != 0) {
            return ResponseCode.EMPLOYEE_EXIST_UN_RETURNED_ASSET;
        }
        int result = this.employeeDao.deleteEmployeeById(id);
        if (result == 0) {
            return ResponseCode.API_DELETE_FAILED;
        }
        result = this.tempEmployeeDao.deleteTempEmployee(id);
        if (result == 0) {
            throw new RuntimeException("delete tempEmployee failed.");
        }
        result = this.employeePositionDao.deleteEmployeePositionByEId(id);
        if (result == 0) {
            throw new RuntimeException("delete employeePosition failed.");
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    @Transactional
    public ResponseCode updateEmployeeById(Employee employee, String positionId) {
        Employee emp = this.employeeDao.findEmployeeByEmail(employee.getEmail());
        if (emp != null && !emp.getId().equals(employee.getId())) {
            return ResponseCode.EMPLOYEE_EMAIL_ALREADY_EXIST;
        }

        emp = this.employeeDao.findEmployeeByPhone(employee.getPhone());
        if (emp != null && !emp.getId().equals(employee.getId())) {
            return ResponseCode.EMPLOYEE_PHONE_ALREADY_EXIST;
        }

        int result = this.employeeDao.updateEmployeeById(employee);
        if (result == 0) {
            return ResponseCode.API_UPDATE_FAILED;
        }
        result = updateTempEmployee(employee);
        if (result == 0) {
            throw new RuntimeException("update tempEmployee failed.");
        }
        if (positionId != null) {
            result = this.employeePositionDao.deleteEmployeePositionByEId(employee.getId());
            if (result == 0) {
                throw new RuntimeException("delete employeePosition failed.");
            }
            Map map = MapUtil.getMap(0, SplitString.getList(positionId));
            map.put("employeeId", employee.getId());
            result = this.employeePositionDao.addEmployeePosition(map);
            if (result == 0) {
                throw new RuntimeException("add employeePosition failed");
            }
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public List<Employee> findEmployeeByPId(List<Position> list) {
        return employeeDao.findEmployeeByPId(list);
    }

    public List<Employee> findChildren(String id) {
        List<Employee> resultList = new ArrayList<>();
        List<Employee> children = this.employeeDao.findEmployeeByFatherId(id);
        if (children == null || children.size() == 0) {
            return resultList;
        }
        resultList.addAll(children);
        for (Employee em : children) {
            resultList.addAll(findChildren(em.getId()));
        }
        return resultList;
    }

    private int addTempEmployee(Employee employee) {
        TempEmployee tempEmployee = new TempEmployee();
        tempEmployee.setEmployeeId(employee.getId());
        tempEmployee.setEmail(employee.getEmail());
        tempEmployee.setName(employee.getName());
        return this.tempEmployeeDao.addTempEmployee(tempEmployee);
    }

    private int updateTempEmployee(Employee employee) {
        TempEmployee tempEmployee = new TempEmployee();
        tempEmployee.setEmployeeId(employee.getId());
        tempEmployee.setEmail(employee.getEmail());
        tempEmployee.setName(employee.getName());
        return this.tempEmployeeDao.updateTempEmployee(tempEmployee);
    }
}
