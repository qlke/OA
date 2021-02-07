package com.evistek.oa.dao;

import com.evistek.oa.entity.DataAuthority;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/1
 */
@Component
public class DataAuthorityDao {
    private SqlSession sqlSession;

    public DataAuthorityDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int addOrgAuthority(Map map) {
        return this.sqlSession.insert("addOrgAuthority", map);
    }

    public int addDepAuthority(Map map) {
        return this.sqlSession.insert("addDepAuthority", map);
    }

    public int deleteDataAuthorityByPId(String positionId) {
        return this.sqlSession.delete("deleteDataAuthorityByPId", positionId);
    }

    public List<DataAuthority> findDataAuthorityByPId(String positionId) {
        return this.sqlSession.selectList("findDataAuthorityByPId", positionId);
    }
}
