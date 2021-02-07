package com.evistek.oa.dao;

import com.evistek.oa.entity.Leave;
import com.evistek.oa.utils.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/30
 */
@Component
public class LeaveDao {
    private SqlSession sqlSession;

    public LeaveDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int updateLeaveById(Leave leave) {
        return this.sqlSession.update("updateLeaveById", leave);
    }

    public Leave findLeaveByProcessInstanceId(String processInstanceId) {
        return this.sqlSession.selectOne("findLeaveByProcessInstanceId", processInstanceId);
    }

    public List<Leave> findLeaveByEId(String employeeId, int page, int pageSize) {
        return this.sqlSession.selectList("findLeaveByEId", employeeId, PageHelper.getRowBounds(page, pageSize));
    }

    public int findLeaveTotalByEId(String employeeId) {
        return this.sqlSession.selectOne("findLeaveTotalByEId", employeeId);
    }

    public int addLeave(Leave leave) {
        return this.sqlSession.insert("addLeave", leave);
    }

    public int deleteLeave(String processInstanceId) {
        return this.sqlSession.delete("deleteLeave", processInstanceId);
    }
}
