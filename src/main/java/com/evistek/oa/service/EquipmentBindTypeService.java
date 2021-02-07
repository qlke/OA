package com.evistek.oa.service;

import com.evistek.oa.entity.EquipmentBindType;
import com.evistek.oa.model.EquipmentBindTypeModel;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/24
 */
public interface EquipmentBindTypeService {
    int addEquipmentBindType(EquipmentBindType equipmentBindType);

    int addEquipmentBindTypes(List<EquipmentBindType> equipmentBindTypeList, int mid);

    int updateEquipmentBindType(EquipmentBindType equipmentBindType);

    int updateEquipmentBindTypes(List<EquipmentBindType> equipmentBindTypeList);

    int deleteEquipmentBidType(EquipmentBindType equipmentBindType);

    int deleteEquipmentBidType(int mid);

    int deleteEquipmentBindTypeTid(int tid);

    int deleteEquipmentBidTypes(List<EquipmentBindType> equipmentBindTypeList);

    List<EquipmentBindTypeModel> selectEquipmentByEid(int id);

    List<EquipmentBindType> selectEquipmentByTid(int tid);

    EquipmentBindTypeModel selectEquipmentDetail(EquipmentBindType equipmentBindType);

}
