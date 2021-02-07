package com.evistek.oa.service.impl;

import com.evistek.oa.dao.EquipmentTypeDao;
import com.evistek.oa.entity.EquipmentBindType;
import com.evistek.oa.entity.EquipmentType;
import com.evistek.oa.model.EquipmentTypeBase;
import com.evistek.oa.service.EquipmentBindTypeService;
import com.evistek.oa.service.EquipmentTypeService;
import com.evistek.oa.utils.ResponseCode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/22
 */
@Service
public class EquipmentTypeServiceImpl implements EquipmentTypeService {


    private EquipmentTypeDao equipmentTypeDao;

    public EquipmentTypeServiceImpl(EquipmentTypeDao equipmentTypeDao, EquipmentBindTypeService equipmentBindTypeService) {
        this.equipmentTypeDao = equipmentTypeDao;
        this.equipmentBindTypeService = equipmentBindTypeService;
    }

    private EquipmentBindTypeService equipmentBindTypeService;



    @Override
    public ResponseCode addEquipmentType(EquipmentType equipmentType) {
        equipmentType.setUsed(0);
        if (equipmentType.getSortWeight() == -1 && equipmentType.getInputType() == 0) {
            equipmentType.setSortWeight(98);
        } else {
            equipmentType.setSortWeight(99);
        }
        int result = equipmentTypeDao.addEquipmentType(equipmentType);
        if (result <= 0) {
            return ResponseCode.EQUIPMENT_TYPE_ERROR;
        }
        List<EquipmentType> equipmentTypeList = equipmentTypeDao.selectEquipmentTypeListCode();
        int sort = 1;
        for (int a = 0; a < equipmentTypeList.size(); a++) {
            EquipmentType equipmentTypeNew = new EquipmentType();
            equipmentTypeNew.setId(equipmentTypeList.get(a).getId());
            equipmentTypeNew.setSortWeight(sort);
            int ret = equipmentTypeDao.updateEquipmentType(equipmentTypeNew);
            if (ret > 0) {
                sort++;
            }
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public int updateEquipmentType(EquipmentType equipmentType) {
        EquipmentType equipmentTypeNew = new EquipmentType();
        equipmentTypeNew.setId(equipmentType.getId());
        equipmentTypeNew.setUsed(equipmentType.getUsed());
        equipmentTypeNew.setName(equipmentType.getName());
        equipmentTypeNew.setDescription(equipmentType.getDescription());
        equipmentTypeNew.setMust(equipmentType.getMust());
        if (equipmentType.getSortWeight() == -1) {
            equipmentTypeNew.setInputType(0);
            equipmentTypeNew.setSortWeight(98);
        } else if (equipmentType.getSortWeight() == 0) {
            equipmentTypeNew.setInputType(equipmentType.getInputType());
            equipmentTypeNew.setSortWeight(99);
        } else {
            equipmentTypeNew.setInputType(equipmentType.getInputType());
            equipmentTypeNew.setSortWeight(equipmentType.getSortWeight());
        }
        int result = equipmentTypeDao.updateEquipmentType(equipmentTypeNew);
        List<EquipmentType> equipmentTypeList = equipmentTypeDao.selectEquipmentTypeListCode();
        int sort = 1;
        for (int a = 0; a < equipmentTypeList.size(); a++) {
            EquipmentType equipmentTypeOld = new EquipmentType();
            equipmentTypeOld.setId(equipmentTypeList.get(a).getId());
            equipmentTypeOld.setSortWeight(sort);
            int ret = equipmentTypeDao.updateEquipmentType(equipmentTypeOld);
            if (ret > 0) {
                sort++;
            }
        }
        return 0;
    }

    @Override
    public ResponseCode deleteEquipmentType(int id) {
        List<EquipmentBindType> equipmentBindTypeList = equipmentBindTypeService.selectEquipmentByTid(id);
        if (null != equipmentBindTypeList && equipmentBindTypeList.size() > 0) {
            return ResponseCode.EQUIPMENT_TYPE_DELETE_ERROR;
        }
        EquipmentType equipmentType = equipmentTypeDao.selectEquipmentTypeById(id);
        if (null != equipmentType && equipmentType.getSortWeight() >= 99) {
            int result = equipmentTypeDao.deleteEquipmentType(id);
            if (result < 1) {
                return ResponseCode.EQUIPMENT_TYPE_DELETE_ERROR;
            } else {
                equipmentBindTypeService.deleteEquipmentBindTypeTid(id);
            }
        } else {
            return ResponseCode.EQUIPMENT_TYPE_DELETE_ERROR;
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode swapEquipmentTypeId(int upId, int downId) {
        EquipmentType equipmentTypeUp = equipmentTypeDao.selectEquipmentTypeById(upId);
        EquipmentType equipmentTypeDown = equipmentTypeDao.selectEquipmentTypeById(downId);
        if (null != equipmentTypeUp && null != equipmentTypeDown) {
            EquipmentType equipmentTypeUpNew = new EquipmentType();
            equipmentTypeUpNew.setId(equipmentTypeUp.getId());
            equipmentTypeUpNew.setSortWeight(equipmentTypeDown.getSortWeight());
            int resultUp = equipmentTypeDao.updateEquipmentType(equipmentTypeUpNew);
            if (resultUp < 1) {
                return ResponseCode.EQUIPMENT_TYPE_ERROR;
            }
            EquipmentType equipmentTypeDownNew = new EquipmentType();
            equipmentTypeDownNew.setId(equipmentTypeDown.getId());
            equipmentTypeDownNew.setSortWeight(equipmentTypeUp.getSortWeight());
            int resultDown = equipmentTypeDao.updateEquipmentType(equipmentTypeDownNew);
            if (resultDown < 1) {
                return ResponseCode.EQUIPMENT_TYPE_ERROR;
            }
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public EquipmentType selectEquipmentTypeById(int id) {
        return equipmentTypeDao.selectEquipmentTypeById(id);
    }

    @Override
    public List<EquipmentType> selectEquipmentTypeListAll() {
        return equipmentTypeDao.selectEquipmentTypeListAll();
    }

    @Override
    public List<EquipmentType> selectEquipmentTypeList(EquipmentTypeBase equipmentTypeBase) {
        return equipmentTypeDao.selectEquipmentTypeList(equipmentTypeBase);
    }

    @Override
    public List<EquipmentType> selectEquipmentTypeListByMust(int must) {
        return equipmentTypeDao.selectEquipmentTypeListByMust(must);
    }

    @Override
    public List<EquipmentType> selectEquipmentTypeListCode() {
        return equipmentTypeDao.selectEquipmentTypeListCode();
    }
}
