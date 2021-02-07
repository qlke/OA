package com.evistek.oa.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiAttendanceListRecordRequest;
import com.dingtalk.api.request.OapiAttendanceListRequest;
import com.dingtalk.api.response.OapiAttendanceListRecordResponse;
import com.dingtalk.api.response.OapiAttendanceListResponse;
import com.evistek.oa.entity.CheckingInList;
import com.evistek.oa.entity.CheckingInRecordList;
import com.evistek.oa.entity.CheckingInUser;
import com.evistek.oa.model.*;
import com.evistek.oa.service.CheckingInService;
import com.evistek.oa.service.DingCheckingInSyncService;
import com.evistek.oa.service.DingService;
import com.evistek.oa.utils.DateUtil;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/18
 */
@Service
public class DingCheckingInSyncImpl implements DingCheckingInSyncService, Runnable {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private DingService dingService;
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private List<CheckingInUser> checkingInUserList;
    private List<CheckingInList> checkingInListList;
    private List<CheckingInList> errorCheckingInListList;
    private List<CheckingInRecordList> checkingInRecordLists;
    private List<CheckingInRecordList> errorCheckingInRecordLists;
    private SimpleDateFormat simpleDateFormat;
    private boolean isNowCommitSync = false;
    private boolean isNowSync = false;
    private boolean isSync = false;
    private int dingSyncState = DING_CHECKING_SYNC_RUNNING;
    private CheckingInService checkingInService;
    private long weekSeconds = 7 * 24 * 60 * 60 * 1000;

    public DingCheckingInSyncImpl(DingService dingService, CheckingInService checkingInService) {
        this.dingService = dingService;
        this.checkingInService = checkingInService;
    }

