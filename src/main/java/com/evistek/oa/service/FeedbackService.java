package com.evistek.oa.service;

import com.evistek.oa.entity.Feedback;
import com.evistek.oa.model.FeedbackBase;
import com.evistek.oa.model.FeedbackModel;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/11
 */
public interface FeedbackService {

    ResponseCode addFeedback(Feedback feedback);

    int updateFeedbackByProcessId(Feedback feedback);

    int updateFeedbackById(Feedback feedback);

    int deleteFeedback(FeedbackBase feedbackBase);

    List<Feedback> selectFeedbackList(FeedbackBase feedbackBase);

    FeedbackModel selectFeedbackById(FeedbackBase feedbackBase);

    Integer findFeedbackTotal(FeedbackBase feedbackBase);

}
