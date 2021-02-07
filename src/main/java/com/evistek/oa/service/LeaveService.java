package com.evistek.oa.service;

import com.evistek.oa.entity.Leave;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/27
 */
public interface LeaveService {

    List<Leave> findLeaveByEId(String employeeId, int page, int pageSize);

    int findLeaveTotalByEId(String employeeId);

    ResponseCode updateLeaveById(Leave leave);

    ResponseCode deleteLeave(String processInstanceId);

    Leave findLeaveByProcessInstanceId(String processInstanceId);

    ResponseCode addLeave(Leave leave, String email);
}
