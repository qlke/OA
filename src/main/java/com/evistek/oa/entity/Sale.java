package com.evistek.oa.entity;

import java.util.Date;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/28
 */
public class Sale {
    private String id;
    private Date saleTime;
    private String saleNumber;
    private String saleClient;
    private String shiftToNumber;
    private String servicer;
    private String department;
    private int deductType;
    private int bookBuilding;
    private String clientOrder;
    private String remark;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private String repairId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }

    public String getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(String saleNumber) {
        this.saleNumber = saleNumber;
    }

    public String getSaleClient() {
        return saleClient;
    }

    public void setSaleClient(String saleClient) {
        this.saleClient = saleClient;
    }

    public String getShiftToNumber() {
        return shiftToNumber;
    }

    public void setShiftToNumber(String shiftToNumber) {
        this.shiftToNumber = shiftToNumber;
    }

    public String getServicer() {
        return servicer;
    }

    public void setServicer(String servicer) {
        this.servicer = servicer;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getDeductType() {
        return deductType;
    }

    public void setDeductType(int deductType) {
        this.deductType = deductType;
    }

    public int getBookBuilding() {
        return bookBuilding;
    }

    public void setBookBuilding(int bookBuilding) {
        this.bookBuilding = bookBuilding;
    }

    public String getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(String clientOrder) {
        this.clientOrder = clientOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }
}
