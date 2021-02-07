package com.evistek.oa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/28
 */
public class RepairCost {
    private String id;
    private String clientName;
    private String productName;
    private String productNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date deliveryTime;
    private String salesman;
    private String contractNumber;
    private int expire;
    private String unPhenomenon;
    private String faultCause;
    private String treatmentMeasure;
    private BigDecimal logisticsCost;
    private BigDecimal packCost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String createUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private String updateUser;
    private List<Material> materials;
    private String repairId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
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

    public String getTreatmentMeasure() {
        return treatmentMeasure;
    }

    public void setTreatmentMeasure(String treatmentMeasure) {
        this.treatmentMeasure = treatmentMeasure;
    }

    public BigDecimal getLogisticsCost() {
        return logisticsCost;
    }

    public void setLogisticsCost(BigDecimal logisticsCost) {
        this.logisticsCost = logisticsCost;
    }

    public BigDecimal getPackCost() {
        return packCost;
    }

    public void setPackCost(BigDecimal packCost) {
        this.packCost = packCost;
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

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }
}
