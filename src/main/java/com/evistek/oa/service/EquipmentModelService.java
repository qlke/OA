package com.evistek.oa.service;

import com.evistek.oa.entity.EquipmentModel;
import com.evistek.oa.model.EquipmentModelBase;
import com.evistek.oa.model.EquipmentModelBaseModel;
import com.evistek.oa.model.EquipmentModelModel;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/28
 */
public interface EquipmentModelService {
    ResponseCode addEquipmentModel(EquipmentModelModel equipmentModelModel);

    int updateEquipmentModel(EquipmentModelModel equipmentModelModel);

    int deleteEquipmentModel(int mid);

    EquipmentModelModel getEquipmentModelById(int id);

    EquipmentModelModel getEquipmentModelByMCode(String MCode);

    List<EquipmentModelModel> getEquipmentModels();

    List<EquipmentModel> getEquipmentModelsBase();

    EquipmentModelBaseModel searchEquipmentModelsBase(EquipmentModelBase equipmentModelBase);
}
