package com.evistek.oa.model;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2021/1/18
 */
public class EquipmentBaseModel {
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<EquipmentModels> getEquipmentModelsList() {
        return equipmentModelsList;
    }

    public void setEquipmentModelsList(List<EquipmentModels> equipmentModelsList) {
        this.equipmentModelsList = equipmentModelsList;
    }

    private List<EquipmentModels> equipmentModelsList;
}
