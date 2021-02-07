package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.Employee;
import com.evistek.oa.entity.Sale;
import com.evistek.oa.service.EmployeeService;
import com.evistek.oa.service.SaleService;
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
public class SaleApi {
    private SaleService saleService;
    private EmployeeService employeeService;

    public SaleApi(SaleService saleService, EmployeeService employeeService) {
        this.saleService = saleService;
        this.employeeService = employeeService;
    }

    /**
     * 查找销货单信息
     *
     * @param repairId 产品维修单id
     * @param token    用户token
     * @param request  HTTP请求
     * @return 响应销货单信息
     */
    @RequestMapping(value = "/findSale", method = RequestMethod.GET)
    public ResponseBase findSale(
            @RequestParam(value = "repairId", required = true) String repairId,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS,
                this.saleService.findSaleByRepairId(repairId));
    }

    /**
     * 添加销货单信息
     *
     * @param sale    销货单信息
     * @param token   用户token
     * @param request HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "add sale")
    @RequestMapping(value = "/addSale", method = RequestMethod.POST)
    public ResponseBase addSale(
            @RequestBody(required = true) Sale sale,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        sale.setCreateUser(employee.getName());
        sale.setUpdateUser(employee.getName());
        //业务人员
        sale.setServicer(employee.getName());
        //部门
        sale.setDepartment(employee.getDepartmentName());
        return ResponseBase.build(this.saleService.addSale(sale));
    }

    /**
     * 编辑销货单信息
     *
     * @param sale    销货单信息
     * @param token   用户token
     * @param request HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "update sale")
    @RequestMapping(value = "/updateSale", method = RequestMethod.PUT)
    public ResponseBase updateSale(
            @RequestBody(required = true) Sale sale,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        sale.setUpdateUser(employee.getName());
        return ResponseBase.build(this.saleService.updateSale(sale));
    }
}
