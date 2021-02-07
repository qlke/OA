package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.Employee;
import com.evistek.oa.entity.Feedback;
import com.evistek.oa.model.FeedbackBase;
import com.evistek.oa.model.FeedbackModel;
import com.evistek.oa.service.*;
import com.evistek.oa.utils.*;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/11
 */
@RestController
@RequestMapping("/api/v1")
public class FeedbackApi {

    private FeedbackService feedbackService;
    private FeedbackTypeService feedbackTypeService;
    private WorkFlowService workFlowService;
    private RuntimeService runtimeService;
    private EmployeeService employeeService;
    private String allotUser;
    private String solveUser;

    public FeedbackApi(FeedbackService feedbackService, FeedbackTypeService feedbackTypeService, WorkFlowService workFlowService, RuntimeService runtimeService, EmployeeService employeeService) {
        this.feedbackService = feedbackService;
        this.feedbackTypeService = feedbackTypeService;
        this.workFlowService = workFlowService;
        this.runtimeService = runtimeService;
        this.employeeService = employeeService;
    }

    @Operation(description = "add feedback")
    @RequestMapping(value = "addFeedback", method = RequestMethod.POST)
    public ResponseBase addFeedBack(
            @RequestParam(value = "token") String token,
            @RequestBody Feedback feedback
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        feedback.setCreateUser(employee.getName());
        return ResponseBase.build(feedbackService.addFeedback(feedback));
    }

    @Operation(description = "update feedback")
    @RequestMapping(value = "updateFeedback", method = RequestMethod.PUT)
    public ResponseBase updateFeedbackPid(
            @RequestParam(value = "token") String token,
            @RequestBody Feedback feedback
    ) {
        int result = feedbackService.updateFeedbackById(feedback);
        if (result != 1) {
            return ResponseBase.build(ResponseCode.API_FEEDBACK_FAILED);
        }
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }

    /**
     * 查看缺陷记录详情
     *
     * @param token
     * @param id
     * @param feedbackNumber
     * @param processInstanceId
     * @return
     */
    @RequestMapping(value = "feedbackRecord", method = RequestMethod.GET)
    public ResponseBase selectFeedbackRecord
    (@RequestParam(value = "token") String token,
     @RequestParam(value = "id", defaultValue = "-1", required = false) int id,
     @RequestParam(value = "feedbackNumber", required = false) String feedbackNumber,
     @RequestParam(value = "processInstanceId", required = false) String processInstanceId) {
        if (id <= -1 && null == feedbackNumber && null == processInstanceId) {
            return ResponseBase.build(ResponseCode.API_FEEDBACK_FAILED);
        }
        FeedbackBase feedbackBase = new FeedbackBase();
        feedbackBase.setId(id);
        feedbackBase.setFeedbackNumber(feedbackNumber);
        feedbackBase.setProcessInstanceId(processInstanceId);
        FeedbackModel feedbackModel = feedbackService.selectFeedbackById(feedbackBase);
        return ResponseData.build(ResponseCode.API_SUCCESS, feedbackModel);
    }

