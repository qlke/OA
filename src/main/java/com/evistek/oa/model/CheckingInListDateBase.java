package com.evistek.oa.model;

import com.evistek.oa.utils.DateUtil;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/26
 */
public class CheckingInListDateBase {
    private String phone;
    private String name;
    private int type;
    private String workDate;
    private String onDutyTime;
    private String offDutyTime;
    private String onDutyTimeResult;
    private String offDutyTimeResult;
    private String onDutyRecordId;
    private String offDutyRecordId;

    public String getOnDutyRecordId() {
        return onDutyRecordId;
    }

    public void setOnDutyRecordId(String onDutyRecordId) {
        this.onDutyRecordId = onDutyRecordId;
    }

    public String getOffDutyRecordId() {
        return offDutyRecordId;
    }

    public void setOffDutyRecordId(String offDutyRecordId) {
        this.offDutyRecordId = offDutyRecordId;
    }

    public void setOnDutyTime(String onDutyTime) {
        this.onDutyTime = onDutyTime;
    }

    public void setOffDutyTime(String offDutyTime) {
        this.offDutyTime = offDutyTime;
    }

    public String getOnDutyTimeResult() {
        return onDutyTimeResult;
    }

    public void setOnDutyTimeResult(String onDutyTimeResult) {
        this.onDutyTimeResult = onDutyTimeResult;
    }

    public String getOffDutyTimeResult() {
        return offDutyTimeResult;
    }

    public void setOffDutyTimeResult(String offDutyTimeResult) {
        this.offDutyTimeResult = offDutyTimeResult;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getOnDutyTime() {
        return onDutyTime;
    }

    public void setOnDutyTime(Long onDutyTime) {
        String date = DateUtil.getInstance().formatDateLong(onDutyTime);
        if (null != date && !"".equals(date)) {
            this.onDutyTime = date;
        }
    }

    public String getOffDutyTime() {
        return offDutyTime;
    }

    public void setOffDutyTime(Long offDutyTime) {
        String date = DateUtil.getInstance().formatDateLong(offDutyTime);
        if (null != date && !"".equals(date)) {
            this.offDutyTime = date;
        }

    }


}
