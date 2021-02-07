package com.evistek.oa.model;

import com.evistek.oa.entity.EquipmentAttribute;
import com.evistek.oa.entity.EquipmentBindType;
import com.evistek.oa.entity.EquipmentType;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/24
 */
public class EquipmentBindTypeModel extends EquipmentBindType {
    private EquipmentType equipmentType;
    private EquipmentAttribute equipmentAttribute;

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public EquipmentAttribute getEquipmentAttribute() {
        return equipmentAttribute;
    }

    public void setEquipmentAttribute(EquipmentAttribute equipmentAttribute) {
        this.equipmentAttribute = equipmentAttribute;
    }
}
