package com.evistek.oa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/4
 */
public class AssetUseRecord {
    private int useType;
    private String assetId;
    private String receiveEmployeeId;
    private String receiveDepartmentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date receiveTime;
    private String returnEmployeeId;
    private String returnDepartmentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String returnTime;
    private int status;
    private String assetName;
    private String receiveEmployeeName;
    private String receiveDepartmentName;
    private String returnEmployeeName;
    private String returnDepartmentName;

    public int getUseType() {
        return useType;
    }

    public void setUseType(int useType) {
        this.useType = useType;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getReceiveEmployeeId() {
        return receiveEmployeeId;
    }

    public void setReceiveEmployeeId(String receiveEmployeeId) {
        this.receiveEmployeeId = receiveEmployeeId;
    }

    public String getReceiveDepartmentId() {
        return receiveDepartmentId;
    }

    public void setReceiveDepartmentId(String receiveDepartmentId) {
        this.receiveDepartmentId = receiveDepartmentId;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getReturnEmployeeId() {
        return returnEmployeeId;
    }

    public void setReturnEmployeeId(String returnEmployeeId) {
        this.returnEmployeeId = returnEmployeeId;
    }

    public String getReturnDepartmentId() {
        return returnDepartmentId;
    }

    public void setReturnDepartmentId(String returnDepartmentId) {
        this.returnDepartmentId = returnDepartmentId;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getReceiveEmployeeName() {
        return receiveEmployeeName;
    }

    public void setReceiveEmployeeName(String receiveEmployeeName) {
        this.receiveEmployeeName = receiveEmployeeName;
    }

    public String getReceiveDepartmentName() {
        return receiveDepartmentName;
    }

    public void setReceiveDepartmentName(String receiveDepartmentName) {
        this.receiveDepartmentName = receiveDepartmentName;
    }

    public String getReturnEmployeeName() {
        return returnEmployeeName;
    }

    public void setReturnEmployeeName(String returnEmployeeName) {
        this.returnEmployeeName = returnEmployeeName;
    }

    public String getReturnDepartmentName() {
        return returnDepartmentName;
    }

    public void setReturnDepartmentName(String returnDepartmentName) {
        this.returnDepartmentName = returnDepartmentName;
    }
}
