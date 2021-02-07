package com.evistek.oa.service.impl;

import com.evistek.oa.dao.EmployeeDao;
import com.evistek.oa.dao.LeaveDao;
import com.evistek.oa.entity.*;
import com.evistek.oa.service.LeaveService;
import com.evistek.oa.service.WorkFlowService;
import com.evistek.oa.utils.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/27
 */
@Service
public class LeaveServiceImpl implements LeaveService {
    private LeaveDao leaveDao;
    private EmployeeDao employeeDao;
    private WorkFlowService workFlowService;

    public LeaveServiceImpl(LeaveDao leaveDao, EmployeeDao employeeDao, WorkFlowService workFlowService) {
        this.leaveDao = leaveDao;
        this.employeeDao = employeeDao;
        this.workFlowService = workFlowService;
    }

    @Override
    public List<Leave> findLeaveByEId(String employeeId, int page, int pageSize) {
        return this.leaveDao.findLeaveByEId(employeeId, page, pageSize);
    }

    @Override
    public int findLeaveTotalByEId(String employeeId) {
        return this.leaveDao.findLeaveTotalByEId(employeeId);
    }

    @Override
    public ResponseCode updateLeaveById(Leave leave) {
        int result = this.leaveDao.updateLeaveById(leave);
        if (result == 0) {
            return ResponseCode.APPLY_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode deleteLeave(String processInstanceId) {
        int result = this.leaveDao.deleteLeave(processInstanceId);
        if (result == 0) {
            return ResponseCode.API_DELETE_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public Leave findLeaveByProcessInstanceId(String processInstanceId) {
        return this.leaveDao.findLeaveByProcessInstanceId(processInstanceId);
    }

    @Override
    @Transactional
    public ResponseCode addLeave(Leave leave, String email) {
        Employee employee = employeeDao.findEmployeeByEmail(email);
        leave.setEmployeeId(employee.getId());
        leave.setDepartment(employee.getDepartmentName());
        Map map = new HashMap();
        if (employee.getFatherEmployeeName() == null) {
            return ResponseCode.APPLY_FATHER_LEADER_IS_NULL;
        }
        //设置下个节点的审批人
        map.put("email", employee.getEmail());
        //开启业务流
        ProcessInstance processInstance = this.workFlowService.startWorkflow(email, Constant.BPM_LEAVE_APPLY, map);
        if (processInstance == null) {
            return ResponseCode.APPLY_FAILED;
        }
        //提交申请
        Map newMap = new HashMap();
        newMap.put("result", Constant.APPLY_APPROVE_PASS);
        newMap.put("annotation", leave.getType());
        newMap.put("position", employee.getPositions().get(0).getIdentify());
        newMap.put("email", employeeDao.findEmployeeById(employee.getFatherId()).getEmail());
        ProcessInstance pro = this.workFlowService.approve(employee, newMap, processInstance.getId());
        if (pro != null) {
            leave.setProcessInstanceId(pro.getId());
            if (this.leaveDao.addLeave(leave) != 0) {
                return ResponseCode.API_SUCCESS;
            }
        }
        throw new RuntimeException("add leave failed.");
    }
}
