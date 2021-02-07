package com.evistek.oa.service;

import com.evistek.oa.entity.FeedbackType;
import com.evistek.oa.model.FeedbackTypeModel;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/14
 */
public interface FeedbackTypeService {
    List<FeedbackType> selectFeedbackList();
    List<FeedbackTypeModel> selectFeedbackListModel();
}
