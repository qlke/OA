package com.evistek.oa.dao;

import com.evistek.oa.entity.AssetType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/13
 */
@Component
public class AssetTypeDao {
    private SqlSession sqlSession;

    public AssetTypeDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<AssetType> findAssetType() {
        return this.sqlSession.selectList("findAssetType");
    }

    public AssetType findAssetTypeById(String id) {
        return this.sqlSession.selectOne("findAssetTypeById", id);
    }

    public AssetType findAssetTypeByFatherId(String fatherId) {
        return this.sqlSession.selectOne("findAssetTypeByFatherId", fatherId);
    }

    public int addAssetType(AssetType assetTypes) {
        return this.sqlSession.insert("addAssetType", assetTypes);
    }

    public int deleteAssetTypeById(String id) {
        return this.sqlSession.delete("deleteAssetTypeById", id);
    }

    public int updateAssetTypeById(AssetType assetTypes) {
        return this.sqlSession.update("updateAssetTypeById", assetTypes);
    }
}
