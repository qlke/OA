package com.evistek.oa.model;

import com.evistek.oa.entity.CheckingInUser;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/18
 */
public class CheckingInUserBase {
    private String ddId;
    private String rootTime;
    private String startTime;
    private String stopTime;
    private String phone;
    private long offset = 0;
    private CheckingInUser checkingInUser;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDdId() {
        return ddId;
    }

    public void setDdId(String ddId) {
        this.ddId = ddId;
    }

    public String getRootTime() {
        return rootTime;
    }

    public void setRootTime(String rootTime) {
        this.rootTime = rootTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public CheckingInUser getCheckingInUser() {
        return checkingInUser;
    }

    public void setCheckingInUser(CheckingInUser checkingInUser) {
        this.checkingInUser = checkingInUser;
    }


}
