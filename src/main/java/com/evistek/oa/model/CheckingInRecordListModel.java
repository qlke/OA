package com.evistek.oa.model;

import com.evistek.oa.utils.DateUtil;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/27
 */
public class CheckingInRecordListModel {
    private String checkType;
    private String baseMacAddr;
    private String locationResult;
    private String isLegal;
    private String baseCheckTime;
    private String timeResult;
    private String userId;
    private String userAddress;
    private String workDate;
    private String sourceType;
    private String userCheckTime;
    private String locationMethod;
    private String planId;
    private String id;

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getBaseMacAddr() {
        return baseMacAddr;
    }

    public void setBaseMacAddr(String baseMacAddr) {
        this.baseMacAddr = baseMacAddr;
    }

    public String getLocationResult() {
        return locationResult;
    }

    public void setLocationResult(String locationResult) {
        this.locationResult = locationResult;
    }

    public String getIsLegal() {
        return isLegal;
    }

    public void setIsLegal(String isLegal) {
        this.isLegal = isLegal;
    }

    public String getBaseCheckTime() {
        return baseCheckTime;
    }

    public void setBaseCheckTime(Long baseCheckTime) {
        String date = DateUtil.getInstance().formatDateLong(baseCheckTime);
        if (null != date && !"".equals(date)) {
            this.baseCheckTime = date;
        }

    }

    public String getTimeResult() {
        return timeResult;
    }

    public void setTimeResult(String timeResult) {
        this.timeResult = timeResult;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Long workDate) {
        String date = DateUtil.getInstance().formatDateLong(workDate);
        if (null != date && !"".equals(date)) {
            this.workDate = date;
        }
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getUserCheckTime() {
        return userCheckTime;
    }

    public void setUserCheckTime(Long userCheckTime) {
        String date = DateUtil.getInstance().formatDateLong(userCheckTime);
        if (null != date && !"".equals(date)) {
            this.userCheckTime = date;
        }
    }

    public String getLocationMethod() {
        return locationMethod;
    }

    public void setLocationMethod(String locationMethod) {
        this.locationMethod = locationMethod;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
