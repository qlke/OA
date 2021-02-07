package com.evistek.oa.service;

import com.evistek.oa.entity.AssetUseRecord;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/12
 */
public interface AssetUseRecordService {
    ResponseCode addAssetUseRecord(AssetUseRecord assetUseRecord, List<String> list);

    ResponseCode updateAssetUseRecordByAssetId(AssetUseRecord assetUseRecord, List<String> list);

    List<AssetUseRecord> findAssetUseRecord(String assetId);
}
