package com.evistek.oa.service.impl;

import com.evistek.oa.dao.EquipmentAttributeDao;
import com.evistek.oa.entity.EquipmentAttribute;
import com.evistek.oa.entity.EquipmentBindType;
import com.evistek.oa.entity.EquipmentType;
import com.evistek.oa.model.EquipmentAttributeBase;
import com.evistek.oa.service.EquipmentAttributeService;
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
public class EquipmentAttributeServiceImpl implements EquipmentAttributeService {

    private EquipmentAttributeDao equipmentAttributeDao;
    private EquipmentTypeService equipmentTypeService;

    public EquipmentAttributeServiceImpl(EquipmentAttributeDao equipmentAttributeDao, EquipmentTypeService equipmentTypeService, EquipmentBindTypeService equipmentBindTypeService) {
        this.equipmentAttributeDao = equipmentAttributeDao;
        this.equipmentTypeService = equipmentTypeService;
        this.equipmentBindTypeService = equipmentBindTypeService;
    }

    private EquipmentBindTypeService equipmentBindTypeService;

    @Override
    public int addEquipmentAttribute(EquipmentAttribute equipmentAttribute) {
        EquipmentType equipmentType = equipmentTypeService.selectEquipmentTypeById(equipmentAttribute.getTid());
        if (null != equipmentType) {
            equipmentAttribute.settName(equipmentType.getName());
        } else {
            equipmentAttribute.settName("error");
        }
        return equipmentAttributeDao.addEquipmentAttribute(equipmentAttribute);
    }

    @Override
    public int updateEquipmentAttribute(EquipmentAttribute equipmentAttribute) {
        return equipmentAttributeDao.updateEquipmentAttribute(equipmentAttribute);
    }

    @Override
    public ResponseCode deleteEquipmentAttribute(int id) {
        EquipmentAttribute equipmentAttribute = equipmentAttributeDao.selectEquipmentAttributeById(id);
        if (null == equipmentAttribute) {
            return ResponseCode.EQUIPMENT_TYPE_DELETE_ERROR;
        } else {
            List<EquipmentBindType> equipmentBindTypeList = equipmentBindTypeService.selectEquipmentByTid(equipmentAttribute.getTid());
            if (null != equipmentBindTypeList && equipmentBindTypeList.size() > 0) {
                return ResponseCode.EQUIPMENT_TYPE_DELETE_ERROR;
            }
        }
        int result = equipmentAttributeDao.deleteEquipmentAttribute(id);
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public EquipmentAttribute selectEquipmentAttributeById(int id) {
        return equipmentAttributeDao.selectEquipmentAttributeById(id);
    }

    @Override
    public List<EquipmentAttribute> selectEquipmentAttributeList(EquipmentAttributeBase equipmentAttributeBase) {
        return equipmentAttributeDao.selectEquipmentAttributeList(equipmentAttributeBase);
    }

    @Override
    public List<EquipmentAttribute> selectEquipmentAttributeListAll() {
        return equipmentAttributeDao.selectEquipmentAttributeListAll();
    }
}
