package com.evistek.oa.service.impl;

import com.evistek.oa.dao.EquipmentBindTypeDao;
import com.evistek.oa.entity.EquipmentBindType;
import com.evistek.oa.model.EquipmentBindTypeModel;
import com.evistek.oa.service.EquipmentBindTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/25
 */
@Service
public class EquipmentBindTypeServiceImpl implements EquipmentBindTypeService {


    private EquipmentBindTypeDao equipmentBindTypeDao;

    public EquipmentBindTypeServiceImpl(EquipmentBindTypeDao equipmentBindTypeDao) {
        this.equipmentBindTypeDao = equipmentBindTypeDao;
    }

    @Override
    public int addEquipmentBindType(EquipmentBindType equipmentBindType) {
        EquipmentBindType equipmentBindTypeHis = equipmentBindTypeDao.getEquipmentBindType(equipmentBindType);
        if (null != equipmentBindTypeHis) {
            return -1;
        }
        return equipmentBindTypeDao.addEquipmentBindType(equipmentBindType);
    }

    @Override
    public int addEquipmentBindTypes(List<EquipmentBindType> equipmentBindTypeList, int mid) {
        int result = 0;
        for (int a = 0; a < equipmentBindTypeList.size(); a++) {
            EquipmentBindType equipmentBindType = equipmentBindTypeList.get(a);
            equipmentBindType.setMid(mid);
            if (equipmentBindType.getAid()>0||null!=equipmentBindType.gettValue()){
                int ret = addEquipmentBindType(equipmentBindType);
                if (ret > 0) {
                    result++;
                }
            }

        }
        return result;
    }

    @Override
    public int updateEquipmentBindType(EquipmentBindType equipmentBindType) {
        return equipmentBindTypeDao.updateEquipmentBindType(equipmentBindType);
    }

    @Override
    public int updateEquipmentBindTypes(List<EquipmentBindType> equipmentBindTypeList) {
        return 0;
    }

    @Override
    public int deleteEquipmentBidType(EquipmentBindType equipmentBindType) {
        return equipmentBindTypeDao.deleteEquipmentBindType(equipmentBindType);
    }

    @Override
    public int deleteEquipmentBidType(int mid) {
        return equipmentBindTypeDao.deleteEquipmentBindType(mid);
    }

    @Override
    public int deleteEquipmentBindTypeTid(int tid) {
        return equipmentBindTypeDao.deleteEquipmentBindTypeTid(tid);
    }

    @Override
    public int deleteEquipmentBidTypes(List<EquipmentBindType> equipmentBindTypeList) {
        for (int a = 0; a < equipmentBindTypeList.size(); a++) {
            deleteEquipmentBidType(equipmentBindTypeList.get(a));
        }
        return 0;
    }

    @Override
    public List<EquipmentBindTypeModel> selectEquipmentByEid(int id) {
        return equipmentBindTypeDao.getEquipmentBindType(id);
    }

    @Override
    public List<EquipmentBindType> selectEquipmentByTid(int tid) {
        return equipmentBindTypeDao.getEquipmentBindTypeTid(tid);
    }

    @Override
    public EquipmentBindTypeModel selectEquipmentDetail(EquipmentBindType equipmentBindType) {
        return equipmentBindTypeDao.getEquipmentBindTypeDetail(equipmentBindType);
    }
}
