package com.evistek.oa.entity;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/10
 */
public class EquipmentAttribute {
    private int id;
    private int tid=-1;
    private String tName;
    private String code;
    private String name;
    private String prefix;
    private String suffix;
    private int used=-1;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
