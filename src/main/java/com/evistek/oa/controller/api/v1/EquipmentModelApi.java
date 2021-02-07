package com.evistek.oa.controller.api.v1;

import com.evistek.oa.model.EquipmentModelBase;
import com.evistek.oa.model.EquipmentModelModel;
import com.evistek.oa.service.EquipmentModelService;
import com.evistek.oa.utils.ResponseBase;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.ResponseData;
import org.springframework.web.bind.annotation.*;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/28
 */
@RestController
@RequestMapping("/api/v1")
public class EquipmentModelApi {
    private EquipmentModelService equipmentModelService;

    public EquipmentModelApi(EquipmentModelService equipmentModelService) {
        this.equipmentModelService = equipmentModelService;
    }

    @RequestMapping(value = "/addEmModel", method = RequestMethod.POST)
    public ResponseBase addEquipmentModel(
            @RequestParam("token") String token,
            @RequestBody EquipmentModelModel equipmentModelModel
    ) {
        ResponseCode responseCode = equipmentModelService.addEquipmentModel(equipmentModelModel);
        return ResponseData.build(responseCode);
    }

    @RequestMapping(value = "/updateEmModel", method = RequestMethod.PUT)
    public ResponseBase updateEquipmentModel(
            @RequestParam("token") String token,
            @RequestBody EquipmentModelModel equipmentModelModel
    ) {
        int result = equipmentModelService.updateEquipmentModel(equipmentModelModel);
        return ResponseData.build(ResponseCode.API_SUCCESS, result);
    }

    @RequestMapping(value = "/deleteEmModelId", method = RequestMethod.DELETE)
    public ResponseBase deleteEquipmentModelId(
            @RequestParam("token") String token,
            @RequestParam("id") int id
    ) {
        int result = equipmentModelService.deleteEquipmentModel(id);
        if (result==-1){
            return ResponseData.build(ResponseCode.EQUIPMENT_MODEL_ERROR);
        }
        return ResponseData.build(ResponseCode.API_SUCCESS);
    }

    @RequestMapping(value = "/getEmModelId", method = RequestMethod.GET)
    public ResponseBase getEquipmentModelId(
            @RequestParam("token") String token,
            @RequestParam("id") int id
    ) {
        EquipmentModelModel equipmentModelModel = equipmentModelService.getEquipmentModelById(id);
        return ResponseData.build(ResponseCode.API_SUCCESS, equipmentModelModel);
    }

    @RequestMapping(value = "/getEmModelMCode", method = RequestMethod.GET)
    public ResponseBase getEquipmentModelMCode(
            @RequestParam("token") String token,
            @RequestParam("mCode") String mCode
    ) {
        EquipmentModelModel equipmentModelModel = equipmentModelService.getEquipmentModelByMCode(mCode);
        return ResponseData.build(ResponseCode.API_SUCCESS, equipmentModelModel);
    }

    @RequestMapping(value = "/getEmModels", method = RequestMethod.GET)
    public ResponseBase getEquipmentModels(
            @RequestParam("token") String token
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS, equipmentModelService.getEquipmentModelsBase());
    }
    @RequestMapping(value = "/searchEmModels", method = RequestMethod.GET)
    public ResponseBase searchEquipmentModels(
            @RequestParam(value = "token",required = false) String token,
            @RequestParam(value = "mCode",required = false) String mCode,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "page", defaultValue = "-1", required = false) int page,
            @RequestParam(value = "pageNumber", defaultValue = "-1", required = false) int pageNumber
    ) {
        EquipmentModelBase equipmentModelBase=new EquipmentModelBase();
        equipmentModelBase.setmCode(mCode);
        equipmentModelBase.setName(name);
        equipmentModelBase.setLimit(page,pageNumber);
        return ResponseData.build(ResponseCode.API_SUCCESS, equipmentModelService.searchEquipmentModelsBase(equipmentModelBase));
    }
}
