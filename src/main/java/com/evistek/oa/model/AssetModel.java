package com.evistek.oa.model;

import com.evistek.oa.entity.AssetUseRecord;
import com.evistek.oa.entity.Asset;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/20
 */
public class AssetModel extends Asset {
    private AssetUseRecord assetUseRecord;

    public AssetUseRecord getAssetUseRecord() {
        return assetUseRecord;
    }

    public void setAssetUseRecord(AssetUseRecord assetUseRecord) {
        this.assetUseRecord = assetUseRecord;
    }
}
