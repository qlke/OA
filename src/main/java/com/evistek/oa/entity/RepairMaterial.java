package com.evistek.oa.entity;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/25
 */
public class RepairMaterial {
    private String repairId;
    private String materialId;
    private String repairCostId;

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getRepairCostId() {
        return repairCostId;
    }

    public void setRepairCostId(String repairCostId) {
        this.repairCostId = repairCostId;
    }
}
