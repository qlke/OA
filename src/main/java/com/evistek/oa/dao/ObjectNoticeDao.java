package com.evistek.oa.dao;

import com.evistek.oa.entity.ObjectNotice;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/11
 */
@Component
public class ObjectNoticeDao {
    private SqlSession sqlSession;

    public ObjectNoticeDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<ObjectNotice> findObjectNoticeByNoticeId(String noticeId) {
        return this.sqlSession.selectList("findObjectNoticeByNoticeId", noticeId);
    }

    public int addObjectNotice(Map map) {
        return this.sqlSession.insert("addObjectNotice", map);
    }

    public int deleteObjectNoticeByNoticeId(String noticeId) {
        return this.sqlSession.delete("deleteObjectNoticeByNoticeId", noticeId);
    }
}
