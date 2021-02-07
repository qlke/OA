package com.evistek.oa.model;

import com.evistek.oa.entity.Equipment;
import com.evistek.oa.entity.EquipmentModel;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2021/1/6
 */
public class EquipmentModels extends Equipment {

    private EquipmentModelModel equipmentModelModel;

    public EquipmentModelModel getEquipmentModelModel() {
        return equipmentModelModel;
    }

    public void setEquipmentModelModel(EquipmentModelModel equipmentModelModel) {
        this.equipmentModelModel = equipmentModelModel;
    }


}
