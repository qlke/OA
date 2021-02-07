package com.evistek.oa.controller.api.v1;

import com.evistek.oa.entity.Feedback;
import com.evistek.oa.entity.Leave;
import com.evistek.oa.entity.ProcessTask;
import com.evistek.oa.entity.Repair;
import com.evistek.oa.model.FeedbackBase;
import com.evistek.oa.service.FeedbackService;
import com.evistek.oa.service.LeaveService;
import com.evistek.oa.service.RepairService;
import com.evistek.oa.service.WorkFlowService;
import com.evistek.oa.utils.*;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2021/2/6
 */
@RestController
@RequestMapping("/api/v1")
public class WorkFlowApi {
    private WorkFlowService workFlowService;
    private LeaveService leaveService;
    private FeedbackService feedbackService;
    private RepairService repairService;
    private RepositoryService repositoryService;

    public WorkFlowApi(WorkFlowService workFlowService, LeaveService leaveService, FeedbackService feedbackService, RepairService repairService, RepositoryService repositoryService) {
        this.workFlowService = workFlowService;
        this.leaveService = leaveService;
        this.feedbackService = feedbackService;
        this.repairService = repairService;
        this.repositoryService = repositoryService;
    }

    /**
     * 查找流程定义
     *
     * @param page     页脚
     * @param pageSize 每页数量
     * @param token    用户token
     * @return 返回已部署的流程定义数据
     */
    @RequestMapping(value = "/findProcess", method = RequestMethod.GET)
    public ResponseBase findProcessDefinition(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "token", required = true) String token
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS,
                this.workFlowService.findProcessDef(page, pageSize));
    }

    /**
     * 查看流程图
     *
     * @param processInstanceId 流程实例id
     * @param token             用户token
     * @param response          HTTP响应
     */
    @RequestMapping(value = "/traceProcess", method = RequestMethod.GET)
    public ResponseBase traceProcess(
            @RequestParam(value = "processInstanceId", required = true) String processInstanceId,
            @RequestParam(value = "token", required = true) String token,
            HttpServletResponse response
    ) {
        int result = workFlowService.showProcessMap(processInstanceId, response);
        if (result != 0) {
            return ResponseBase.build(ResponseCode.APPLY_NO_PROCESS);
        }
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }

    /**
     * 查找任务列表
     *
     * @param type              任务类型:根据type查找任务
     * @param processInstanceId 流程实例id:根据processInstanceId查询任务详情
     * @param page              页脚
     * @param pageSize          每页数量
     * @param token             用户token
     * @return 响应任务列表
     */
    @RequestMapping(value = "/findTask", method = RequestMethod.GET)
    public ResponseBase findTask(
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "processInstanceId", required = false) String processInstanceId,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "token", required = true) String token
    ) {
        if (processInstanceId != null) {
            return ResponseData.build(ResponseCode.API_SUCCESS, findTaskInfo(processInstanceId, type, null));
        }
        String email = JwtUtil.getEmail(token);
        List<Task> tasks = this.workFlowService.findTask(email);
        ProcessDefinition definition = this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionName(type).singleResult();
        if (definition == null) {
            return ResponseData.build(ResponseCode.API_SUCCESS);
        }
        String pdId = definition.getId();
        List list = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getProcessDefinitionId().equals(pdId) && task.getAssignee().equals(email)) {
                ProcessTask processTask = new ProcessTask();
                processTask.setId(task.getId());
                processTask.setName(task.getName());
                processTask.setProcessInstanceId(task.getProcessInstanceId());
                processTask.setAssignee(task.getAssignee());
                processTask.setProcessDefinitionId(task.getProcessDefinitionId());
                processTask.setExecutionId(task.getExecutionId());
                Object object = findTaskInfo(task.getProcessInstanceId(), type, processTask);
                list.add(object);
            }
        }
        return ResponseData.build(ResponseCode.API_SUCCESS,
                MapUtil.getMap(list.size(), PageHelper.pageList(list, page, pageSize)));
    }

    /**
     * 查找批注内容
     *
     * @param processInstanceId 流程实例id
     * @param token             用户token
     * @return 响应批注内容
     */
    @RequestMapping(value = "/findComment", method = RequestMethod.GET)
    public ResponseBase findComment(
            @RequestParam(value = "processInstanceId", required = true) String processInstanceId,
            @RequestParam(value = "token", required = true) String token
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS,
                this.workFlowService.findProcessInstanceComment(processInstanceId));
    }

    /**
     * 查找任务流转记录
     *
     * @param processInstanceId 流程实例id
     * @param token             用户token
     * @return 响应流转记录
     */
    @RequestMapping(value = "/findRecord", method = RequestMethod.GET)
    public ResponseBase findRecord(
            @RequestParam(value = "processInstanceId", required = true) String processInstanceId,
            @RequestParam(value = "token", required = true) String token
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS,
                this.workFlowService.findHistoricActivityInstance(processInstanceId));
    }

    public Object findTaskInfo(String processInstanceId, String type, ProcessTask task) {
        switch (type) {
            case Constant.BPM_LEAVE_APPLY:
                Leave leave = this.leaveService.findLeaveByProcessInstanceId(processInstanceId);
                leave.setProcessTask(task);
                return leave;
            case Constant.BPM_RECORD_TASK:
                FeedbackBase feedbackBase = new FeedbackBase();
                feedbackBase.setProcessInstanceId(processInstanceId);
                Feedback feedback = this.feedbackService.selectFeedbackById(feedbackBase);
                feedback.setProcessTask(task);
                return feedback;
            case Constant.BPM_DEVICE_REPAIR:
                Repair repair = this.repairService.findRepairByProcessInstanceId(processInstanceId);
                repair.setProcessTask(task);
                return repair;
            default:
                return null;
        }
    }
}
