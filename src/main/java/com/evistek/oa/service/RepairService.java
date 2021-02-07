package com.evistek.oa.service;

import com.evistek.oa.entity.Repair;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/25
 */
public interface RepairService {
    int UNDER_WARRANTY = 0;//在保修期内
    int NON_WARRANTY = 1;//在保修期外
    //流程状态
    int WF_STATUS_ZERO = 0;//销货退回或维修备案
    int WF_STATUS_ONE = 1;//物流
    int WF_STATUS_TWO = 2;//维修或检测
    int WF_STATUS_THREE = 3;//销货或财务统计
    int WF_STATUS_FOUR = 4;//维修核实
    int WF_STATUS_FIVE = 5;//财务收款
    int WF_STATUS_SIX = 6;//维修
    int WF_STATUS_SEVEN = 7;//发货

    List<Repair> findRepair(Map map, int page, int pageSize);

    Repair findRepairById(String id);

    Repair findRepairByProcessInstanceId(String processInstanceId);

    Integer findRepairTotal(Map map);

    ResponseCode addRepair(Repair repair);

    ResponseCode updateRepair(Repair repair);

    Map setAssignee(Repair repair, int result);

    ResponseCode deleteRepairById(String id);
}