    @PostConstruct
    public void init() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        checkingInUserList = Collections.synchronizedList(new ArrayList<>());
        checkingInListList = Collections.synchronizedList(new ArrayList<>());
        errorCheckingInListList = Collections.synchronizedList(new ArrayList<>());
        checkingInRecordLists = Collections.synchronizedList(new ArrayList<>());
        errorCheckingInRecordLists = Collections.synchronizedList(new ArrayList<>());
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        checkingInUserList.addAll(checkingInService.selectCheckingInUserList());
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this, 0, 10, TimeUnit.SECONDS);

    }

    private void refreshUserList() {
        checkingInUserList.clear();
        checkingInUserList.addAll(checkingInService.selectCheckingInUserList());
    }

    @Override
    public void run() {
        try {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            if (hour >= DING_SYNC_START_TIME && hour <= DING_SYNC_END_TIME) {
                if (hour == DING_SYNC_START_TIME && minute < DING_SYNC_START_TIME_MINUTE) {
                    isSync = false;
                } else {
                    isSync = true;
                }

            } else {
                isSync = false;
            }
            if (isSync) {
                dingSyncState = DING_CHECKING_SYNC_RUNNING;
                //System.out.println("同步中");
                if (!isNowCommitSync) {
                    isNowSync = true;
                    //检查列表更新时间
                    CheckingInUserBase checkingInUserBase = checkSyncCheckingInList();
                    if (null != checkingInUserBase && null != checkingInUserBase.getCheckingInUser() && null != checkingInUserBase.getStartTime()) {
                        syncDingCheckingInList(checkingInUserBase);
                    }
                    //检查列表详情时间
                    CheckingInUserBase checkingInUserBaseRecord = checkSyncCheckingInRecordList();
                    if (null != checkingInUserBaseRecord && null != checkingInUserBaseRecord.getCheckingInUser() && null != checkingInUserBaseRecord.getStartTime()) {
                        syncDingCheckingInRecordList(checkingInUserBaseRecord);
                    }
                    isNowSync = false;
                }
            } else {
                dingSyncState = DING_CHECKING_SYNC_STOP;
                System.out.println("未同步");
            }
        } catch (Exception e) {
            logger.error("同步异常：" + e);
            dingSyncState = DING_CHECKING_SYNC_ERROR;
        }

    }

    public CheckingInUserBase checkSyncCheckingInList() {
        CheckingInUserBase checkingInUserBase = new CheckingInUserBase();
        for (int a = 0; a < checkingInUserList.size(); a++) {
            CheckingInUser checkingInUserTmp = checkingInUserList.get(a);
            checkingInUserBase.setCheckingInUser(checkingInUserTmp);
            /*System.out.println("user:" + checkingInUserList.size());
            System.out.println("phone:" + checkingInUserTmp.getPhone());
            System.out.println("date:" + checkingInUserTmp.getListTime());*/
            try {
                if (null == checkingInUserTmp.getListTime() || checkingInUserTmp.getListTime().length() == 0) {
                    checkingInUserTmp.setListTime(getTodayBeforeDate());
                    checkingInUserBase.setStartTime(checkingInUserTmp.getListTime());
                    checkingInUserBase.setRootTime(checkingInUserBase.getStartTime());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(simpleDateFormat.parse(checkingInUserBase.getStartTime()));
                    calendar.add(Calendar.DATE, 7);
                    checkingInUserBase.setStopTime(simpleDateFormat.format(calendar.getTime()));
                    return checkingInUserBase;
                } else {
                    Calendar calendarToday = Calendar.getInstance();
                    Calendar calendarHistory = Calendar.getInstance();
                    calendarHistory.setTime(simpleDateFormat.parse(checkingInUserTmp.getListTime()));
                    calendarToday.set(Calendar.HOUR_OF_DAY, 0);
                    calendarToday.set(Calendar.MINUTE, 0);
                    calendarToday.set(Calendar.SECOND, 0);
                    calendarToday.set(Calendar.MILLISECOND, 0);
                    calendarHistory.set(Calendar.HOUR_OF_DAY, 0);
                    calendarHistory.set(Calendar.MINUTE, 0);
                    calendarHistory.set(Calendar.SECOND, 0);
                    calendarHistory.set(Calendar.MILLISECOND, 0);
                    long today = calendarToday.getTime().getTime();
                    long hisDay = calendarHistory.getTime().getTime();
                    if (today == hisDay) {
                        continue;
                    } else {
                        long tmp = today - hisDay;
                        if (tmp >= weekSeconds) {
                            checkingInUserBase.setStartTime(getDateString(calendarHistory.getTime()));
                            calendarHistory.add(Calendar.DATE, 7);
                            checkingInUserBase.setStopTime(getDateString(calendarHistory.getTime()));
                            return checkingInUserBase;
                        } else if (tmp > 0 && tmp < weekSeconds) {
                            checkingInUserBase.setStartTime(getDateString(calendarHistory.getTime()));
                            checkingInUserBase.setStopTime(getDateString(calendarToday.getTime()));
                            return checkingInUserBase;
                        } else {
                            continue;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("异常：" + e.toString());
                return null;
            }
        }
        return null;
    }


    public boolean syncDingCheckingInList(CheckingInUserBase checkingInUserBase) {
        boolean result = false;
        List<String> ddIdList = new ArrayList<>();
        ddIdList.add(checkingInUserBase.getCheckingInUser().getDdId());
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/attendance/list");
        OapiAttendanceListRequest req = new OapiAttendanceListRequest();
        req.setWorkDateFrom(checkingInUserBase.getStartTime());
        req.setWorkDateTo(checkingInUserBase.getStopTime());
        req.setUserIdList(ddIdList);
        req.setOffset(checkingInUserBase.getOffset());
        req.setLimit(50L);
        try {
            OapiAttendanceListResponse rsp = client.execute(req, dingService.getToken());
            if (rsp.getErrcode() != 0) {
                if (rsp.getErrcode() == DING_CHECKING_TOKEN_ERROR || rsp.getErrcode() != 0) {
                    dingService.refreshToken();
                }
                return false;
            }
            CheckingInListDingBase checkingInListDingBase = JSON.parseObject(rsp.getBody(), CheckingInListDingBase.class);
            if (null != checkingInListDingBase) {
                if (null == checkingInListDingBase.getRecordresult() || checkingInListDingBase.getRecordresult().size() <= 0) {
                    CheckingInUser checkingInUser = new CheckingInUser();
                    checkingInUser.setDdId(checkingInUserBase.getCheckingInUser().getDdId());
                    checkingInUser.setPhone(checkingInUserBase.getCheckingInUser().getPhone());
                    checkingInUser.setListTime(checkingInUserBase.getStopTime());
                    if (null != checkingInUserBase.getRootTime()) {
                        checkingInUser.setSyncListTime(checkingInUserBase.getRootTime());
                    }
                    updateDingUserListTime(checkingInUser);
                } else {
                    checkingInListList.addAll(checkingInListDingBase.getRecordresult());
                    if (checkingInListDingBase.getHasMore()) {
                        checkingInUserBase.setOffset(checkingInUserBase.getOffset() + 50L);
                        result = syncDingCheckingInList(checkingInUserBase);
                    }
                    result = saveCheckingInList();
                    if (result) {
                        CheckingInUser checkingInUser = new CheckingInUser();
                        checkingInUser.setDdId(checkingInUserBase.getCheckingInUser().getDdId());
                        checkingInUser.setListTime(checkingInUserBase.getStopTime());
                        checkingInUser.setPhone(checkingInUserBase.getCheckingInUser().getPhone());
                        if (null != checkingInUserBase.getRootTime()) {
                            checkingInUser.setSyncListTime(checkingInUserBase.getRootTime());
                        }
                        updateDingUserListTime(checkingInUser);
                    }
                }

            }
        } catch (Exception e) {
            logger.info("异常", e);
        }
        return result;
    }

    public CheckingInUserBase checkSyncCheckingInRecordList() {
        CheckingInUserBase checkingInUserBase = new CheckingInUserBase();
        for (int a = 0; a < checkingInUserList.size(); a++) {
            CheckingInUser checkingInUserTmp = checkingInUserList.get(a);
            try {
                checkingInUserBase.setCheckingInUser(checkingInUserTmp);
                if (null == checkingInUserTmp.getListRecordTime() || checkingInUserTmp.getListRecordTime().length() == 0) {
                    //直接同步当前日期回溯179天
                    checkingInUserTmp.setListRecordTime(getTodayBeforeDate());
                    checkingInUserBase.setStartTime(checkingInUserTmp.getListRecordTime());
                    checkingInUserBase.setRootTime(checkingInUserBase.getStartTime());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(simpleDateFormat.parse(checkingInUserBase.getStartTime()));
                    calendar.add(Calendar.DATE, 7);
                    checkingInUserBase.setStopTime(simpleDateFormat.format(calendar.getTime()));
                    return checkingInUserBase;
                } else {
                    //判断更新时间时间是否是今天
                    Calendar calendarToday = Calendar.getInstance();
                    Calendar calendarHistory = Calendar.getInstance();
                    calendarHistory.setTime(simpleDateFormat.parse(checkingInUserTmp.getListRecordTime()));
                    calendarToday.set(Calendar.HOUR_OF_DAY, 0);
                    calendarToday.set(Calendar.MINUTE, 0);
                    calendarToday.set(Calendar.SECOND, 0);
                    calendarToday.set(Calendar.MILLISECOND, 0);
                    calendarHistory.set(Calendar.HOUR_OF_DAY, 0);
                    calendarHistory.set(Calendar.MINUTE, 0);
                    calendarHistory.set(Calendar.SECOND, 0);
                    calendarHistory.set(Calendar.MILLISECOND, 0);
                    long today = calendarToday.getTime().getTime();
                    long hisDay = calendarHistory.getTime().getTime();
                    if (today == hisDay) {
                        continue;
                    } else {
                        long tmp = today - hisDay;
                        if (tmp >= weekSeconds) {
                            checkingInUserBase.setStartTime(getDateString(calendarHistory.getTime()));
                            calendarHistory.add(Calendar.DATE, 7);
                            checkingInUserBase.setStopTime(getDateString(calendarHistory.getTime()));
                            return checkingInUserBase;
                        } else if (tmp > 0 && tmp < weekSeconds) {
                            checkingInUserBase.setStartTime(getDateString(calendarHistory.getTime()));
                            checkingInUserBase.setStopTime(getDateString(calendarToday.getTime()));
                            return checkingInUserBase;
                        } else {
                            continue;
                        }
                    }
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;

    }

    public boolean syncDingCheckingInRecordList(CheckingInUserBase checkingInUserBase) {
        boolean result = false;
        List<String> userId = new ArrayList();
        userId.add(checkingInUserBase.getCheckingInUser().getDdId());
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/attendance/listRecord");
            OapiAttendanceListRecordRequest req = new OapiAttendanceListRecordRequest();
            req.setUserIds(userId);
            req.setCheckDateFrom(checkingInUserBase.getStartTime());
            req.setCheckDateTo(checkingInUserBase.getStopTime());
            OapiAttendanceListRecordResponse rsp = client.execute(req, dingService.getToken());
            if (rsp.getErrcode() != 0) {
                if (rsp.getErrcode() == DING_CHECKING_TOKEN_ERROR || rsp.getErrcode() != 0) {
                    dingService.refreshToken();
                }
                return false;
            }
            //请求成功 但是数据列表是空的 也要更新时间
            CheckingInRecordListDingBase checkingInRecordListDingBase = JSON.parseObject(rsp.getBody(), CheckingInRecordListDingBase.class);
            if (null != checkingInRecordListDingBase) {
                if (null == checkingInRecordListDingBase.getRecordresult() || checkingInRecordListDingBase.getRecordresult().size() <= 0) {
                    CheckingInUser checkingInUser = new CheckingInUser();
                    checkingInUser.setDdId(checkingInUserBase.getCheckingInUser().getDdId());
                    checkingInUser.setPhone(checkingInUserBase.getCheckingInUser().getPhone());
                    checkingInUser.setListRecordTime(checkingInUserBase.getStopTime());
                    if (null != checkingInUserBase.getRootTime()) {
                        checkingInUser.setSyncListRecordTime(checkingInUserBase.getRootTime());
                    }
                    updateDingUserListTime(checkingInUser);
                } else {
                    checkingInRecordLists.addAll(checkingInRecordListDingBase.getRecordresult());
                    result = saveCheckingInRecordList();
                    if (result) {
                        CheckingInUser checkingInUser = new CheckingInUser();
                        checkingInUser.setDdId(checkingInUserBase.getCheckingInUser().getDdId());
                        checkingInUser.setListRecordTime(checkingInUserBase.getStopTime());
                        checkingInUser.setPhone(checkingInUserBase.getCheckingInUser().getPhone());
                        if (null != checkingInUserBase.getRootTime()) {
                            checkingInUser.setSyncListRecordTime(checkingInUserBase.getRootTime());
                        }
                        updateDingUserListTime(checkingInUser);
                    }
                }

            }
        } catch (ApiException e) {
            logger.error("发生错误" + e.toString());
        } catch (Exception e) {
            logger.error("其他错误" + e.toString());
        }
        return result;
    }

    private void updateDingUserListTime(CheckingInUser checkingInUser) {
        int result = checkingInService.updateCheckingInUser(checkingInUser);
        if (result != -1) {
            refreshUserList();
        }
    }


    private boolean saveCheckingInList() {
        boolean result = true;
        int ret = checkingInService.addCheckingInListList(checkingInListList);
        if (ret != checkingInListList.size()) {
            errorCheckingInListList.addAll(checkingInListList);
        }
        checkingInListList.clear();
        return result;
    }

    private boolean saveCheckingInRecordList() {
        boolean result = true;
        int ret = checkingInService.addCheckingInRecordListList(checkingInRecordLists);
        if (ret != checkingInRecordLists.size()) {
            errorCheckingInRecordLists.addAll(checkingInRecordLists);
        }
        checkingInRecordLists.clear();
        return result;
    }

    public String getTodayBeforeDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -179);
        return getDateString(calendar.getTime());
    }

    public String getDateString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(simpleDateFormat.format(calendar.getTime()));
        return stringBuilder.toString();
    }


    @Override
    public CheckingInListDateBase syncTodayCheckingIn(String phone) {
        CheckingInListDateBase result = null;
        CheckingInUserBase checkingInUserBase = new CheckingInUserBase();
        checkingInUserBase.setPhone(phone);
        CheckingInUser checkingInUser = checkingInService.selectCheckingInUser(checkingInUserBase);
        if (null == checkingInUser) {
            return result;
        }
        checkingInUserBase.setCheckingInUser(checkingInUser);
        List<String> userId = new ArrayList();
        userId.add(checkingInUserBase.getCheckingInUser().getDdId());
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
        calendarEnd.set(Calendar.MINUTE, 0);
        calendarEnd.set(Calendar.SECOND, 0);
        calendarEnd.set(Calendar.MILLISECOND, 0);
        calendarEnd.add(Calendar.DAY_OF_YEAR, 1);
        String startTime = DateUtil.getInstance().formatDateLong(calendarStart.getTime().getTime());
        String endTime = DateUtil.getInstance().formatDateLong(calendarEnd.getTime().getTime());
        if (null == startTime || null == endTime || "error".equals(startTime) || "error".equals(endTime)) {
            return result;
        }
        Calendar calendarNow = Calendar.getInstance();
        int hourNow = calendarNow.get(Calendar.HOUR_OF_DAY);
        CheckingInListDateBase checkingInListDateBase = getCheckingDate(checkingInUser);
        if (null == checkingInListDateBase) {
            return result;
        } else {
            if (null != checkingInListDateBase.getOnDutyTimeResult() && null != checkingInListDateBase.getOffDutyTimeResult()) {
                return checkingInListDateBase;
            } else if (null != checkingInListDateBase.getOnDutyTimeResult() && hourNow < DING_SYNC_END_TIME_BASE) {
                return checkingInListDateBase;
            }
        }
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/attendance/list");
            OapiAttendanceListRequest req = new OapiAttendanceListRequest();
            req.setWorkDateFrom(startTime);
            req.setWorkDateTo(endTime);
            req.setUserIdList(userId);
            req.setOffset(checkingInUserBase.getOffset());
            req.setLimit(50L);
            System.out.println("请求：" + req.getWorkDateFrom() + req.getWorkDateTo());
            OapiAttendanceListResponse rsp = client.execute(req, dingService.getToken());
            if (rsp.getErrcode() != 0) {
                if (rsp.getErrcode() == DING_CHECKING_TOKEN_ERROR || rsp.getErrcode() != 0) {
                    dingService.refreshToken();
                }
                return result;
            }
            CheckingInListDingBase checkingInListDingBase = JSON.parseObject(rsp.getBody(), CheckingInListDingBase.class);
            if (null != checkingInListDingBase) {
                if (null != checkingInListDingBase.getRecordresult() && checkingInListDingBase.getRecordresult().size() > 0) {
                    List<CheckingInList> checkingInLists = new ArrayList<>();
                    checkingInLists.addAll(checkingInListDingBase.getRecordresult());
                    int results = checkingInService.addCheckingInListList(checkingInLists);
                    if (results != -1) {
                        result = getCheckingDate(checkingInUser);
                    }
                }

            }
        } catch (ApiException e) {
            logger.error("发生错误" + e.toString());
        } catch (Exception e) {
            logger.error("其他错误" + e.toString());
        }
        return result;
    }


    public CheckingInListDateBase getCheckingDate(CheckingInUser checkingInUser) {

        CheckingInListDateBase checkingInListDateBase = getCheckingDateUid(checkingInUser.getDdId());
        if (null != checkingInListDateBase) {
            checkingInListDateBase.setPhone(checkingInUser.getPhone());
            checkingInListDateBase.setName(checkingInUser.getName());
            checkingInListDateBase.setType(checkingInUser.getType());
        }
        return checkingInListDateBase;
    }

    public CheckingInListDateBase getCheckingDateUid(String userId) {
        CheckingInListDateBase checkingInListDateBase = null;
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);
        CheckingInListBase checkingInListBase = new CheckingInListBase();
        checkingInListBase.setUserId(userId);
        checkingInListBase.setWorkDate(calendarStart.getTime().getTime());
        checkingInListDateBase = checkingInService.selectCheckingInListDate(checkingInListBase);
        if (null != checkingInListDateBase) {
            checkingInListDateBase.setWorkDate(DateUtil.getInstance().formatDateLong(checkingInListBase.getWorkDate()));
        }
        return checkingInListDateBase;
    }


    @Override
    public void syncAddUser(CheckingInUser checkingInUser) {
        isNowCommitSync = true;
        if (!isNowSync) {
            refreshUserList();
        }
        isNowCommitSync = false;
    }

    @Override
    public int dingCheckingSyncState() {
        if (!scheduledThreadPoolExecutor.isShutdown()) {
            return dingSyncState;
        }
        return DING_CHECKING_SYNC_STOP;
    }

    @PreDestroy
    public void destroy() {
        if (!scheduledThreadPoolExecutor.isShutdown()) {
            scheduledThreadPoolExecutor.shutdown();
        }
    }
}
