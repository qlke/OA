package com.evistek.oa.service;

import com.evistek.oa.entity.Authority;
import com.evistek.oa.entity.DataAuthority;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/5
 */
public interface AuthorityService {
    List<Authority> findAuthorityByEId(String employeeId);

    List<Authority> findAuthorityByPId(String positionId);

    List<DataAuthority> findRoleDataAuthorityByPId(String positionId);
}
