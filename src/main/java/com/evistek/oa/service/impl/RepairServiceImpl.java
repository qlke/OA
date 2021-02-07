package com.evistek.oa.service.impl;

import com.evistek.oa.dao.EmployeeDao;
import com.evistek.oa.dao.RepairDao;
import com.evistek.oa.dao.SaleDao;
import com.evistek.oa.dao.SaleReturnDao;
import com.evistek.oa.entity.Material;
import com.evistek.oa.entity.RepairCost;
import com.evistek.oa.entity.Repair;
import com.evistek.oa.service.*;
import com.evistek.oa.utils.Constant;
import com.evistek.oa.utils.ResponseCode;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/25
 */
@Service
public class RepairServiceImpl implements RepairService {
    private RepairDao repairDao;
    private MaterialService materialService;
    private SettingService settingService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private RepairCostService repairCostService;
    private SaleReturnDao saleReturnDao;
    private SaleDao saleDao;
    private EmployeeDao employeeDao;
    private WorkFlowService workFlowService;

    public RepairServiceImpl(
            RepairDao repairDao, MaterialService materialService,
            SettingService settingService,
            RuntimeService runtimeService,
            TaskService taskService, RepairCostService repairCostService,
            SaleReturnDao saleReturnDao, SaleDao saleDao, EmployeeDao employeeDao, WorkFlowService workFlowService) {
        this.repairDao = repairDao;
        this.materialService = materialService;
        this.settingService = settingService;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.repairCostService = repairCostService;
        this.saleReturnDao = saleReturnDao;
        this.saleDao = saleDao;
        this.employeeDao = employeeDao;
        this.workFlowService = workFlowService;
    }

    @Override
    public List<Repair> findRepair(Map map, int page, int pageSize) {
        return this.repairDao.findRepair(map, page, pageSize);
    }

    @Override
    public Repair findRepairById(String id) {
        return this.repairDao.findRepairById(id);
    }

    @Override
    public Repair findRepairByProcessInstanceId(String processInstanceId) {
        return this.repairDao.findRepairByProcessInstanceId(processInstanceId);
    }

    @Override
    public Integer findRepairTotal(Map map) {
        return this.repairDao.findRepairTotal(map);
    }

    @Override
    @Transactional
    public ResponseCode addRepair(Repair repair) {
        this.settingService.setSetting(Constant.KEY_SALE, repair.getSalesman());
        Map map = new HashMap();
        map.put("email", repair.getCreateUser());
        ProcessInstance processInstance = this.workFlowService
                .startWorkflow(repair.getCreateUser(), Constant.BPM_DEVICE_REPAIR, map);
        if (processInstance != null) {
            //提交产品维修单
            Map newMap = new HashMap();
            newMap.put("email", repair.getSalesman());
            newMap.put("result", repair.getExpire());
            newMap.put("annotation", "申请产品维修");
            ProcessInstance pro = this.workFlowService.approve(employeeDao
                    .findEmployeeByEmail(repair.getCreateUser()), newMap, processInstance.getId());
            if (pro != null) {
                repair.setProcessInstanceId(processInstance.getId());
                if (repairDao.addRepair(repair) != 0) {
                    List<Material> materials = repair.getMaterials();
                    if (materials == null || materials.isEmpty()) {
                        return ResponseCode.API_SUCCESS;
                    }
                    if (this.materialService.material(materials, repair.getId(), null) != 0) {
                        return ResponseCode.API_SUCCESS;
                    }
                }
            }
            throw new RuntimeException("add repair failed.");
        }
        return ResponseCode.APPLY_FAILED;
    }

    @Override
    @Transactional
    public ResponseCode updateRepair(Repair repair) {
        int result = this.repairDao.updateRepair(repair);
        if (result == 0) {
            return ResponseCode.API_UPDATE_FAILED;
        }
        List<Material> materials = repair.getMaterials();
        if (materials != null && !materials.isEmpty() && materialService
                .material(repair.getMaterials(), repair.getId(), null) == 0) {
            throw new RuntimeException("update material failed.");
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public Map setAssignee(Repair repair, int result) {
        int status = repair.getStatus();
        Map map = new HashMap();
        if (status == WF_STATUS_ZERO) {
            map.put("email", this.settingService.getSetting(Constant.KEY_SALE));
        }
        if (status == WF_STATUS_ONE) {
            map.put("email", this.settingService.getSetting(Constant.KEY_PMC));
        }
        if (status == WF_STATUS_TWO || (repair.getExpire() == UNDER_WARRANTY && status == WF_STATUS_THREE)) {
            map.put("email", this.settingService.getSetting(Constant.KEY_PMC));
        }
        if (repair.getExpire() == NON_WARRANTY) {
            if (status == WF_STATUS_THREE || status == WF_STATUS_FIVE) {
                map.put("email", this.settingService.getSetting(Constant.KEY_FINANCE));
            }
            if (status == WF_STATUS_FOUR) {
                map.put("email", this.settingService.getSetting(Constant.KEY_SALE));
                map.put("result", result);
            }
            if (status == WF_STATUS_SIX) {
                map.put("email", this.settingService.getSetting(Constant.KEY_ENGINEER));
            }
            if (status == WF_STATUS_SEVEN) {
                map.put("email", this.settingService.getSetting(Constant.KEY_PMC));
            }
        }
        return map;
    }

    @Override
    @Transactional
    public ResponseCode deleteRepairById(String id) {
        Repair repair = this.repairDao.findRepairById(id);
        Task task = this.taskService.createTaskQuery()
                .processInstanceId(repair.getProcessInstanceId()).singleResult();
        if (task != null) {
            return ResponseCode.REPAIR_CAN_NOT_DELETE;
        }
        int result = this.repairDao.deleteRepairById(id);
        if (result == 0) {
            return ResponseCode.API_DELETE_FAILED;
        }
        if (!repair.getMaterials().isEmpty() && materialService.deleteMaterialByRepairId(id) == 0) {
            throw new RuntimeException("delete material failed.");
        }
        RepairCost repairCost = this.repairCostService.findRepairCostByRepairId(id);
        if (!repairCost.getMaterials().isEmpty() && repairCost != null) {
            if (repairCostService.deleteRepairCostById(repairCost.getId()) == 0) {
                throw new RuntimeException("delete repair cost failed.");
            }
        }
        if (saleReturnDao.findSaleReturnByRepairId(id) != null && saleReturnDao.deleteSaleReturnByRepairId(id) == 0) {
            throw new RuntimeException("delete saleReturn failed.");
        }
        if (this.saleDao.findSaleByRepairId(id) != null && this.saleDao.deleteSaleByRepairId(id) == 0) {
            throw new RuntimeException("delete sale failed.");
        }
        return ResponseCode.API_SUCCESS;
    }
}
