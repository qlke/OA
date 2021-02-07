package com.evistek.oa.entity;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/4
 */
public class Authority {
    private String id;
    private String name;
    private String identify;
    private String fatherId;

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

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }
}
