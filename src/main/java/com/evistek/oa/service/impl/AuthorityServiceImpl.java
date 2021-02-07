package com.evistek.oa.service.impl;

import com.evistek.oa.dao.AuthorityDao;
import com.evistek.oa.dao.DataAuthorityDao;
import com.evistek.oa.entity.Authority;
import com.evistek.oa.entity.DataAuthority;
import com.evistek.oa.service.AuthorityService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/5
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
    private AuthorityDao authorityDao;
    private DataAuthorityDao dataAuthorityDao;

    public AuthorityServiceImpl(AuthorityDao authorityDao, DataAuthorityDao dataAuthorityDao) {
        this.authorityDao = authorityDao;
        this.dataAuthorityDao = dataAuthorityDao;
    }

    @Override
    public List<Authority> findAuthorityByEId(String employeeId) {
        return this.authorityDao.findAuthorityByEId(employeeId);
    }

    @Override
    public List<Authority> findAuthorityByPId(String positionId) {
        return this.authorityDao.findAuthorityByPId(positionId);
    }

    @Override
    public List<DataAuthority> findRoleDataAuthorityByPId(String positionId) {
        return this.dataAuthorityDao.findDataAuthorityByPId(positionId);
    }
}
