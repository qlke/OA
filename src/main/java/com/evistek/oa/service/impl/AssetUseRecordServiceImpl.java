package com.evistek.oa.service.impl;

import com.evistek.oa.dao.AssetDao;
import com.evistek.oa.dao.AssetUseRecordDao;
import com.evistek.oa.dao.DepartmentEmployeeAssetDao;
import com.evistek.oa.entity.AssetUseRecord;
import com.evistek.oa.entity.Asset;
import com.evistek.oa.service.AssetUseRecordService;
import com.evistek.oa.utils.MapUtil;
import com.evistek.oa.utils.ResponseCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/12
 */
@Service
public class AssetUseRecordServiceImpl implements AssetUseRecordService {
    private AssetUseRecordDao assetUseRecordDao;
    private DepartmentEmployeeAssetDao departmentEmployeeAssetDao;
    private AssetDao assetDao;

    public AssetUseRecordServiceImpl(AssetUseRecordDao assetUseRecordDao, DepartmentEmployeeAssetDao departmentEmployeeAssetDao, AssetDao assetDao) {
        this.assetUseRecordDao = assetUseRecordDao;
        this.departmentEmployeeAssetDao = departmentEmployeeAssetDao;
        this.assetDao = assetDao;
    }

    @Override
    @Transactional
    public ResponseCode addAssetUseRecord(AssetUseRecord assetUseRecord, List<String> list) {
        //使用中的资产不能分配
        List<Asset> assets = this.assetDao.findAssetByIds(list);
        for (Asset as : assets) {
            if (as.getStatus() == 1) {
                return ResponseCode.ASSET_IN_USE;
            }
        }
        //添加资产使用记录
        Map map = MapUtil.getMap(0, list);
        map.put("useType", assetUseRecord.getUseType());
        map.put("receiveEmployeeId", assetUseRecord.getReceiveEmployeeId());
        map.put("receiveDepartmentId", assetUseRecord.getReceiveDepartmentId());
        map.put("receiveTime", assetUseRecord.getReceiveTime());
        map.put("status", assetUseRecord.getStatus());
        int result = this.assetUseRecordDao.addAssetUseRecord(map);
        if (result == 0) {
            return ResponseCode.ASSET_ALLOT_FAILED;
        }
        //添加部门员工资产信息
        Map maps = MapUtil.getMap(0, list);
        maps.put("departmentId", assetUseRecord.getReceiveDepartmentId());
        maps.put("employeeId", assetUseRecord.getReceiveEmployeeId());
        result = this.departmentEmployeeAssetDao.addDEA(maps);
        if (result == 0) {
            throw new RuntimeException("add department employee asset failed.");
        }
        Map statusMap = MapUtil.getMap(0, list);
        statusMap.put("status", 1);
        result = this.assetDao.updateAssetStatus(statusMap);
        if (result == 0) {
            throw new RuntimeException("update asset status failed.");
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    @Transactional
    public ResponseCode updateAssetUseRecordByAssetId(AssetUseRecord assetUseRecord, List<String> list) {
        //根据资产id修改资产使用记录
        Map map = MapUtil.getMap(0, list);
        map.put("returnEmployeeId", assetUseRecord.getReturnEmployeeId());
        map.put("returnDepartmentId", assetUseRecord.getReturnDepartmentId());
        map.put("returnTime", assetUseRecord.getReturnTime());
        map.put("status", assetUseRecord.getStatus());
        int result = this.assetUseRecordDao.updateAssetUseRecordByAssetId(map);
        if (result == 0) {
            return ResponseCode.ASSET_RETURN_FAILED;
        }
        result = this.departmentEmployeeAssetDao.deleteDEAByAssetId(list);
        if (result == 0) {
            throw new RuntimeException("delete department employee asset failed.");
        }
        Map statusMap = MapUtil.getMap(0, list);
        statusMap.put("status", assetUseRecord.getStatus());
        result = this.assetDao.updateAssetStatus(statusMap);
        if (result == 0) {
            throw new RuntimeException("update asset status failed.");
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public List<AssetUseRecord> findAssetUseRecord(String assetId) {
        return this.assetUseRecordDao.findAssetUseRecord(assetId);
    }
}
