package com.evistek.oa.entity;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/4
 */
public class Position {
    private String id;
    private String name;
    private String identify;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
