package com.evistek.oa.model;

import com.evistek.oa.entity.Feedback;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/14
 */
public class FeedbackModel extends Feedback {
    private String createUserName;
    private String allotUserName;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getAllotUserName() {
        return allotUserName;
    }

    public void setAllotUserName(String allotUserName) {
        this.allotUserName = allotUserName;
    }


}
