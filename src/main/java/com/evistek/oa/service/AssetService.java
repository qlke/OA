package com.evistek.oa.service;

import com.evistek.oa.entity.Asset;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/11
 */
public interface AssetService {
    List<Asset> findAsset(String number, String type, int status, int page, int pageSize);

    Asset findAssetById(String id);

    Integer findAssetTotal(String number, String type, int status);

    List<Asset> findAssetByEmployeeId(String employeeId);

    List<Asset> findAssetByDepartmentId(String departmentId);

    ResponseCode addAsset(Asset assets);

    ResponseCode deleteAssetById(String id);

    ResponseCode updateAssetById(Asset assets);
}
