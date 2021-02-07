package com.evistek.oa.entity;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/10
 */
public class EquipmentType {
    private int id;
    private String name;
    private String description;
    private int used=-1;
    private int must=-1;

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    private int inputType=-1;

    public int getMust() {
        return must;
    }

    public void setMust(int must) {
        this.must = must;
    }

    public int getSortWeight() {
        return sortWeight;
    }

    public void setSortWeight(int sortWeight) {
        this.sortWeight = sortWeight;
    }

    private int sortWeight=-2;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }


}
