package com.evistek.oa.service.impl;

import com.evistek.oa.dao.DepartmentDao;
import com.evistek.oa.dao.OrganizationDao;
import com.evistek.oa.entity.Organization;
import com.evistek.oa.service.OrganizationService;
import com.evistek.oa.utils.ResponseCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/10
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    private OrganizationDao organizationDao;
    private DepartmentDao departmentDao;

    public OrganizationServiceImpl(OrganizationDao organizationDao, DepartmentDao departmentDao) {
        this.organizationDao = organizationDao;
        this.departmentDao = departmentDao;
    }

    @Override
    public List<Organization> findOrganization(String userOrganizationId) {
        List<Organization> organizations = new ArrayList<>();
        Organization organization = this.organizationDao.findOrganizationById(userOrganizationId);
        organizations.add(organization);
        organizations.addAll(findChildren(organization.getId()));
        organizations.addAll(findFatherOrg(organization.getFatherId(), new ArrayList<>()));
        return organizations;
    }

    @Override
    public Organization findOrganizationById(String id) {
        return this.organizationDao.findOrganizationById(id);
    }

    @Override
    public ResponseCode addOrganization(Organization organizations) {
        if (this.organizationDao.findOrganizationByName(organizations.getName()) != null) {
            return ResponseCode.ORGANIZATION_ALREADY_EXIST;
        }
        int result = this.organizationDao.addOrganization(organizations);
        if (result == 0) {
            return ResponseCode.API_ADD_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode deleteOrganizationById(String id) {
        if (this.departmentDao.findDepartmentByOrganizationId(id).size() != 0) {
            return ResponseCode.ORGANIZATION_NOT_DELETE_REF;
        }
        int result = this.organizationDao.deleteOrganizationById(id);
        if (result == 0) {
            return ResponseCode.API_DELETE_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode updateOrganizationById(Organization organizations) {
        Organization organization = this.organizationDao.findOrganizationByName(organizations.getName());
        if (organization != null && !organizations.getId().equals(organization.getId())) {
            return ResponseCode.ORGANIZATION_ALREADY_EXIST;
        }
        int result = this.organizationDao.updateOrganizationById(organizations);
        if (result == 0) {
            return ResponseCode.API_UPDATE_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode hasAuthority(String requestOrgId, String callerOrgId) {
        List<Organization> organizations = findChildren(callerOrgId);
        if (organizations != null || organizations.size() > 0) {
            for (Organization org : organizations) {
                if (org.getId().equals(requestOrgId)) {
                    return ResponseCode.API_SUCCESS;
                }
            }
        }
        return ResponseCode.API_NOT_PERMISSION;
    }

    @Override
    public List<Organization> findChildren(String fatherId) {
        List<Organization> resultList = new ArrayList<>();
        List<Organization> children = this.organizationDao.findOrganizationByFatherId(fatherId);
        if (children == null || children.size() == 0) {
            return resultList;
        }
        resultList.addAll(children);
        for (Organization org : children) {
            resultList.addAll(findChildren(org.getId()));
        }
        return resultList;
    }

    @Override
    public List<Organization> findFatherOrg(String fatherId, List<Organization> resultList) {
        Organization organization = this.organizationDao.findOrganizationById(fatherId);
        if (organization != null) {
            resultList.add(organization);
            findFatherOrg(organization.getFatherId(), resultList);
        }
        return resultList;
    }
}
