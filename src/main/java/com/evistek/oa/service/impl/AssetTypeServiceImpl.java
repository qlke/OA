package com.evistek.oa.service.impl;

import com.evistek.oa.dao.AssetDao;
import com.evistek.oa.dao.AssetTypeDao;
import com.evistek.oa.entity.AssetType;
import com.evistek.oa.service.AssetTypeService;
import com.evistek.oa.utils.ResponseCode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/13
 */
@Service
public class AssetTypeServiceImpl implements AssetTypeService {
    private AssetTypeDao assetTypeDao;
    private AssetDao assetDao;

    public AssetTypeServiceImpl(AssetTypeDao assetTypeDao, AssetDao assetDao) {
        this.assetTypeDao = assetTypeDao;
        this.assetDao = assetDao;
    }

    @Override
    public List<AssetType> findAssetType() {
        return this.assetTypeDao.findAssetType();
    }

    @Override
    public AssetType findAssetTypeById(String id) {
        return this.assetTypeDao.findAssetTypeById(id);
    }

    @Override
    public ResponseCode addAssetType(AssetType assetTypes) {
        if (assetTypes.getFatherId() == "") {
            assetTypes.setFatherId(null);
        }
        int result = this.assetTypeDao.addAssetType(assetTypes);
        if (result == 0) {
            return ResponseCode.API_ADD_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode deleteAssetTypeById(String id) {
        if (this.assetDao.findAssetByTypeId(id).size() != 0) {
            return ResponseCode.ASSET_TYPE_NOT_DELETE_REF;
        }
        int result = this.assetTypeDao.deleteAssetTypeById(id);
        if (result == 0) {
            return ResponseCode.API_DELETE_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode updateAssetTypeById(AssetType assetTypes) {
        int result = this.assetTypeDao.updateAssetTypeById(assetTypes);
        if (result == 0) {
            return ResponseCode.API_UPDATE_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }

    //找到资产类型的上级目录
    public List<AssetType> findFatherAssetType(String fatherId, List<AssetType> resultList) {
        AssetType assetType = this.assetTypeDao.findAssetTypeByFatherId(fatherId);
        if (assetType != null) {
            resultList.add(assetType);
            findFatherAssetType(assetType.getFatherId(), resultList);
        }
        return resultList;
    }
}
