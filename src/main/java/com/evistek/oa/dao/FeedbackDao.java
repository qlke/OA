package com.evistek.oa.dao;

import com.evistek.oa.entity.Feedback;
import com.evistek.oa.model.FeedbackBase;
import com.evistek.oa.model.FeedbackModel;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/14
 */
@Component
public class FeedbackDao {
    public FeedbackDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private SqlSession sqlSession;

    public int addFeedback(Feedback feedback) {
        return sqlSession.insert("addFeedback", feedback);
    }

    public int updateFeedbackByProcessId(Feedback feedback) {
        return sqlSession.insert("updateFeedbackByProcessId", feedback);
    }

    public int updateFeedbackById(Feedback feedback) {
        return sqlSession.insert("updateFeedbackById", feedback);
    }

    public FeedbackModel selectFeedbackById(FeedbackBase feedbackBase) {
        Feedback feedback = sqlSession.selectOne("selectFeedbackById", feedbackBase);
        FeedbackModel feedbackModel = new FeedbackModel();
        if (null != feedback) {
            BeanUtils.copyProperties(feedback, feedbackModel);
        }
        return feedbackModel;
    }

    public int deleteFeedback(FeedbackBase feedbackBase) {
        return sqlSession.delete("deleteFeedback", feedbackBase);
    }

    public List<Feedback> selectFeedbackList(FeedbackBase feedbackBase) {
        return sqlSession.selectList("selectFeedbackList", feedbackBase);
    }

    public Integer findFeedbackTotal(FeedbackBase feedbackBase) {
        return this.sqlSession.selectOne("findFeedbackTotal", feedbackBase);
    }
}
