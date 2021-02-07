package com.evistek.oa.controller.api.v1;

import com.evistek.oa.entity.EquipmentBindType;
import com.evistek.oa.service.EquipmentBindTypeService;
import com.evistek.oa.utils.ResponseBase;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.ResponseData;
import org.springframework.web.bind.annotation.*;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/25
 */
@RestController
@RequestMapping("/api/v1")
public class EquipmentBindTypeApi {


    private EquipmentBindTypeService equipmentBindTypeService;

    public EquipmentBindTypeApi(EquipmentBindTypeService equipmentBindTypeService) {
        this.equipmentBindTypeService = equipmentBindTypeService;
    }

    @RequestMapping(value = "/addEmBindType", method = RequestMethod.POST)
    public ResponseBase addBindType(
            @RequestParam("token") String token,
            @RequestBody EquipmentBindType equipmentBindType
    ) {
        int result = equipmentBindTypeService.addEquipmentBindType(equipmentBindType);
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }

    @RequestMapping(value = "/updateEmBindType", method = RequestMethod.POST)
    public ResponseBase updateBindType(
            @RequestParam("token") String token,
            @RequestBody EquipmentBindType equipmentBindType
    ) {
        int result = equipmentBindTypeService.updateEquipmentBindType(equipmentBindType);
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }

    @RequestMapping(value = "/deleteEmBindType", method = RequestMethod.DELETE)
    public ResponseBase deleteBindType(
            @RequestParam("token") String token,
            @RequestParam("mid") int mid,
            @RequestParam("tid") int tid
    ) {
        EquipmentBindType equipmentBindType = new EquipmentBindType();
        equipmentBindType.setMid(mid);
        equipmentBindType.setTid(tid);
        int result = equipmentBindTypeService.deleteEquipmentBidType(equipmentBindType);
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }

    @RequestMapping(value = "/getEmBindTypes", method = RequestMethod.GET)
    public ResponseBase selectBindType(
            @RequestParam("token") String token,
            @RequestParam("mid") int mid
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS,equipmentBindTypeService.selectEquipmentByEid(mid));
    }
}
