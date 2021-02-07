package com.evistek.oa.service.impl;

import com.evistek.oa.dao.EquipmentDao;
import com.evistek.oa.entity.Equipment;
import com.evistek.oa.model.EquipmentBase;
import com.evistek.oa.model.EquipmentBaseModel;
import com.evistek.oa.model.EquipmentModels;
import com.evistek.oa.service.EquipmentService;
import com.evistek.oa.utils.ResponseCode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/15
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {
    public EquipmentServiceImpl(EquipmentDao equipmentDao) {
        this.equipmentDao = equipmentDao;
    }

    private EquipmentDao equipmentDao;

    @Override
    public List<Equipment> selectEquipmentList() {
        return equipmentDao.selectEquipmentList();
    }

    @Override
    public ResponseCode addEquipment(Equipment equipment) {
        int result = equipmentDao.addEquipment(equipment);
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode updateEquipment(Equipment equipment) {
        int result = equipmentDao.updateEquipment(equipment);
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode deleteEquipment(int id) {
        int result = equipmentDao.deleteEquipment(id);
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public EquipmentModels selectEquipmentById(int id) {
        return equipmentDao.selectEquipmentById(id);
    }

    @Override
    public EquipmentBaseModel searchEquipment(EquipmentBase equipmentBase) {
        return equipmentDao.searchEquipment(equipmentBase);
    }

}
