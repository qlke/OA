package com.evistek.oa.dao;

import com.evistek.oa.entity.AssetUseRecord;
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
public class AssetUseRecordDao {
    private SqlSession sqlSession;

    public AssetUseRecordDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int addAssetUseRecord(Map map) {
        return this.sqlSession.insert("addAssetUseRecord", map);
    }

    public int updateAssetUseRecordByAssetId(Map map) {
        return this.sqlSession.update("updateAssetUseRecordByAssetId", map);
    }

    public List<AssetUseRecord> findAssetUseRecord(String assetId) {
        return this.sqlSession.selectList("findAssetUseRecord", assetId);
    }

    public List<AssetUseRecord> findAssetUseRecordByRED(String receiveEmployeeId) {
        return this.sqlSession.selectList("findAssetUseRecordByRED", receiveEmployeeId);
    }

    public int deleteAURByAssetId(String assetId) {
        return this.sqlSession.delete("deleteAURByAssetId", assetId);
    }
}
