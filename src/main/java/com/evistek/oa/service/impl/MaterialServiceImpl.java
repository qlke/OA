package com.evistek.oa.service.impl;

import com.evistek.oa.dao.MaterialDao;
import com.evistek.oa.dao.RepairMaterialDao;
import com.evistek.oa.entity.Material;
import com.evistek.oa.service.MaterialService;
import com.evistek.oa.utils.MapUtil;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.UuidUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/25
 */
@Service
public class MaterialServiceImpl implements MaterialService {
    private MaterialDao materialDao;
    private RepairMaterialDao repairMaterialDao;

    public MaterialServiceImpl(MaterialDao materialDao, RepairMaterialDao repairMaterialDao) {
        this.materialDao = materialDao;
        this.repairMaterialDao = repairMaterialDao;
    }

    @Override
    public int material(List<Material> materials, String repairId, String repairCostId) {
        int result = 0;
        List<String> list = new ArrayList<>();
        for (Material r : materials) {
            if (r.getId() == null) {
                r.setId(UuidUtil.getUUID());
            }
            list.add(r.getId());
        }
        if (!this.materialDao.findMaterialByIds(list).isEmpty()) {
            result = this.materialDao.deleteMaterialByIds(list);
            if (result == 0) {
                return result;
            }
        }
        result = this.materialDao.addMaterial(materials);
        if (result == 0) {
            return result;
        }
        Map map = MapUtil.getMap(0, list);
        if (repairId != null && !repairMaterialDao.findRMByRepairId(repairId).isEmpty()) {
            result = this.repairMaterialDao.deleteRMByRepairId(repairId);
        }
        if (repairId != null) {
            map.put("repairId", repairId);
        }
        if (repairCostId != null && !repairMaterialDao.findRMByRepairCostId(repairCostId).isEmpty()) {
            result = this.repairMaterialDao.deleteRMByRepairCostId(repairCostId);
        }
        if (repairCostId != null) {
            map.put("repairCostId", repairCostId);
        }
        if (result == 0) {
            return 0;
        }
        return this.repairMaterialDao.addRepairMaterial(map);
    }

    @Override
    public int deleteMaterialByRepairId(String repairId) {
        int result = this.materialDao.deleteMaterialByRepairId(repairId);
        if (result == 0) {
            return result;
        }
        return this.repairMaterialDao.deleteRMByRepairId(repairId);
    }

    @Override
    public int deleteMaterialByRCId(String repairCostId) {
        int result = this.materialDao.deleteMaterialByRCId(repairCostId);
        if (result == 0) {
            return result;
        }
        return this.repairMaterialDao.deleteRMByRepairCostId(repairCostId);
    }
}