    /**
     * 查询缺陷记录列表
     *
     * @param token
     * @param allotUser    分配人
     * @param createUser   创建人
     * @param title        反馈标题
     * @param body         反馈内容
     * @param softVersion  软件版本
     * @param equipmentId  设备id
     * @param feedbackType 反馈类型
     * @param page         页码
     * @param pageNumber   每页数量
     * @return
     */
    @RequestMapping(value = "searchFeedback", method = RequestMethod.GET)
    public ResponseBase selectFeedbackList(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "allotUser", required = false) String allotUser,
            @RequestParam(value = "createUser", required = false) String createUser,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "body", required = false) String body,
            @RequestParam(value = "softVersion", required = false) String softVersion,
            @RequestParam(value = "equipmentId", defaultValue = "-1", required = false) int equipmentId,
            @RequestParam(value = "feedbackType", defaultValue = "-1", required = false) int feedbackType,
            @RequestParam(value = "feedbackNumber", required = false) String feedbackNumber,
            @RequestParam(value = "page", defaultValue = "-1", required = false) int page,
            @RequestParam(value = "pageNumber", defaultValue = "-1", required = false) int pageNumber
    ) {
        FeedbackBase feedbackBase = new FeedbackBase();
        feedbackBase.setCreateUser(createUser);
        feedbackBase.setAllotUser(allotUser);
        feedbackBase.setTitle(title);
        feedbackBase.setBody(body);
        feedbackBase.setSoftVersion(softVersion);
        feedbackBase.setEquipmentId(equipmentId);
        feedbackBase.setFeedbackType(feedbackType);
        feedbackBase.setFeedbackNumber(feedbackNumber);
        feedbackBase.setLimit(page, pageNumber);
        List<Feedback> feedbackList = feedbackService.selectFeedbackList(feedbackBase);
        return ResponseData.build(ResponseCode.API_SUCCESS,
                MapUtil.getMap(this.feedbackService.findFeedbackTotal(feedbackBase), feedbackList));
    }

    @RequestMapping(value = "getFeedbackTypes", method = RequestMethod.GET)
    public ResponseBase selectFeedbackList(@RequestParam(value = "token") String token) {
        return ResponseData.build(ResponseCode.API_SUCCESS, feedbackTypeService.selectFeedbackListModel());
    }

    @Operation(description = "delete feedback")
    @RequestMapping(value = "deleteFeedback", method = RequestMethod.DELETE)
    public ResponseBase deleteFeedback(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "id", defaultValue = "-1") int feedbackId) {
        if (feedbackId < 0) {
            return ResponseBase.build(ResponseCode.API_FEEDBACK_FAILED);
        }
        FeedbackBase feedbackBase = new FeedbackBase();
        feedbackBase.setId(feedbackId);
        int result = feedbackService.deleteFeedback(feedbackBase);
        if (result != 1) {
            return ResponseBase.build(ResponseCode.API_FEEDBACK_FAILED);
        }
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }

    /**
     * 缺陷记录模块流程任务处理
     *
     * @param id         缺陷记录id
     * @param email      用户邮箱
     * @param result     解决结果
     * @param annotation 备注信息
     * @param token      用户token
     * @param request    HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "dispose task")
    @RequestMapping(value = "/dispose", method = RequestMethod.POST)
    public ResponseBase dispose(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "result", required = false) int result,
            @RequestParam(value = "annotation", required = true) String annotation,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        Employee employee = this.employeeService.findEmployeeByEmail(JwtUtil.getEmail(token));
        FeedbackBase feedbackBase = new FeedbackBase();
        feedbackBase.setId(id);
        Feedback feedback = this.feedbackService.selectFeedbackById(feedbackBase);
        Map map = new HashMap();
        map.put("annotation", annotation);
        map.put("result", result);
        int status = feedback.getFeedbackStatus();
        if (status == 0) {
            //分配问题
            map.put("email", email);
            allotUser = employee.getEmail();
            solveUser = email;
        } else if (status == 1) {
            //解决问题
            map.put("email", allotUser);
        } else if (status == 2) {
            //审核问题
            map.put("email", solveUser);
        } else {
            return ResponseBase.build(ResponseCode.APPLY_APPROVE_FAILED);
        }
        ProcessInstance instance = this.workFlowService.approve(employee, map, feedback.getProcessInstanceId());
        if (instance == null) {
            if (result == 0) {
                feedback.setFeedbackStatus(4);
            } else {
                feedback.setFeedbackStatus(3);
            }
        } else {
            if (result == 1) {
                feedback.setFeedbackStatus(1);
            } else {
                feedback.setFeedbackStatus(status + 1);
            }
        }
        int res = this.feedbackService.updateFeedbackByProcessId(feedback);
        if (res == 0) {
            this.runtimeService.deleteProcessInstance(instance.getId(), employee.getName());
            return ResponseBase.build(ResponseCode.APPLY_APPROVE_FAILED);
        }
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }
}
