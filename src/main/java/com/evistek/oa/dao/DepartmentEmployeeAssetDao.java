package com.evistek.oa.dao;

import com.evistek.oa.entity.DepartmentEmployeeAsset;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/12
 */
@Component
public class DepartmentEmployeeAssetDao {
    private SqlSession sqlSession;

    public DepartmentEmployeeAssetDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<DepartmentEmployeeAsset> findDEAByAssetId(String assetId) {
        return this.sqlSession.selectList("findDEAByAssetId", assetId);
    }

    public int addDEA(Map map) {
        return this.sqlSession.insert("addDEA", map);
    }

    public int deleteDEAByAssetId(List<String> list) {
        return this.sqlSession.delete("deleteDEAByAssetId", list);
    }
}
