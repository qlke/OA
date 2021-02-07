package com.evistek.oa.model;

import com.evistek.oa.entity.EquipmentModel;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2021/1/27
 */
public class EquipmentModelBaseModel {
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<EquipmentModel> getEquipmentModelList() {
        return equipmentModelList;
    }

    public void setEquipmentModelList(List<EquipmentModel> equipmentModelList) {
        this.equipmentModelList = equipmentModelList;
    }

    private List<EquipmentModel> equipmentModelList;
}
