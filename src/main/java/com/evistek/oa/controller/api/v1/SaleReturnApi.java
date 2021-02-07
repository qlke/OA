package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.Employee;
import com.evistek.oa.entity.SaleReturn;
import com.evistek.oa.service.EmployeeService;
import com.evistek.oa.service.SaleReturnService;
import com.evistek.oa.utils.JwtUtil;
import com.evistek.oa.utils.ResponseBase;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.ResponseData;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/30
 */
@RestController
@RequestMapping("/api/v1")
public class SaleReturnApi {
    private SaleReturnService saleReturnService;
    private EmployeeService employeeService;

    public SaleReturnApi(SaleReturnService saleReturnService, EmployeeService employeeService) {
        this.saleReturnService = saleReturnService;
        this.employeeService = employeeService;
    }

    /**
     * 查找销货退回单信息
     *
     * @param repairId 产品维修单id
     * @param token    用户token
     * @param request  HTTP请求
     * @return 响应产品维修单信息
     */
    @RequestMapping(value = "/findSaleReturn", method = RequestMethod.GET)
    public ResponseBase findSaleReturn(
            @RequestParam(value = "repairId", required = true) String repairId,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS,
                this.saleReturnService.findSaleReturnByRepairId(repairId));
    }

    /**
     * 添加销货退回单信息
     *
     * @param saleReturn 销货退回单信息
     * @param token      用户token
     * @param request    HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "add sale return")
    @RequestMapping(value = "/addSaleReturn", method = RequestMethod.POST)
    public ResponseBase addSaleReturn(
            @RequestBody(required = true) SaleReturn saleReturn,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        saleReturn.setCreateUser(employee.getName());
        saleReturn.setUpdateUser(employee.getName());
        //部门
        saleReturn.setDepartment(employee.getDepartmentName());
        //业务人员
        saleReturn.setServicer(employee.getName());
        return ResponseBase.build(this.saleReturnService.addSaleReturn(saleReturn));
    }

    /**
     * 编辑销货退回单信息
     *
     * @param saleReturn 销货退回单信息
     * @param token      用户token
     * @param request    HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "update sale return")
    @RequestMapping(value = "/updateSaleReturn", method = RequestMethod.PUT)
    public ResponseBase updateSaleReturn(
            @RequestBody(required = true) SaleReturn saleReturn,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        saleReturn.setUpdateUser(employee.getName());
        return ResponseBase.build(this.saleReturnService.updateSaleReturn(saleReturn));
    }
}
