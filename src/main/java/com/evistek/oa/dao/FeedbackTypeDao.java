package com.evistek.oa.dao;

import com.evistek.oa.entity.FeedbackType;
import com.evistek.oa.model.FeedbackTypeModel;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/15
 */
@Component
public class FeedbackTypeDao {
    public FeedbackTypeDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private SqlSession sqlSession;


    public List<FeedbackType> selectFeedBackTypeList() {
        return sqlSession.selectList("selectFeedbackTypeList");
    }

    public List<FeedbackTypeModel> selectFeedBackTypeModelList() {
        return sqlSession.selectList("selectFeedbackTypeModelList");
    }

    public FeedbackType selectFeedbackTypeById(int id) {
        return sqlSession.selectOne("selectFeedbackTypeById", id);
    }
}
