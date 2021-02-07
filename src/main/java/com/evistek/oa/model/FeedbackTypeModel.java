package com.evistek.oa.model;

import com.evistek.oa.entity.FeedbackType;
import com.evistek.oa.entity.TempEmployee;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2021/1/13
 */
public class FeedbackTypeModel extends FeedbackType {
    public TempEmployee getTempEmployee() {
        return tempEmployee;
    }

    public void setTempEmployee(TempEmployee tempEmployee) {
        this.tempEmployee = tempEmployee;
    }

    private TempEmployee tempEmployee;
}
