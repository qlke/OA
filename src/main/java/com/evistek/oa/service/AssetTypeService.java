package com.evistek.oa.service;

import com.evistek.oa.entity.AssetType;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/13
 */
public interface AssetTypeService {
    List<AssetType> findAssetType();

    AssetType findAssetTypeById(String id);

    List<AssetType> findFatherAssetType(String fatherId, List<AssetType> resultList);

    ResponseCode addAssetType(AssetType assetTypes);

    ResponseCode deleteAssetTypeById(String id);

    ResponseCode updateAssetTypeById(AssetType assetTypes);
}
