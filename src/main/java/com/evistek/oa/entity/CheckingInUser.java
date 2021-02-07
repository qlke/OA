package com.evistek.oa.entity;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/17
 */
public class CheckingInUser {
    private String phone;
    private String ddId;
    private String name;
    private int type;
    private String createTime;
    private String syncListTime;
    private String listTime;
    private String syncListRecordTime;
    private String listRecordTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSyncListTime() {
        return syncListTime;
    }

    public void setSyncListTime(String syncListTime) {
        this.syncListTime = syncListTime;
    }

    public String getListTime() {
        return listTime;
    }

    public void setListTime(String listTime) {
        this.listTime = listTime;
    }

    public String getSyncListRecordTime() {
        return syncListRecordTime;
    }

    public void setSyncListRecordTime(String syncListRecordTime) {
        this.syncListRecordTime = syncListRecordTime;
    }

    public String getListRecordTime() {
        return listRecordTime;
    }

    public void setListRecordTime(String listRecordTime) {
        this.listRecordTime = listRecordTime;
    }


}
