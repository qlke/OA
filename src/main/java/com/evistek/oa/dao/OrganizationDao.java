package com.evistek.oa.dao;

import com.evistek.oa.entity.Organization;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/5
 */
@Component
public class OrganizationDao {
    private SqlSession sqlSession;

    public OrganizationDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Organization findOrganizationById(String id) {
        return this.sqlSession.selectOne("findOrganizationById", id);
    }

    public Organization findOrganizationByName(String name) {
        return this.sqlSession.selectOne("findOrganizationByName", name);
    }

    public List<Organization> findOrganizationByFatherId(String fatherId) {
        return this.sqlSession.selectList("findOrganizationByFatherId", fatherId);
    }

    public int addOrganization(Organization organizations) {
        return this.sqlSession.insert("addOrganization", organizations);
    }

    public int deleteOrganizationById(String id) {
        return this.sqlSession.delete("deleteOrganizationById", id);
    }

    public int updateOrganizationById(Organization organizations) {
        return this.sqlSession.update("updateOrganizationById", organizations);
    }


}
