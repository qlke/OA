package com.evistek.oa.dao;

import com.evistek.oa.entity.EmployeePosition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/11
 */
@Component
public class EmployeePositionDao {
    private SqlSession sqlSession;

    public EmployeePositionDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int addEmployeePosition(Map map) {
        return this.sqlSession.insert("addEmployeePosition", map);
    }

    public int deleteEmployeePositionByEId(String employeeId) {
        return this.sqlSession.delete("deleteEmployeePositionByEId", employeeId);
    }

    public List<EmployeePosition> findEmployeePositionByPId(String positionId) {
        return this.sqlSession.selectList("findEmployeePositionByPId", positionId);
    }
}
