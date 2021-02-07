package com.evistek.oa.service;

import com.evistek.oa.entity.RepairCost;
import com.evistek.oa.utils.ResponseCode;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/29
 */
public interface RepairCostService {
    RepairCost findRepairCostByRepairId(String repairId);

    RepairCost findRepairCostById(String id);

    ResponseCode addRepairCost(RepairCost repairCost);

    ResponseCode updateRepairCost(RepairCost repairCost);

    int deleteRepairCostById(String id);
}
