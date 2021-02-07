package com.evistek.oa.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/10
 */
@Component
public class PositionAuthorityDao {
    private SqlSession sqlSession;

    public PositionAuthorityDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int addPositionAuthority(String positionId, List<String> list) {
        Map map = new HashMap();
        map.put("positionId", positionId);
        map.put("list", list);
        return this.sqlSession.insert("addPositionAuthority", map);
    }

    public int deletePositionAuthorityByPId(String positionId) {
        return this.sqlSession.delete("deletePositionAuthorityByPId", positionId);
    }
}
