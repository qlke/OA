package com.evistek.oa.dao;

import com.evistek.oa.entity.Authority;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/5
 */
@Component
public class AuthorityDao {
    private SqlSession sqlSession;

    public AuthorityDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Authority> findAuthorityByEId(String employeeId) {
        return this.sqlSession.selectList("findAuthorityByEId", employeeId);
    }

    public List<Authority> findAuthorityByPId(String positionId) {
        return this.sqlSession.selectList("findAuthorityByPId", positionId);
    }
}
