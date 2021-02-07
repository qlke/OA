package com.evistek.oa.entity;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/1
 */
public class DataAuthority {
    private String positionId;
    private String organizationId;
    private String departmentId;

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
