package com.evistek.oa.service;

import com.evistek.oa.entity.Material;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/25
 */
public interface MaterialService {
    int material(List<Material> materials, String repairId, String repairCostId);

    int deleteMaterialByRepairId(String repairId);

    int deleteMaterialByRCId(String repairCostId);
}
