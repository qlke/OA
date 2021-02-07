package com.evistek.oa.service;

import com.evistek.oa.entity.Organization;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/10
 */
public interface OrganizationService {
    List<Organization> findOrganization(String userOrganizationId);

    Organization findOrganizationById(String id);

    ResponseCode addOrganization(Organization organizations);

    ResponseCode deleteOrganizationById(String id);

    ResponseCode updateOrganizationById(Organization organizations);

    ResponseCode hasAuthority(String requestOrgId, String callerOrgId);

    List<Organization> findChildren(String fatherId);

    List<Organization> findFatherOrg(String organizationId, List<Organization> resultList);
}
