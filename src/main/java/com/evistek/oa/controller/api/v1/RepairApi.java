package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.Employee;
import com.evistek.oa.entity.Repair;
import com.evistek.oa.service.*;
import com.evistek.oa.utils.*;
import org.activiti.engine.RuntimeService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/25
 */
@RestController
@RequestMapping("/api/v1")
public class RepairApi {
    private RepairService repairService;
    private EmployeeService employeeService;
    private RuntimeService runtimeService;
    private ExportExcelUtil exportExcelUtil;
    private WorkFlowService workFlowService;

    public RepairApi(RepairService repairService, EmployeeService employeeService,
                     RuntimeService runtimeService, ExportExcelUtil exportExcelUtil,
                     WorkFlowService workFlowService) {
        this.repairService = repairService;
        this.employeeService = employeeService;
        this.runtimeService = runtimeService;
        this.exportExcelUtil = exportExcelUtil;
        this.workFlowService = workFlowService;
    }

    /**
     * 查找返修信息
     *
     * @param id       产品维修单id
     * @param page     页脚
     * @param pageSize 每页数量
     * @param token    用户token
     * @param request  HTTP请求
     * @return 响应维修信息或列表
     */
    @RequestMapping(value = "/findRepair", method = RequestMethod.GET)
    public ResponseData findRepair(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "productNumber", required = false) String productNumber,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        if (id != null) {
            return ResponseData.build(ResponseCode.API_SUCCESS, repairService.findRepairById(id));
        }
        Map map = new HashMap();
        map.put("productName", productName);
        map.put("productNumber", productNumber);
        return ResponseData.build(ResponseCode.API_SUCCESS, MapUtil.getMap(
                this.repairService.findRepairTotal(map), repairService.findRepair(map, page, pageSize)));
    }

    /**
     * 添加返修信息和物料
     *
     * @param repair  返修信息
     * @param token   用户token
     * @param request HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "add repair")
    @RequestMapping(value = "/addRepair", method = RequestMethod.POST)
    public ResponseBase addRepair(
            @RequestBody(required = true) Repair repair,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        repair.setCreateUser(employee.getEmail());
        repair.setUpdateUser(employee.getName());
        repair.setDepartment(employee.getDepartmentName());
        repair.setApplyUser(employee.getName());
        return ResponseBase.build(this.repairService.addRepair(repair));
    }

    /**
     * 编辑产品维修信息
     *
     * @param repair  产品维修信息
     * @param token   用户token
     * @param request HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "update repair")
    @RequestMapping(value = "/updateRepair", method = RequestMethod.PUT)
    public ResponseBase updateRepair(
            @RequestBody(required = true) Repair repair,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        repair.setUpdateUser(employee.getName());
        return ResponseBase.build(this.repairService.updateRepair(repair));
    }

    /**
     * 任务处理
     *
     * @param repairId   产品维修id
     * @param annotation 节点备注信息
     * @param result     是否维修
     * @param token      用户token
     * @param request    HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "dispose task")
    @RequestMapping(value = "/disposeTask", method = RequestMethod.POST)
    public ResponseBase disposeTask(
            @RequestParam(value = "repairId", required = true) String repairId,
            @RequestParam(value = "annotation", required = true) String annotation,
            @RequestParam(value = "result", required = false, defaultValue = "-1") int result,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        String email = JwtUtil.getEmail(token);
        Employee employee = this.employeeService.findEmployeeByEmail(email);
        Repair repair = this.repairService.findRepairById(repairId);
        Map map = this.repairService.setAssignee(repair, result);
        if (map == null) {
            return ResponseBase.build(ResponseCode.APPLY_APPROVE_FAILED);
        }
        map.put("annotation", annotation);
        this.workFlowService.approve(employee, map, repair.getProcessInstanceId());
        int status = repair.getStatus();
        if (status == 4 && result == 1) {
            repair.setStatus(status + 3);
        } else {
            repair.setStatus(status + 1);
        }
        if (this.repairService.updateRepair(repair) != ResponseCode.API_SUCCESS) {
            this.runtimeService.deleteProcessInstance(repair.getProcessInstanceId(), email);
            return ResponseBase.build(ResponseCode.APPLY_APPROVE_FAILED);
        }
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }

    /**
     * 删除产品返修信息
     *
     * @param id      返修id
     * @param token   用户token
     * @param request HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "delete repair")
    @RequestMapping(value = "deleteRepair", method = RequestMethod.DELETE)
    public ResponseBase deleteRepair(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseBase.build(this.repairService.deleteRepairById(id));
    }

    /**
     * 导出Excel表单
     *
     * @param id      表单id
     * @param type    表单类型
     * @param token   用户token
     * @param request HTTP请求
     * @return 响应Excel路径
     */
    @Operation(description = "export excel")
    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public ResponseBase exportExcel(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        String result = this.exportExcelUtil.buildRepairExcel(id, type);
        if (result == null) {
            return ResponseBase.build(ResponseCode.REPAIR_FORM_NO_EXIST);
        }
        return ResponseData.build(ResponseCode.API_SUCCESS, result);
    }
}
