package com.evistek.oa.model;

import com.evistek.oa.entity.EquipmentBindType;
import com.evistek.oa.entity.EquipmentModel;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/28
 */
public class EquipmentModelModel extends EquipmentModel {
    private List<EquipmentBindType> equipmentBindTypeList;

    public List<EquipmentBindType> getEquipmentBindTypeList() {
        return equipmentBindTypeList;
    }

    public void setEquipmentBindTypeList(List<EquipmentBindType> equipmentBindTypeList) {
        this.equipmentBindTypeList = equipmentBindTypeList;
    }
}
