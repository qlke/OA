package com.evistek.oa.entity;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/24
 */
public class EquipmentBindType {
    private int mid = -1;
    private int tid = -1;
    private int aid = -1;
    private String tValue;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String gettValue() {
        return tValue;
    }

    public void settValue(String tValue) {
        this.tValue = tValue;
    }


}
