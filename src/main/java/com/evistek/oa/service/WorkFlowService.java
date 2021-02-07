package com.evistek.oa.service;

import com.evistek.oa.entity.Employee;
import com.evistek.oa.model.CommentModel;
import com.evistek.oa.model.HistoricActivityInstanceModel;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2021/2/6
 */
public interface WorkFlowService {
    Map findProcessDef(int page, int pageSize);

    List<Task> findTask(String email);

    ProcessInstance startWorkflow(String email, String process, Map map);

    int showProcessMap(String processInstanceId, HttpServletResponse response);

    ProcessInstance approve(Employee employee, Map map, String processInstanceId);

    List<CommentModel> findProcessInstanceComment(String processInstanceId);

    List<HistoricActivityInstanceModel> findHistoricActivityInstance(String processInstanceId);
}
