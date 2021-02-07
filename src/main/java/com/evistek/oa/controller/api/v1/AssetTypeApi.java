package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.AssetType;
import com.evistek.oa.service.AssetTypeService;
import com.evistek.oa.utils.MapUtil;
import com.evistek.oa.utils.ResponseBase;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.ResponseData;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/13
 */
@RestController
@RequestMapping("/api/v1")
public class AssetTypeApi {
    private AssetTypeService assetTypeService;

    public AssetTypeApi(AssetTypeService assetTypeService) {
        this.assetTypeService = assetTypeService;
    }

    @RequestMapping(value = "/findAssetType", method = RequestMethod.GET)
    public ResponseBase findAssetType(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "token", required = true) String token
    ) {
        if (id != null) {
            AssetType assetType = this.assetTypeService.findAssetTypeById(id);
            Map map = MapUtil.getMap(0, null);
            map.put("assetTypes", assetType);
            map.put("fatherAssetTypes", this.assetTypeService.findFatherAssetType(assetType.getFatherId(), new ArrayList<>()));
            return ResponseData.build(ResponseCode.API_SUCCESS, map);
        }
        return ResponseData.build(ResponseCode.API_SUCCESS, this.assetTypeService.findAssetType());
    }

    @Operation(description = "add asset type")
    @RequestMapping(value = "/addAssetType", method = RequestMethod.POST)
    public ResponseBase addAssetType(
            @RequestBody(required = true) AssetType assetType,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseBase.build(this.assetTypeService.addAssetType(assetType));
    }

    @Operation(description = "delete asset type")
    @RequestMapping(value = "/deleteAssetType", method = RequestMethod.DELETE)
    public ResponseBase deleteAssetType(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseBase.build(this.assetTypeService.deleteAssetTypeById(id));
    }

    @Operation(description = "update asset type")
    @RequestMapping(value = "/updateAssetType", method = RequestMethod.PUT)
    public ResponseBase updateAssetType(
            @RequestBody(required = true) AssetType assetType,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        return ResponseBase.build(this.assetTypeService.updateAssetTypeById(assetType));
    }
}
