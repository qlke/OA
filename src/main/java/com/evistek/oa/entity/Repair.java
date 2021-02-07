package com.evistek.oa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/25
 */
public class Repair {
    private String id;
    private String department;
    private String applyUser;
    private String clientName;
    private String productName;
    private String productNumber;
    private int expire;
    private String salesman;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reCompletionTime;
    private String serviceUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date actFinishTime;
    private String unPhenomenon;
    private String faultCause;
    private String serviceResult;
    private String imMeasure;
    private int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String createUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private String updateUser;
    private List<Material> materials;
    private String processInstanceId;
    private ProcessTask processTask;
    private int result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getReCompletionTime() {
        return reCompletionTime;
    }

    public void setReCompletionTime(Date reCompletionTime) {
        this.reCompletionTime = reCompletionTime;
    }

    public String getServiceUser() {
        return serviceUser;
    }

    public void setServiceUser(String serviceUser) {
        this.serviceUser = serviceUser;
    }

    public Date getActFinishTime() {
        return actFinishTime;
    }

    public void setActFinishTime(Date actFinishTime) {
        this.actFinishTime = actFinishTime;
    }

    public String getUnPhenomenon() {
        return unPhenomenon;
    }

    public void setUnPhenomenon(String unPhenomenon) {
        this.unPhenomenon = unPhenomenon;
    }

    public String getFaultCause() {
        return faultCause;
    }

    public void setFaultCause(String faultCause) {
        this.faultCause = faultCause;
    }

    public String getServiceResult() {
        return serviceResult;
    }

    public void setServiceResult(String serviceResult) {
        this.serviceResult = serviceResult;
    }

    public String getImMeasure() {
        return imMeasure;
    }

    public void setImMeasure(String imMeasure) {
        this.imMeasure = imMeasure;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public ProcessTask getProcessTask() {
        return processTask;
    }

    public void setProcessTask(ProcessTask processTask) {
        this.processTask = processTask;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
