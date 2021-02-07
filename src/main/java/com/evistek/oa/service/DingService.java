package com.evistek.oa.service;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/18
 */
public interface DingService {
    int DING_TOKEN_TYPE_SHANGHAI = 0;
    int DING_TOKEN_TYPE_NINGBO = 1;
    int DING_TOKEN_TYPE_WUXI = 2;

    String getToken();

    String getToken(int type);

    boolean isRefreshing();

    void refreshToken();

    void refreshToken(int type);
}
