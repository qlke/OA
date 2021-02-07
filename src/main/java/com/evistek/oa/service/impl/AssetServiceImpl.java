package com.evistek.oa.service.impl;

import com.evistek.oa.dao.AssetDao;
import com.evistek.oa.dao.AssetUseRecordDao;
import com.evistek.oa.dao.DepartmentEmployeeAssetDao;
import com.evistek.oa.entity.Asset;
import com.evistek.oa.service.AssetService;
import com.evistek.oa.utils.MapUtil;
import com.evistek.oa.utils.ResponseCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/11
 */
@Service
public class AssetServiceImpl implements AssetService {
    private AssetDao assetDao;
    private AssetUseRecordDao assetUseRecordDao;
    private DepartmentEmployeeAssetDao departmentEmployeeAssetDao;

    public AssetServiceImpl(AssetDao assetDao, AssetUseRecordDao assetUseRecordDao, DepartmentEmployeeAssetDao departmentEmployeeAssetDao) {
        this.assetDao = assetDao;
        this.assetUseRecordDao = assetUseRecordDao;
        this.departmentEmployeeAssetDao = departmentEmployeeAssetDao;
    }

    @Override
    public List<Asset> findAsset(String number, String type, int status, int page, int pageSize) {
        Map map = MapUtil.getMap(0, null);
        map.put("number", number);
        map.put("type", type);
        map.put("status", status);
        return this.assetDao.findAsset(map, page, pageSize);
    }

    @Override
    public Asset findAssetById(String id) {
        return this.assetDao.findAssetById(id);
    }

    @Override
    public Integer findAssetTotal(String number, String type, int status) {
        Map map = MapUtil.getMap(0, null);
        map.put("number", number);
        map.put("type", type);
        map.put("status", status);
        return this.assetDao.findAssetTotal(map);
    }

    @Override
    public List<Asset> findAssetByEmployeeId(String employeeId) {
        return this.assetDao.findAssetByEmployeeId(employeeId);
    }

    @Override
    public List<Asset> findAssetByDepartmentId(String departmentId) {
        return this.assetDao.findAssetByDepartmentId(departmentId);
    }

    @Override
    public ResponseCode addAsset(Asset assets) {
        //资产编号不能重复
        if (this.assetDao.findAssetByNumber(assets.getNumber()) != null) {
            return ResponseCode.ASSET_NUMBER_ALREADY_EXIST;
        }
        //资产型号不能重复
        if (this.assetDao.findAssetByModel(assets.getModel()) != null) {
            return ResponseCode.ASSET_MODEL_ALREADY_EXIST;
        }
        //资产序列号不能重复
        if (this.assetDao.findAssetBySerialNumber(assets.getSerialNumber()) != null) {
            return ResponseCode.ASSET_SERIAL_NUMBER_ALREADY_EXIST;
        }
        int result = this.assetDao.addAsset(assets);
        if (result == 0) {
            return ResponseCode.API_ADD_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    @Transactional
    public ResponseCode deleteAssetById(String id) {
        //资产是否在使用
        if (this.departmentEmployeeAssetDao.findDEAByAssetId(id).size() != 0) {
            return ResponseCode.ASSET_NOT_DELETE_REF;
        }
        int result = this.assetDao.deleteAssetById(id);
        if (result == 0) {
            return ResponseCode.API_DELETE_FAILED;
        }
        //删除资产使用记录
        if (this.assetUseRecordDao.findAssetUseRecord(id).size() != 0) {
            result = this.assetUseRecordDao.deleteAURByAssetId(id);
            if (result == 0) {
                throw new RuntimeException("delete asset use record failed.");
            }
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode updateAssetById(Asset assets) {
        //不能存在相同的编号
        Asset beforeUpdateAsset = this.assetDao.findAssetByNumber(assets.getNumber());
        if (beforeUpdateAsset != null && !beforeUpdateAsset.getId().equals(assets.getId())) {
            return ResponseCode.ASSET_NUMBER_ALREADY_EXIST;
        }
        //不能存在相同的设备型号
        beforeUpdateAsset = this.assetDao.findAssetByModel(assets.getModel());
        if (beforeUpdateAsset != null && !beforeUpdateAsset.getId().equals(assets.getId())) {
            return ResponseCode.ASSET_MODEL_ALREADY_EXIST;
        }
        //不能存在相同的设备序列号
        beforeUpdateAsset = this.assetDao.findAssetBySerialNumber(assets.getSerialNumber());
        if (beforeUpdateAsset != null && !beforeUpdateAsset.getId().equals(assets.getId())) {
            return ResponseCode.ASSET_SERIAL_NUMBER_ALREADY_EXIST;
        }
        int result = this.assetDao.updateAssetById(assets);
        if (result == 0) {
            return ResponseCode.API_UPDATE_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }
}
