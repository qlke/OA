package com.evistek.oa.dao;

import com.evistek.oa.entity.Asset;
import com.evistek.oa.utils.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/11
 */
@Component
public class AssetDao {
    private SqlSession sqlSession;

    public AssetDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Asset> findAsset(Map map, int page, int pageSize) {
        return this.sqlSession.selectList("findAsset", map, PageHelper.getRowBounds(page, pageSize));
    }

    public List<Asset> findAssetByDepartmentId(String departmentId) {
        return this.sqlSession.selectList("findAssetByDepartmentId", departmentId);
    }

    public Asset findAssetById(String id) {
        return this.sqlSession.selectOne("findAssetById", id);
    }

    public Integer findAssetTotal(Map map) {
        return this.sqlSession.selectOne("findAssetTotal", map);
    }

    public Asset findAssetByNumber(String number) {
        return this.sqlSession.selectOne("findAssetByNumber", number);
    }

    public Asset findAssetByModel(String model) {
        return this.sqlSession.selectOne("findAssetByModel", model);
    }

    public Asset findAssetBySerialNumber(String serialNumber) {
        return this.sqlSession.selectOne("findAssetBySerialNumber", serialNumber);
    }

    public List<Asset> findAssetByTypeId(String typeId) {
        return this.sqlSession.selectList("findAssetById", typeId);
    }

    public List<Asset> findAssetByEmployeeId(String employeeId) {
        return this.sqlSession.selectList("findAssetByEmployeeId", employeeId);
    }

    public List<Asset> findAssetByIds(List<String> ids) {
        return this.sqlSession.selectList("findAssetByIds", ids);
    }

    public int addAsset(Asset assets) {
        return this.sqlSession.insert("addAsset", assets);
    }

    public int deleteAssetById(String id) {
        return this.sqlSession.delete("deleteAssetById", id);
    }

    public int updateAssetStatus(Map map) {
        return this.sqlSession.update("updateAssetStatus", map);
    }

    public int updateAssetById(Asset assets) {
        return this.sqlSession.update("updateAssetById", assets);
    }
}
