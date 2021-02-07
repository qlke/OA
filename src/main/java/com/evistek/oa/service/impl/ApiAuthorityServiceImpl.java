package com.evistek.oa.service.impl;

import com.evistek.oa.dao.ApiAuthorityDao;
import com.evistek.oa.entity.ApiAuthority;
import com.evistek.oa.service.ApiAuthorityService;
import com.evistek.oa.utils.MapUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/16
 */
@Service
public class ApiAuthorityServiceImpl implements ApiAuthorityService {
    private ApiAuthorityDao apiAuthorityDao;

    public ApiAuthorityServiceImpl(ApiAuthorityDao apiAuthorityDao) {
        this.apiAuthorityDao = apiAuthorityDao;
    }

    @Override
    public ApiAuthority findApiAuthority(String apiName, String apiMethod) {
        Map map = MapUtil.getMap(0, null);
        map.put("apiName", apiName);
        map.put("apiMethod", apiMethod);
        return this.apiAuthorityDao.findApiAuthority(map);
    }
}
