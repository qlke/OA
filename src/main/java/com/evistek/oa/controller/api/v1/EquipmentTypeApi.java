package com.evistek.oa.controller.api.v1;

import com.evistek.oa.entity.EquipmentType;
import com.evistek.oa.model.EquipmentTypeBase;
import com.evistek.oa.service.EquipmentTypeService;
import com.evistek.oa.utils.ResponseBase;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.ResponseData;
import org.springframework.web.bind.annotation.*;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/22
 */
@RestController
@RequestMapping("/api/v1")
public class EquipmentTypeApi {


    private EquipmentTypeService equipmentTypeService;

    public EquipmentTypeApi(EquipmentTypeService equipmentTypeService) {
        this.equipmentTypeService = equipmentTypeService;
    }

    @RequestMapping(value = "/addEquipmentType", method = RequestMethod.POST)
    public ResponseBase addEquipmentType(
            @RequestParam("token") String token,
            @RequestBody EquipmentType equipmentType
    ) {
        ResponseCode responseCode = equipmentTypeService.addEquipmentType(equipmentType);
        return ResponseBase.build(responseCode);
    }

    @RequestMapping(value = "/updateEquipmentType", method = RequestMethod.PUT)
    public ResponseBase updateEquipmentType(
            @RequestParam("token") String token,
            @RequestBody EquipmentType equipmentType
    ) {
        int result = equipmentTypeService.updateEquipmentType(equipmentType);
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }


    @RequestMapping(value = "/deleteEquipmentType", method = RequestMethod.DELETE)
    public ResponseBase deleteEquipmentType(
            @RequestParam("token") String token,
            @RequestParam("id") int id
    ) {
        ResponseCode responseCode=equipmentTypeService.deleteEquipmentType(id);
        return ResponseBase.build(responseCode);
    }

    @RequestMapping(value = "/getEquipmentTypes", method = RequestMethod.GET)
    public ResponseBase selectEquipmentType(
            @RequestParam("token") String token
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS, equipmentTypeService.selectEquipmentTypeListAll());
    }

    @RequestMapping(value = "/getEquipmentTypeDetail", method = RequestMethod.GET)
    public ResponseBase selectEquipmentType(
            @RequestParam("token") String token,
            @RequestParam("id") int id
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS, equipmentTypeService.selectEquipmentTypeById(id));
    }

    @RequestMapping(value = "/searchEquipmentType", method = RequestMethod.GET)
    public ResponseBase searchEquipmentTypes(
            @RequestParam("token") String token,
            @RequestParam(value = "must", required = false, defaultValue = "-1") int must,
            @RequestParam(value = "used", required = false, defaultValue = "-1") int used,
            @RequestParam(value = "inputType", required = false, defaultValue = "-1") int inputType,
            @RequestParam(value = "name", required = false) String name
    ) {
        EquipmentTypeBase equipmentTypeBase = new EquipmentTypeBase();
        equipmentTypeBase.setMust(must);
        equipmentTypeBase.setUsed(used);
        equipmentTypeBase.setInputType(inputType);
        equipmentTypeBase.setName(name);
        return ResponseData.build(ResponseCode.API_SUCCESS, equipmentTypeService.selectEquipmentTypeList(equipmentTypeBase));
    }

    @RequestMapping(value = "/swapEpType", method = RequestMethod.PUT)
    public ResponseBase swapEquipmentType(
            @RequestParam("token") String token,
            @RequestParam("upId") int upId,
            @RequestParam("downId") int downId
    ) {
        ResponseCode responseCode = equipmentTypeService.swapEquipmentTypeId(upId, downId);
        return ResponseBase.build(responseCode);
    }
}
