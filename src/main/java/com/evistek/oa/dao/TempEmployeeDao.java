package com.evistek.oa.dao;

import com.evistek.oa.entity.TempEmployee;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2021/1/11
 */
@Component
public class TempEmployeeDao {
    private SqlSession sqlSession;

    public TempEmployeeDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<TempEmployee> findTempEmployee() {
        return this.sqlSession.selectList("findTempEmployee");
    }

    public int addTempEmployee(TempEmployee tempEmployee) {
        return this.sqlSession.insert("addTempEmployee", tempEmployee);
    }

    public int deleteTempEmployee(String tempEmployeeId) {
        return this.sqlSession.delete("deleteTempEmployee", tempEmployeeId);
    }

    public int updateTempEmployee(TempEmployee tempEmployee) {
        return this.sqlSession.update("updateTempEmployee", tempEmployee);
    }
}
