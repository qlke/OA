package com.evistek.oa.dao;

import com.evistek.oa.entity.Repair;
import com.evistek.oa.utils.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/25
 */
@Component
public class RepairDao {
    private SqlSession sqlSession;

    public RepairDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Repair> findRepair(Map map, int page, int pageSize) {
        return this.sqlSession.selectList("findRepair", map, PageHelper.getRowBounds(page, pageSize));
    }

    public Repair findRepairById(String id) {
        return this.sqlSession.selectOne("findRepairById", id);
    }

    public Repair findRepairByProcessInstanceId(String processInstanceId) {
        return this.sqlSession.selectOne("findRepairByProcessInstanceId", processInstanceId);
    }

    public Integer findRepairTotal(Map map) {
        return this.sqlSession.selectOne("findRepairTotal", map);
    }

    public int addRepair(Repair repair) {
        return this.sqlSession.insert("addRepair", repair);
    }

    public int updateRepair(Repair repair) {
        return this.sqlSession.update("updateRepair", repair);
    }

    public int deleteRepairById(String id) {
        return this.sqlSession.delete("deleteRepairById", id);
    }
}
