package com.evistek.oa.service;

import com.evistek.oa.entity.EquipmentType;
import com.evistek.oa.model.EquipmentTypeBase;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/22
 */
public interface EquipmentTypeService {
    ResponseCode addEquipmentType(EquipmentType equipmentType);

    int updateEquipmentType(EquipmentType equipmentType);

    ResponseCode deleteEquipmentType(int id);

    ResponseCode swapEquipmentTypeId(int upId,int downId);

    EquipmentType selectEquipmentTypeById(int id);

    List<EquipmentType> selectEquipmentTypeListAll();

    List<EquipmentType> selectEquipmentTypeList(EquipmentTypeBase equipmentTypeBase);

    List<EquipmentType> selectEquipmentTypeListByMust(int must);

    List<EquipmentType> selectEquipmentTypeListCode();
}
