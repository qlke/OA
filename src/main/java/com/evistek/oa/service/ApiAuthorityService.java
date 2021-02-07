package com.evistek.oa.service;

import com.evistek.oa.entity.ApiAuthority;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/16
 */
public interface ApiAuthorityService {
    ApiAuthority findApiAuthority(String apiName, String apiMethod);
}
