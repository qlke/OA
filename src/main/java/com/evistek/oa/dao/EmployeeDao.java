package com.evistek.oa.dao;

import com.evistek.oa.entity.Employee;
import com.evistek.oa.entity.Position;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/3
 */
@Component
public class EmployeeDao {

    private SqlSession sqlSession;

    public EmployeeDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Employee findEmployeeByEmail(String email) {
        return this.sqlSession.selectOne("findEmployeeByEmail", email);
    }

    public List<Employee> findEmployeeByDId(String departmentId) {
        return this.sqlSession.selectList("findEmployeeByDId", departmentId);
    }

    public Employee findEmployeeById(String id) {
        return this.sqlSession.selectOne("findEmployeeById", id);
    }

    public List<Employee> findEmployeeByFatherId(String fatherId) {
        return this.sqlSession.selectList("findEmployeeByFatherId", fatherId);
    }

    public Employee findEmployeeByPhone(String phone) {
        return this.sqlSession.selectOne("findEmployeeByPhone", phone);
    }

    public List<Employee> findEmployeeByPId(List<Position> list) {
        return this.sqlSession.selectList("findEmployeeByPId", list);
    }

    public int addEmployee(Employee employee) {
        return this.sqlSession.insert("addEmployee", employee);
    }

    public int deleteEmployeeById(String id) {
        return this.sqlSession.delete("deleteEmployeeById", id);
    }

    public int updateEmployeeById(Employee employee) {
        return this.sqlSession.update("updateEmployeeById", employee);
    }
}
