package com.evistek.oa.entity;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/27
 */
public class Process {
    //流程定义id
    private String id;
    //流程定义部署id
    private String deploymentId;
    //流程定义名称
    private String name;
    //流程的资源名称（绝对路径：C:\Users\qlke\Desktop\OA\oa\target\classes\process\LeaveApply.bpmn）
    private String resourceName;
    //流程名称
    private String key;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
