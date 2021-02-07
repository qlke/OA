package com.evistek.oa.dao;

import com.evistek.oa.entity.Department;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/5
 */
@Component
public class DepartmentDao {
    private SqlSession sqlSession;

    public DepartmentDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Department findDepartmentById(String id) {
        return this.sqlSession.selectOne("findDepartmentById", id);
    }

    public List<Department> findDepartmentByOrganizationId(String organizationId) {
        return this.sqlSession.selectList("findDepartmentByOrganizationId", organizationId);
    }

    public List<Department> findDepartmentByFatherId(String fatherId) {
        return this.sqlSession.selectList("findDepartmentByFatherId", fatherId);
    }

    public int addDepartment(Department departments) {
        return this.sqlSession.insert("addDepartment", departments);
    }

    public int deleteDepartmentById(String id) {
        return this.sqlSession.delete("deleteDepartmentById", id);
    }

    public int updateDepartmentById(Department departments) {
        return this.sqlSession.update("updateDepartmentById", departments);
    }
}
