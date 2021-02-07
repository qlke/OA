package com.evistek.oa.entity;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/10
 */
public class Feedback {
    private int id = -1;
    private String feedbackNumber;
    private int feedbackType = -1;
    private String title;
    private String body;
    private int equipmentId = -1;
    private String annexUrl;
    private String softVersion;
    private int feedbackStatus;
    private String createUser;
    private String createTime;
    private String allotUser;
    private String processInstanceId;
    private ProcessTask processTask;

    public ProcessTask getProcessTask() {
        return processTask;
    }

    public void setProcessTask(ProcessTask processTask) {
        this.processTask = processTask;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeedbackNumber() {
        return feedbackNumber;
    }

    public void setFeedbackNumber(String feedbackNumber) {
        this.feedbackNumber = feedbackNumber;
    }

    public int getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(int feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getAnnexUrl() {
        return annexUrl;
    }

    public void setAnnexUrl(String annexUrl) {
        this.annexUrl = annexUrl;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public int getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(int feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAllotUser() {
        return allotUser;
    }

    public void setAllotUser(String allotUser) {
        this.allotUser = allotUser;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

}
