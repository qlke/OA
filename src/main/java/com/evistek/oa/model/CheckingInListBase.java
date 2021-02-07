package com.evistek.oa.model;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/19
 */
public class CheckingInListBase {
    private String checkType;
    private String corpId;
    private String locationResult;
    private long baseCheckTime;
    private long groupId;
    private String timeResult;
    private String userId;
    private String recordId;
    private long workDate;
    private String sourceType;
    private long userCheckTime;
    private long planId;
    private String id;
    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }
    public String getCheckType() {
        return checkType;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }
    public String getCorpId() {
        return corpId;
    }

    public void setLocationResult(String locationResult) {
        this.locationResult = locationResult;
    }
    public String getLocationResult() {
        return locationResult;
    }

    public void setBaseCheckTime(long baseCheckTime) {
        this.baseCheckTime = baseCheckTime;
    }
    public long getBaseCheckTime() {
        return baseCheckTime;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
    public long getGroupId() {
        return groupId;
    }

    public void setTimeResult(String timeResult) {
        this.timeResult = timeResult;
    }
    public String getTimeResult() {
        return timeResult;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    public String getRecordId() {
        return recordId;
    }

    public void setWorkDate(long workDate) {
        this.workDate = workDate;
    }
    public long getWorkDate() {
        return workDate;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
    public String getSourceType() {
        return sourceType;
    }

    public void setUserCheckTime(long userCheckTime) {
        this.userCheckTime = userCheckTime;
    }
    public long getUserCheckTime() {
        return userCheckTime;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }
    public long getPlanId() {
        return planId;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
}
