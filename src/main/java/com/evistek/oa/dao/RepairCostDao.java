package com.evistek.oa.dao;

import com.evistek.oa.entity.RepairCost;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/29
 */
@Component
public class RepairCostDao {
    private SqlSession sqlSession;

    public RepairCostDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public RepairCost findRepairCostByRepairId(String repairId) {
        return this.sqlSession.selectOne("findRepairCostByRepairId", repairId);
    }

    public RepairCost findRepairCostById(String id) {
        return this.sqlSession.selectOne("findRepairCostById", id);
    }

    public int addRepairCost(RepairCost repairCost) {
        return this.sqlSession.insert("addRepairCost", repairCost);
    }

    public int updateRepairCost(RepairCost repairCost) {
        return this.sqlSession.update("updateRepairCost", repairCost);
    }

    public int deleteRepairCostById(String id) {
        return this.sqlSession.delete("deleteRepairCostById", id);
    }
}
