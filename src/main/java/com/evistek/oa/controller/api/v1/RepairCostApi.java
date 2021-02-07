package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.Employee;
import com.evistek.oa.entity.RepairCost;
import com.evistek.oa.service.EmployeeService;
import com.evistek.oa.service.RepairCostService;
import com.evistek.oa.utils.JwtUtil;
import com.evistek.oa.utils.ResponseBase;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.ResponseData;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/29
 */
@RestController
@RequestMapping("/api/v1")
public class RepairCostApi {
    private RepairCostService repairCostService;
    private EmployeeService employeeService;

    public RepairCostApi(RepairCostService repairCostService, EmployeeService employeeService) {
        this.repairCostService = repairCostService;
        this.employeeService = employeeService;
    }

    /**
     * 查看维修费用详细信息
     *
     * @param repairId 维修费用id
     * @param token    用户token
     * @param request  HTTP请求
     * @return 响应维修费用详细信息
     */
    @RequestMapping(value = "/findRepairCost", method = RequestMethod.GET)
    public ResponseBase findRepairCost(
            @RequestParam(value = "repairId", required = true) String repairId,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS,
                this.repairCostService.findRepairCostByRepairId(repairId));
    }

    /**
     * 添加维修费用信息
     *
     * @param repairCost 维修费用信息
     * @param token      用户token
     * @param request    HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "add repair cost")
    @RequestMapping(value = "/addRepairCost", method = RequestMethod.POST)
    public ResponseBase addRepairCost(
            @RequestBody(required = true) RepairCost repairCost,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        repairCost.setCreateUser(employee.getName());
        repairCost.setUpdateUser(employee.getName());
        return ResponseBase.build(this.repairCostService.addRepairCost(repairCost));
    }

    /**
     * 更新维修费用确认单的信息
     *
     * @param repairCost 维修费用确认单信息
     * @param token      用户token
     * @param request    HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "update repair cost")
    @RequestMapping(value = "/updateRepairCost", method = RequestMethod.PUT)
    public ResponseBase updateRepairCost(
            @RequestBody(required = true) RepairCost repairCost,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        repairCost.setUpdateUser(employee.getName());
        return ResponseBase.build(this.repairCostService.updateRepairCost(repairCost));
    }

}
