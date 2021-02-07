package com.evistek.oa.dao;

import com.evistek.oa.entity.RepairMaterial;
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
public class RepairMaterialDao {
    private SqlSession sqlSession;

    public RepairMaterialDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<RepairMaterial> findRMByRepairId(String repairId) {
        return this.sqlSession.selectList("findRMByRepairId", repairId);
    }

    public List<RepairMaterial> findRMByRepairCostId(String repairCostId) {
        return this.sqlSession.selectList("findRMByRepairCostId", repairCostId);
    }

    public int addRepairMaterial(Map map) {
        return this.sqlSession.insert("addRepairMaterial", map);
    }

    public int deleteRMByRepairId(String repairId) {
        return this.sqlSession.delete("deleteRMByRepairId", repairId);
    }

    public int deleteRMByRepairCostId(String repairCostId) {
        return this.sqlSession.delete("deleteRMByRepairCostId", repairCostId);
    }
}
