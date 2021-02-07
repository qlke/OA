package com.evistek.oa.model;

import com.evistek.oa.entity.Equipment;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2021/1/18
 */
public class EquipmentBase extends Equipment {
    private int page = 1;
    private int pageNumber = 10;
    private boolean isLimit = false;

    public void setLimit(int page_,int pageNumber_){
        if (page_ > 0 && pageNumber_ > 0) {
            isLimit=true;
            if (page_ > 0) {
                page=(page_ - 1) * pageNumber_;
            } else {
                page=0;
            }
            if (pageNumber_ < 10) {
                pageNumber=10;
            } else {
                pageNumber=pageNumber_;
            }
        }
    }
}
