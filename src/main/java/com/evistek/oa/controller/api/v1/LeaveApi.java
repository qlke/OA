package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.*;
import com.evistek.oa.service.*;
import com.evistek.oa.utils.*;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/27
 */
@RestController
@RequestMapping("/api/v1")
public class LeaveApi {
    private LeaveService leaveService;
    private TaskService taskService;
    private RuntimeService runtimeService;
    private EmployeeService employeeService;
    private WorkFlowService workFlowService;


    public LeaveApi(LeaveService leaveService, TaskService taskService, RuntimeService runtimeService, EmployeeService employeeService, WorkFlowService workFlowService) {
        this.leaveService = leaveService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.employeeService = employeeService;
        this.workFlowService = workFlowService;
    }

    /**
     * 查找请假申请列表
     *
     * @param token 用户token
     * @return 响应用户的申请列表/流转记录
     */
    @RequestMapping(value = "findApply", method = RequestMethod.GET)
    public ResponseBase findApply(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "token", required = true) String token
    ) {
        Employee employee = employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        return ResponseData.build(ResponseCode.API_SUCCESS,
                MapUtil.getMap(this.leaveService.findLeaveTotalByEId(employee.getId()),
                        this.leaveService.findLeaveByEId(employee.getId(), page, pageSize)));
    }

    /**
     * 请假申请
     *
     * @param leave   申请信息
     * @param token   用户token
     * @param request HTTP请求
     * @return 响应成功信息
     */
    @Operation(description = "employee apply")
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ResponseBase apply(
            @RequestBody(required = true) Leave leave,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseBase.build(this.leaveService.addLeave(leave, JwtUtil.getEmail(token)));
    }

    /**
     * 请假审批
     *
     * @param duration          请假时长
     * @param processInstanceId 流程实例id
     * @param result            审批结果
     * @param annotation        批注
     * @param token             用户token
     * @param request           HTTP请求
     * @return 响应成功信息
     */
    @Operation(description = "approve")
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public ResponseBase approve(
            @RequestParam(value = "duration", required = true) int duration,
            @RequestParam(value = "processInstanceId", required = true) String processInstanceId,
            @RequestParam(value = "result", required = true) int result,
            @RequestParam(value = "annotation", required = true) String annotation,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        String email = JwtUtil.getEmail(token);
        Employee employee = this.employeeService.findEmployeeByEmail(email);
        Map map = new HashMap();
        map.put("annotation", annotation);
        map.put("result", result);
        map.put("duration", duration);
        if (employee.getFatherId() == null) {
            return ResponseBase.build(ResponseCode.APPLY_FATHER_LEADER_IS_NULL);
        }
        map.put("email", employeeService.findEmployeeById(employee.getFatherId()).getEmail());
        ProcessInstance processInstance = this.workFlowService.approve(employee, map, processInstanceId);
        Leave leave = this.leaveService.findLeaveByProcessInstanceId(processInstanceId);
        if (null == processInstance) {
            if (result == Constant.APPLY_APPROVE_PASS) {
                leave.setStatus(Constant.APPLY_STATUS_PASS);
            } else {
                leave.setStatus(Constant.APPLY_STATUS_NOT_PASS);
            }
        }
        if (this.leaveService.updateLeaveById(leave) != ResponseCode.API_SUCCESS) {
            this.runtimeService.deleteProcessInstance(processInstanceId, email);
            return ResponseBase.build(ResponseCode.APPLY_APPROVE_FAILED);
        }
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }

    /**
     * 删除申请
     *
     * @param processInstanceId 流程实例id
     * @param token             用户token
     * @param request           HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "delete apply")
    @RequestMapping(value = "/deleteApply", method = RequestMethod.DELETE)
    public ResponseBase deleteApply(
            @RequestParam(value = "processInstanceId", required = true) String processInstanceId,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if (task != null) {
            return ResponseBase.build(ResponseCode.APPLY_UNDISSOLVED_NOT_CAN_DELETE);
        }
        return ResponseBase.build(this.leaveService.deleteLeave(processInstanceId));
    }
}
