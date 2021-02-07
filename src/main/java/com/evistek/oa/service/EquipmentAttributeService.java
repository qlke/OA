package com.evistek.oa.service;

import com.evistek.oa.entity.EquipmentAttribute;
import com.evistek.oa.model.EquipmentAttributeBase;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/22
 */
public interface EquipmentAttributeService {
    int addEquipmentAttribute(EquipmentAttribute equipmentAttribute);
    int updateEquipmentAttribute(EquipmentAttribute equipmentAttribute);
    ResponseCode deleteEquipmentAttribute(int id);
    EquipmentAttribute selectEquipmentAttributeById(int id);
    List<EquipmentAttribute> selectEquipmentAttributeList(EquipmentAttributeBase equipmentAttributeBase);
    List<EquipmentAttribute> selectEquipmentAttributeListAll();

}
