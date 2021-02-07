package com.evistek.oa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/28
 */
public class SaleReturn {
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date returnTime;
    private String returnNumber;
    private String saleClient;
    private String lastNumber;
    private String servicer;
    private String department;
    private int deductType;
    private int bookBuilding;
    private int billType;
    private String clientOrder;
    private String remark;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String createUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private String updateUser;
    private String repairId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber;
    }

    public String getSaleClient() {
        return saleClient;
    }

    public void setSaleClient(String saleClient) {
        this.saleClient = saleClient;
    }

    public String getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(String lastNumber) {
        this.lastNumber = lastNumber;
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

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
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
