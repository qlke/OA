package com.evistek.oa.service.impl;

import com.evistek.oa.dao.DepartmentDao;
import com.evistek.oa.dao.EmployeeDao;
import com.evistek.oa.dao.OrganizationDao;
import com.evistek.oa.entity.Department;
import com.evistek.oa.entity.Organization;
import com.evistek.oa.service.DepartmentService;
import com.evistek.oa.service.OrganizationService;
import com.evistek.oa.utils.ResponseCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/5
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentDao departmentDao;
    private OrganizationDao organizationDao;
    private EmployeeDao employeeDao;
    private OrganizationService organizationService;

    public DepartmentServiceImpl(DepartmentDao departmentDao, OrganizationDao organizationDao, EmployeeDao employeeDao, OrganizationService organizationService) {
        this.departmentDao = departmentDao;
        this.organizationDao = organizationDao;
        this.employeeDao = employeeDao;
        this.organizationService = organizationService;
    }

    @Override
    public List<Department> findDepartmentByOrganizationId(String organizationId) {
        return this.departmentDao.findDepartmentByOrganizationId(organizationId);
    }

    @Override
    public Department findDepartmentById(String id) {
        return this.departmentDao.findDepartmentById(id);
    }

    @Override
    @Transactional
    public ResponseCode addDepartment(Department departments) {
        List<Department> deps = findDepartmentByOrganizationId(departments.getOrganizationId());
        for (Department d : deps) {
            if (d.getName().equals(departments.getName())) {
                return ResponseCode.DEPARTMENT_ALREADY_EXIST;
            }
        }
        int result = this.departmentDao.addDepartment(departments);
        if (result == 0) {
            return ResponseCode.API_ADD_FAILED;
        }
        Organization organization = getOrganization(departments.getOrganizationId());
        if (organization != null) {
            organization.setNumber(organization.getNumber() + 1);
            result = this.organizationDao.updateOrganizationById(organization);
            if (result == 0) {
                throw new RuntimeException("update organization number failed.");
            }
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode deleteDepartmentById(String id) {
        String organizationId = findDepartmentById(id).getOrganizationId();
        if (!this.employeeDao.findEmployeeByDId(id).isEmpty()) {
            return ResponseCode.DEPARTMENT_NOT_DELETE_REF;
        }
        int result = this.departmentDao.deleteDepartmentById(id);
        if (result == 0) {
            return ResponseCode.API_DELETE_FAILED;
        }
        Organization organization = getOrganization(organizationId);
        if (organization != null) {
            organization.setNumber(organization.getNumber() - 1);
            result = this.organizationDao.updateOrganizationById(organization);
            if (result == 0) {
                throw new RuntimeException("update organization number failed.");
            }
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    @Transactional
    public ResponseCode updateDepartmentById(Department departments) {
        if (findDepartmentByOrganizationId(departments.getOrganizationId()).contains(departments.getName())) {
            return ResponseCode.DEPARTMENT_ALREADY_EXIST;
        }
        String beforeUpdateOrgId = findDepartmentById(departments.getId()).getOrganizationId();
        int result = this.departmentDao.updateDepartmentById(departments);
        if (result == 0) {
            return ResponseCode.API_UPDATE_FAILED;
        }
        if (!departments.getOrganizationId().equals(beforeUpdateOrgId)) {
            List<Department> deps = this.findChildren(departments.getId());
            for (Department d : deps) {
                d.setOrganizationId(departments.getOrganizationId());
                result = this.departmentDao.updateDepartmentById(d);
            }
        }
        if (result == 0) {
            throw new RuntimeException("update department failed.");
        }
        Organization beforeUpdateOrg = getOrganization(beforeUpdateOrgId);
        Organization organization = getOrganization(departments.getOrganizationId());
        if (!beforeUpdateOrg.getId().equals(organization.getId())) {
            beforeUpdateOrg.setNumber(beforeUpdateOrg.getNumber() - 1);
            if (this.organizationDao.updateOrganizationById(beforeUpdateOrg) == 0) {
                throw new RuntimeException("update organization number failed.");
            }
            organization.setNumber(organization.getNumber() + 1);
            if (this.organizationDao.updateOrganizationById(organization) == 0) {
                throw new RuntimeException("update organization number failed.");
            }
        }
        return ResponseCode.API_SUCCESS;
    }

    //找到部门的上级部门
    public List<Department> findFatherDepartment(String fatherId, List<Department> resultList) {
        Department department = findDepartmentById(fatherId);
        if (department != null) {
            resultList.add(department);
            findFatherDepartment(department.getFatherId(), resultList);
        }
        return resultList;
    }

    @Override
    public List<Department> findChildren(String fatherId) {
        List<Department> resultList = new ArrayList<>();
        List<Department> children = this.departmentDao.findDepartmentByFatherId(fatherId);
        if (children == null || children.size() == 0) {
            return resultList;
        }
        resultList.addAll(children);
        for (Department dep : children) {
            resultList.addAll(findChildren(dep.getId()));
        }
        return resultList;
    }

    public Organization getOrganization(String orgId) {
        return this.organizationDao.findOrganizationById(orgId);
    }

    public ResponseCode hasAuthority(String organizationId, String departmentId) {
        List<Department> departments = findDepartmentByOrganizationId(organizationId);
        List<Organization> organizations = this.organizationService.findChildren(organizationId);
        if (organizations.size() != 0) {
            for (Organization org : organizations) {
                departments.addAll(findDepartmentByOrganizationId(org.getId()));
            }
        }
        for (Department dep : departments) {
            if (dep.getId().equals(departmentId)) {
                return ResponseCode.API_SUCCESS;
            }
        }
        return ResponseCode.API_NOT_PERMISSION;
    }
}
