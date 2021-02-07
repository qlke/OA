package com.evistek.oa.service.impl;

import com.evistek.oa.dao.RepairCostDao;
import com.evistek.oa.dao.RepairMaterialDao;
import com.evistek.oa.entity.Material;
import com.evistek.oa.entity.RepairCost;
import com.evistek.oa.service.MaterialService;
import com.evistek.oa.service.RepairCostService;
import com.evistek.oa.utils.ResponseCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/29
 */
@Service
public class RepairCostServiceImpl implements RepairCostService {
    private RepairCostDao repairCostDao;
    private MaterialService materialService;
    private RepairMaterialDao repairMaterialDao;

    public RepairCostServiceImpl(RepairCostDao repairCostDao,
                                 MaterialService materialService, RepairMaterialDao repairMaterialDao) {
        this.repairCostDao = repairCostDao;
        this.materialService = materialService;
        this.repairMaterialDao = repairMaterialDao;
    }

    @Override
    public RepairCost findRepairCostByRepairId(String repairId) {
        return this.repairCostDao.findRepairCostByRepairId(repairId);
    }

    @Override
    public RepairCost findRepairCostById(String id) {
        return this.repairCostDao.findRepairCostById(id);
    }

    @Override
    public ResponseCode addRepairCost(RepairCost repairCost) {
        int result = this.repairCostDao.addRepairCost(repairCost);
        if (result == 0) {
            return ResponseCode.API_ADD_FAILED;
        }
        List<Material> materials = repairCost.getMaterials();
        if (materials != null && !materials.isEmpty() && this.materialService
                .material(materials, null, repairCost.getId()) == 0) {
            throw new RuntimeException("add material failed.");
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    @Transactional
    public ResponseCode updateRepairCost(RepairCost repairCost) {
        int result = this.repairCostDao.updateRepairCost(repairCost);
        if (result == 0) {
            return ResponseCode.API_UPDATE_FAILED;
        }
        List<Material> materials = repairCost.getMaterials();
        if (materials != null && !materials.isEmpty() && materialService
                .material(materials, null, repairCost.getId()) == 0) {
            throw new RuntimeException("update material failed.");
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public int deleteRepairCostById(String id) {
        int result = this.repairCostDao.deleteRepairCostById(id);
        if (result != 0) {
            result = this.materialService.deleteMaterialByRCId(id);
        }
        if (result == 0) {
            return result;
        }
        return this.repairMaterialDao.deleteRMByRepairCostId(id);
    }
}
