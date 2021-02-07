package com.evistek.oa.service.impl;

import com.evistek.oa.dao.EquipmentModelDao;
import com.evistek.oa.entity.EquipmentAttribute;
import com.evistek.oa.entity.EquipmentBindType;
import com.evistek.oa.entity.EquipmentModel;
import com.evistek.oa.entity.EquipmentType;
import com.evistek.oa.model.EquipmentBindTypeModel;
import com.evistek.oa.model.EquipmentModelBase;
import com.evistek.oa.model.EquipmentModelBaseModel;
import com.evistek.oa.model.EquipmentModelModel;
import com.evistek.oa.service.EquipmentAttributeService;
import com.evistek.oa.service.EquipmentBindTypeService;
import com.evistek.oa.service.EquipmentModelService;
import com.evistek.oa.service.EquipmentTypeService;
import com.evistek.oa.utils.ResponseCode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/28
 */
@Service
public class EquipmentModelServiceImpl implements EquipmentModelService {

    private EquipmentModelDao equipmentModelDao;
    private EquipmentTypeService equipmentTypeService;
    private EquipmentAttributeService equipmentAttributeService;

    public EquipmentModelServiceImpl(EquipmentModelDao equipmentModelDao, EquipmentTypeService equipmentTypeService, EquipmentAttributeService equipmentAttributeService, EquipmentBindTypeService equipmentBindTypeService) {
        this.equipmentModelDao = equipmentModelDao;
        this.equipmentTypeService = equipmentTypeService;
        this.equipmentAttributeService = equipmentAttributeService;
        this.equipmentBindTypeService = equipmentBindTypeService;
    }

    private EquipmentBindTypeService equipmentBindTypeService;


    @Override
    public ResponseCode addEquipmentModel(EquipmentModelModel equipmentModelModel) {
        List<EquipmentType> equipmentTypeListHis = equipmentTypeService.selectEquipmentTypeListCode();
        if (null == equipmentTypeListHis) {
            return ResponseCode.EQUIPMENT_MODEL_ERROR;
        }
        StringBuilder codeStringBuilder = new StringBuilder();
        List<EquipmentBindType> equipmentTypeList = equipmentModelModel.getEquipmentBindTypeList();
        for (int a = 0; a < equipmentTypeListHis.size(); a++) {
            for (int b = 0; b < equipmentTypeList.size(); b++) {
                if (equipmentTypeListHis.get(a).getId() == equipmentTypeList.get(b).getTid()) {
                    EquipmentType equipmentType = equipmentTypeService.selectEquipmentTypeById(equipmentTypeList.get(b).getTid());
                    if (null != equipmentType && equipmentType.getInputType() == 0) {
                        EquipmentAttribute equipmentAttribute = equipmentAttributeService.selectEquipmentAttributeById(equipmentTypeList.get(b).getAid());
                        if (null != equipmentAttribute) {
                            codeStringBuilder.append(equipmentAttribute.getPrefix());
                            codeStringBuilder.append(equipmentAttribute.getCode());
                            codeStringBuilder.append(equipmentAttribute.getSuffix());
                            break;
                        }
                    }
                }
            }
        }
        String code = codeStringBuilder.toString().trim().replaceAll("null", "");
        EquipmentModelModel equipmentModelHis = equipmentModelDao.getEquipmentModelByMCode(code);
        if (equipmentModelHis != null) {
            return ResponseCode.EQUIPMENT_MODEL_EXIST;
        }
        EquipmentModel equipmentModel = new EquipmentModel();
        BeanUtils.copyProperties(equipmentModelModel, equipmentModel);
        equipmentModel.setmCode(code);
        int result = equipmentModelDao.addEquipmentModel(equipmentModel);
        if (result > 0) {
            int ret = equipmentBindTypeService.addEquipmentBindTypes(equipmentTypeList, equipmentModel.getId());
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public int updateEquipmentModel(EquipmentModelModel equipmentModelModel) {
        int result = equipmentModelDao.updateEquipmentModel(equipmentModelModel);
        List<EquipmentBindType> equipmentBindTypeList = equipmentModelModel.getEquipmentBindTypeList();
        if (null != equipmentBindTypeList && equipmentBindTypeList.size() > 0) {
            for (int a = 0; a < equipmentBindTypeList.size(); a++) {
                EquipmentBindType equipmentBindType = equipmentBindTypeList.get(a);
                if (equipmentBindType.getMid() < 0 || equipmentBindType.getTid() < 0) {
                    continue;
                }

                EquipmentBindTypeModel equipmentBindTypeModel = equipmentBindTypeService.selectEquipmentDetail(equipmentBindType);
                if (null == equipmentBindTypeModel || equipmentBindTypeModel.getEquipmentType().getMust() == 0) {
                    continue;
                } else {
                    equipmentBindTypeService.updateEquipmentBindType(equipmentBindType);
                }

            }
        }
        return result;
    }

    @Override
    public int deleteEquipmentModel(int mid) {
        EquipmentModel equipmentModel = equipmentModelDao.getEquipmentModelById(mid);
        if (null == equipmentModel) {
            return -1;
        }
        int result = equipmentModelDao.deleteEquipmentModel(mid);
        if (result < 1) {
            return -1;
        }
        result = equipmentBindTypeService.deleteEquipmentBidType(mid);
        return result;
    }

    @Override
    public EquipmentModelModel getEquipmentModelById(int id) {
        return equipmentModelDao.getEquipmentModelById(id);
    }

    @Override
    public EquipmentModelModel getEquipmentModelByMCode(String MCode) {
        return equipmentModelDao.getEquipmentModelByMCode(MCode);
    }

    @Override
    public List<EquipmentModelModel> getEquipmentModels() {
        return equipmentModelDao.getEquipmentModels();
    }

    @Override
    public List<EquipmentModel> getEquipmentModelsBase() {
        return equipmentModelDao.getEquipmentModelsBase();
    }

    @Override
    public EquipmentModelBaseModel searchEquipmentModelsBase(EquipmentModelBase equipmentModelBase) {
        return equipmentModelDao.searchEquipmentModelsBase(equipmentModelBase);
    }
}
