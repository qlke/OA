package com.evistek.oa.controller.api.v1;

import com.evistek.oa.entity.EquipmentAttribute;
import com.evistek.oa.model.EquipmentAttributeBase;
import com.evistek.oa.service.EquipmentAttributeService;
import com.evistek.oa.utils.ResponseBase;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.ResponseData;
import org.springframework.web.bind.annotation.*;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/23
 */
@RestController
@RequestMapping("/api/v1")
public class EquipmentAttributeApi {
    private EquipmentAttributeService equipmentAttributeService;

    public EquipmentAttributeApi(EquipmentAttributeService equipmentAttributeService) {
        this.equipmentAttributeService = equipmentAttributeService;
    }

    @RequestMapping(value = "/addEquipmentAttribute", method = RequestMethod.POST)
    public ResponseBase addEquipmentAttribute(
            @RequestParam("token") String token,
            @RequestBody EquipmentAttribute equipmentAttribute
    ) {
        int result = equipmentAttributeService.addEquipmentAttribute(equipmentAttribute);
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }

    @RequestMapping(value = "/updateEquipmentAttribute", method = RequestMethod.PUT)
    public ResponseBase updateEquipmentAttribute(
            @RequestParam("token") String token,
            @RequestBody EquipmentAttribute equipmentAttribute
    ) {
        int result = equipmentAttributeService.updateEquipmentAttribute(equipmentAttribute);
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }

    @RequestMapping(value = "/deleteEquipmentAttribute", method = RequestMethod.DELETE)
    public ResponseBase deleteEquipmentAttribute(
            @RequestParam("token") String token,
            @RequestParam("id") int id
    ) {
        ResponseCode responseCode = equipmentAttributeService.deleteEquipmentAttribute(id);
        return ResponseBase.build(responseCode);
    }

    @RequestMapping(value = "/getEquipmentAttribute", method = RequestMethod.GET)
    public ResponseBase selectEquipmentAttributeDetail(
            @RequestParam("token") String token,
            @RequestParam("id") int id
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS, equipmentAttributeService.selectEquipmentAttributeById(id));
    }

    @RequestMapping(value = "/getEquipmentAttributes", method = RequestMethod.GET)
    public ResponseBase selectEquipmentAttributes(
            @RequestParam("token") String token
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS, equipmentAttributeService.selectEquipmentAttributeListAll());
    }

    @RequestMapping(value = "/searchEquipmentAttribute", method = RequestMethod.GET)
    public ResponseBase searchEquipmentAttributes(
            @RequestParam("token") String token,
            @RequestParam(value = "tid",required = false,defaultValue = "-1") int tid,
            @RequestParam(value = "used",required = false,defaultValue = "-1") int used,
            @RequestParam(value = "name",required = false) String name
    ) {
        EquipmentAttributeBase equipmentAttributeBase = new EquipmentAttributeBase();
        equipmentAttributeBase.setName(name);
        equipmentAttributeBase.setTid(tid);
        equipmentAttributeBase.setUsed(used);
        return ResponseData.build(ResponseCode.API_SUCCESS, equipmentAttributeService.selectEquipmentAttributeList(equipmentAttributeBase));
    }
}
