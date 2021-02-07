package com.evistek.oa.service.impl;

import com.evistek.oa.entity.Employee;
import com.evistek.oa.entity.Process;
import com.evistek.oa.model.CommentModel;
import com.evistek.oa.model.HistoricActivityInstanceModel;
import com.evistek.oa.service.EmployeeService;
import com.evistek.oa.service.WorkFlowService;
import com.evistek.oa.utils.MapUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2021/2/6
 */
@Service
public class WorkFlowServiceImpl implements WorkFlowService {
    private RepositoryService repositoryService;
    private TaskService taskService;
    private RuntimeService runtimeService;
    private IdentityService identityService;
    private HistoryService historyService;
    private EmployeeService employeeService;
    private Logger logger;

    public WorkFlowServiceImpl(RepositoryService repositoryService, TaskService taskService, RuntimeService runtimeService, IdentityService identityService, HistoryService historyService, EmployeeService employeeService) {
        this.repositoryService = repositoryService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.identityService = identityService;
        this.historyService = historyService;
        this.employeeService = employeeService;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public Map findProcessDef(int page, int pageSize) {
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().listPage(page, pageSize);
        int total = repositoryService.createProcessDefinitionQuery().list().size();
        List<Process> processes = new ArrayList<Process>();
        for (int i = 0; i < processDefinitions.size(); i++) {
            Process process = new Process();
            process.setId(processDefinitions.get(i).getDeploymentId());
            process.setDeploymentId(processDefinitions.get(i).getDeploymentId());
            process.setName(processDefinitions.get(i).getName());
            process.setResourceName(processDefinitions.get(i).getResourceName());
            process.setKey(processDefinitions.get(i).getKey());
            processes.add(process);
        }
        return MapUtil.getMap(total, processes);
    }

    @Override
    public List<Task> findTask(String email) {
        return this.taskService.createTaskQuery().taskAssignee(email).list();
    }

    @Override
    public ProcessInstance startWorkflow(String email, String process, Map map) {
        identityService.setAuthenticatedUserId(email);
        String processDefinitionKey = repositoryService.createProcessDefinitionQuery()
                .processDefinitionName(process).singleResult().getKey();
        if (processDefinitionKey == null) {
            return null;
        }
        return this.runtimeService.startProcessInstanceByKey(processDefinitionKey, email, map);
    }

    @Override
    public int showProcessMap(String processInstanceId, HttpServletResponse response) {
        ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (process == null) {
            return -1;
        }
        BpmnModel bpmnmodel = repositoryService.getBpmnModel(process.getProcessDefinitionId());
        List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
        DefaultProcessDiagramGenerator gen = new DefaultProcessDiagramGenerator();
        // 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .executionId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
        // 计算活动线
        List<String> highLightedFlows = getHighLightedFlows(
                (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                        .getDeployedProcessDefinition(process.getProcessDefinitionId()), historicActivityInstances);
        InputStream in = gen.generateDiagram(bpmnmodel, "png", activeActivityIds, highLightedFlows,
                "宋体", "微软雅黑", "黑体", null, 2.0);
        ServletOutputStream output = null;
        try {
            output = response.getOutputStream();
            IOUtils.copy(in, output);
        } catch (IOException e) {
            logger.error("processInstanceId" + processInstanceId + "failed to generate flowchart，cause：" + e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public ProcessInstance approve(Employee employee, Map map, String processInstanceId) {
        Task task = this.taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if (!employee.getEmail().equals(task.getAssignee())) {
            logger.error("the current user is inconsistent with the assignee.");
            throw new RuntimeException("the current user is inconsistent with the assignee.");
        }
        //设置受理人姓名
        Authentication.setAuthenticatedUserId(employee.getName());
        //添加备注信息
        taskService.addComment(task.getId(), processInstanceId, map.get("annotation").toString());
        this.taskService.complete(task.getId(), map);
        return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    @Override
    public List<CommentModel> findProcessInstanceComment(String processInstanceId) {
        List<Comment> comments = this.taskService.getProcessInstanceComments(processInstanceId);
        List<CommentModel> commentModels = new ArrayList<>();
        for (Comment comment : comments) {
            CommentModel c = new CommentModel();
            c.setTaskId(comment.getTaskId());
            c.setMessage(comment.getFullMessage());
            c.setProcessInstanceId(comment.getProcessInstanceId());
            c.setTaskName(historyService.createHistoricTaskInstanceQuery().taskId(comment.getTaskId()).singleResult().getName());
            c.setTime(comment.getTime());
            c.setEmployeeName(comment.getUserId());
            commentModels.add(c);
        }
        return commentModels;
    }

    @Override
    public List<HistoricActivityInstanceModel> findHistoricActivityInstance(String processInstanceId) {
        List<HistoricActivityInstanceModel> hisList = new ArrayList<>();
        List<HistoricActivityInstance> his = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
        for (HistoricActivityInstance h : his) {
            HistoricActivityInstanceModel hisModel = new HistoricActivityInstanceModel();
            hisModel.setStartTime(h.getStartTime());
            hisModel.setEndTime(h.getEndTime());
            hisModel.setActivityName(h.getActivityName());
            if (h.getAssignee() != null) {
                hisModel.setUser(this.employeeService.findEmployeeByEmail(h.getAssignee()).getName());
            }
            hisList.add(hisModel);
        }
        return hisList;
    }

    public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
                                            List<HistoricActivityInstance> historicActivityInstances) {
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size(); i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            if ((i + 1) >= historicActivityInstances.size()) {
                break;
            }
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());// 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {// 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {// 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {// 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }
}
