package com.evistek.oa.dao;

import com.evistek.oa.entity.ApiAuthority;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/16
 */
@Component
public class ApiAuthorityDao {
    private SqlSession sqlSession;

    public ApiAuthorityDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public ApiAuthority findApiAuthority(Map map) {
        return this.sqlSession.selectOne("findApiAuthority", map);

    }
}
