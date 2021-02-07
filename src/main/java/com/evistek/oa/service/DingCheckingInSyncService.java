package com.evistek.oa.service;

import com.evistek.oa.entity.CheckingInUser;
import com.evistek.oa.model.CheckingInListDateBase;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/18
 */
public interface DingCheckingInSyncService {
    long DING_CHECKING_TOKEN_ERROR = 40014;

    int DING_CHECKING_SYNC_STOP = 0;
    int DING_CHECKING_SYNC_RUNNING = 1;
    int DING_CHECKING_SYNC_ERROR = 2;

    int DING_SYNC_START_TIME=9;
    int DING_SYNC_START_TIME_MINUTE=40;
    int DING_SYNC_END_TIME=22;
    int DING_SYNC_START_TIME_BASE=9;
    int DING_SYNC_END_TIME_BASE=18;
    void syncAddUser(CheckingInUser checkingInUser);

    int dingCheckingSyncState();
    CheckingInListDateBase syncTodayCheckingIn(String phone);
}
