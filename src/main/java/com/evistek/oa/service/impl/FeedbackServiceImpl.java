package com.evistek.oa.service.impl;

import com.evistek.oa.dao.EmployeeDao;
import com.evistek.oa.dao.FeedbackDao;
import com.evistek.oa.dao.FeedbackTypeDao;
import com.evistek.oa.entity.Employee;
import com.evistek.oa.entity.Feedback;
import com.evistek.oa.entity.FeedbackType;
import com.evistek.oa.model.FeedbackBase;
import com.evistek.oa.model.FeedbackModel;
import com.evistek.oa.service.FeedbackService;
import com.evistek.oa.service.WorkFlowService;
import com.evistek.oa.utils.CodeUtil;
import com.evistek.oa.utils.Constant;
import com.evistek.oa.utils.ResponseCode;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/14
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    private FeedbackDao feedbackDao;
    private EmployeeDao employeeDao;
    private FeedbackTypeDao feedbackTypeDao;
    private RuntimeService runtimeService;
    private WorkFlowService workFlowService;

    public FeedbackServiceImpl(FeedbackDao feedbackDao, EmployeeDao employeeDao, FeedbackTypeDao feedbackTypeDao, RuntimeService runtimeService, WorkFlowService workFlowService) {
        this.feedbackDao = feedbackDao;
        this.employeeDao = employeeDao;
        this.feedbackTypeDao = feedbackTypeDao;
        this.runtimeService = runtimeService;
        this.workFlowService = workFlowService;
    }


    @Override
    @Transactional
    public ResponseCode addFeedback(Feedback feedback) {
        FeedbackType feedbackType = feedbackTypeDao.selectFeedbackTypeById(feedback.getFeedbackType());
        if (null != feedbackType && null != feedbackType.getNumberPrefix() && !"".equals(feedbackType.getNumberPrefix())) {
            feedback.setFeedbackNumber(CodeUtil.getInstance().getCodeString(feedbackType.getNumberPrefix()));
        } else {
            feedback.setFeedbackNumber(CodeUtil.getInstance().getCodeString("UNKNOWN-"));
        }
        String pid = startWorkflow(feedback);
        if (pid == null) {
            return ResponseCode.APPLY_FAILED;
        }
        feedback.setProcessInstanceId(pid);
        if (feedbackDao.addFeedback(feedback) == 0) {
            runtimeService.deleteProcessInstance(feedback.getProcessInstanceId(), feedback.getCreateUser());
        }
        return ResponseCode.API_SUCCESS;
    }

    private String startWorkflow(Feedback feedback) {
        Map map = new HashMap<>();
        map.put("email", feedback.getAllotUser());
        ProcessInstance processInstance = this.workFlowService
                .startWorkflow(feedback.getCreateUser(), Constant.BPM_RECORD_TASK, map);
        if (processInstance != null) {
            //提交问题
            map.put("annotation", feedback.getTitle());
            ProcessInstance pro = this.workFlowService.approve(employeeDao
                    .findEmployeeByEmail(feedback.getAllotUser()), map, processInstance.getId());
            if (pro != null) {
                return pro.getId();
            }
            //取消开启的工作流
            runtimeService.deleteProcessInstance(processInstance.getId(), feedback.getCreateUser());
        }
        return null;
    }

    @Override
    public int updateFeedbackByProcessId(Feedback feedback) {
        return feedbackDao.updateFeedbackByProcessId(feedback);
    }

    @Override
    public int updateFeedbackById(Feedback feedback) {
        String pid = startWorkflow(feedback);
        int result = 0;
        if (pid == null) {
            return result;
        }
        feedback.setProcessInstanceId(pid);
        feedback.setFeedbackStatus(0);
        if ((result = feedbackDao.updateFeedbackById(feedback)) == 0) {
            runtimeService.deleteProcessInstance(feedback.getProcessInstanceId(), feedback.getCreateUser());
        }
        return result;
    }

    @Override
    public int deleteFeedback(FeedbackBase feedbackBase) {
        return feedbackDao.deleteFeedback(feedbackBase);
    }

    @Override
    public List<Feedback> selectFeedbackList(FeedbackBase feedbackBase) {
        return feedbackDao.selectFeedbackList(feedbackBase);
    }

    @Override
    public FeedbackModel selectFeedbackById(FeedbackBase feedbackBase) {
        FeedbackModel feedbackModel = feedbackDao.selectFeedbackById(feedbackBase);
        Employee employeesCreate = employeeDao.findEmployeeByEmail(feedbackModel.getCreateUser());
        if (null != employeesCreate) {
            feedbackModel.setCreateUserName(employeesCreate.getName());
        }
        Employee employeesAllot = employeeDao.findEmployeeByEmail(feedbackModel.getAllotUser());
        if (null != employeesAllot) {
            feedbackModel.setAllotUserName(employeesAllot.getName());
        }
        return feedbackModel;
    }

    @Override
    public Integer findFeedbackTotal(FeedbackBase feedbackBase) {
        return this.feedbackDao.findFeedbackTotal(feedbackBase);
    }


}
