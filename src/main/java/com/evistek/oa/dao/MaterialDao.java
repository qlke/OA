package com.evistek.oa.dao;

import com.evistek.oa.entity.Material;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/25
 */
@Component
public class MaterialDao {
    private SqlSession sqlSession;

    public MaterialDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Material> findMaterialByIds(List<String> list) {
        return this.sqlSession.selectList("findMaterialByIds", list);
    }

    public int addMaterial(List<Material> materials) {
        return this.sqlSession.insert("addMaterial", materials);
    }

    public int deleteMaterialByIds(List<String> ids) {
        return this.sqlSession.delete("deleteMaterialByIds", ids);
    }

    public int deleteMaterialByRepairId(String repairId) {
        return this.sqlSession.delete("deleteMaterialByRepairId", repairId);
    }

    public int deleteMaterialByRCId(String repairCostId) {
        return this.sqlSession.delete("deleteMaterialByRCId", repairCostId);
    }
}
