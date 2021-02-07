package com.evistek.oa.service;

import com.evistek.oa.entity.Equipment;
import com.evistek.oa.model.EquipmentBase;
import com.evistek.oa.model.EquipmentBaseModel;
import com.evistek.oa.model.EquipmentModels;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/11
 */
public interface EquipmentService {
    List<Equipment> selectEquipmentList();

    ResponseCode addEquipment(Equipment equipment);

    ResponseCode updateEquipment(Equipment equipment);

    ResponseCode deleteEquipment(int id);

    EquipmentModels selectEquipmentById(int id);

    EquipmentBaseModel searchEquipment(EquipmentBase equipmentBase);

}
