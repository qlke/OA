package com.evistek.oa.dao;

import com.evistek.oa.entity.Position;
import com.evistek.oa.utils.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/6
 */
@Component
public class PositionDao {
    private SqlSession sqlSession;

    public PositionDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Position> findPosition(String name, int page, int pageSize) {
        return this.sqlSession.selectList("findPosition", name, PageHelper.getRowBounds(page, pageSize));
    }

    public Position findPositionByName(String name) {
        return this.sqlSession.selectOne("findPositionByName", name);
    }

    public Position findPositionById(String id) {
        return this.sqlSession.selectOne("findPositionById", id);
    }

    public Integer findPositionTotal(String name) {
        return this.sqlSession.selectOne("findPositionTotal", name);
    }

    public int addPosition(Position position) {
        return this.sqlSession.insert("addPosition", position);
    }

    public int deletePositionById(String id) {
        return this.sqlSession.delete("deletePositionById", id);
    }

    public int updatePositionById(Position position) {
        return this.sqlSession.update("updatePositionById", position);
    }
}
