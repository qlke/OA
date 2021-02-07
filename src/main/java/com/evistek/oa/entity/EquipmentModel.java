package com.evistek.oa.entity;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/28
 */
public class EquipmentModel {
    private int id;
    private String name;
    private String mCode;
    private String softUrl;
    private String specUrl;
    private int used=-1;
    private String createTime;

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    private String director;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getSoftUrl() {
        return softUrl;
    }

    public void setSoftUrl(String softUrl) {
        this.softUrl = softUrl;
    }

    public String getSpecUrl() {
        return specUrl;
    }

    public void setSpecUrl(String specUrl) {
        this.specUrl = specUrl;
    }


}
