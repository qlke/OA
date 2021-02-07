package com.evistek.oa.dao;

import com.evistek.oa.entity.Notice;
import com.evistek.oa.utils.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/10
 */
@Component
public class NoticeDao {
    private SqlSession sqlSession;

    public NoticeDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Notice> findAllPublishNotice(int page, int pageSize) {
        return this.sqlSession.selectList("findAllPublishNotice",
                null, PageHelper.getRowBounds(page, pageSize));
    }

    public List<Notice> findPublishNotice(List<String> objectIds, int page, int pageSize) {
        return this.sqlSession.selectList("findPublishNotice",
                objectIds, PageHelper.getRowBounds(page, pageSize));
    }

    public List<Notice> findNotice(String userId, int page, int pageSize) {
        return this.sqlSession.selectList("findNotice", userId,
                PageHelper.getRowBounds(page, pageSize));
    }

    public Notice findNoticeById(String id) {
        return this.sqlSession.selectOne("findNoticeById", id);
    }

    public Integer findAllPublicNoticeTotal() {
        return this.sqlSession.selectOne("findAllPublicNoticeTotal");
    }

    public Integer findPublicNoticeTotal(List<String> objectIds) {
        return this.sqlSession.selectOne("findPublicNoticeTotal", objectIds);
    }

    public Integer findNoticeTotal(String userId) {
        return this.sqlSession.selectOne("findNoticeTotal", userId);
    }

    public int addNotice(Notice notice) {
        return this.sqlSession.insert("addNotice", notice);
    }

    public int updateNoticeById(Notice notice) {
        return this.sqlSession.update("updateNoticeById", notice);
    }

    public int deleteNoticeById(String id) {
        return this.sqlSession.delete("deleteNoticeById", id);
    }

    public int updatePublishById(String id) {
        return this.sqlSession.update("updatePublishById", id);
    }
}
