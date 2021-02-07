package com.evistek.oa.controller.api.v1;

import com.evistek.oa.entity.Equipment;
import com.evistek.oa.model.EquipmentBase;
import com.evistek.oa.service.EquipmentService;
import com.evistek.oa.utils.ResponseBase;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2021/1/8
 */
@RestController
@RequestMapping("/api/v1")
public class EquipmentApi {
    EquipmentService equipmentService;

    public EquipmentApi(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }


    @RequestMapping(value = "/addEquipment", method = RequestMethod.POST)
    public ResponseBase addEquipment(
            @RequestParam("token") String token,
            @RequestBody Equipment equipment
    ) {
        ResponseCode responseCode = equipmentService.addEquipment(equipment);
        return ResponseBase.build(responseCode);
    }

    @RequestMapping(value = "/updateEquipment", method = RequestMethod.PUT)
    public ResponseBase updateEquipment(
            @RequestParam("token") String token,
            @RequestBody Equipment equipment
    ) {
        ResponseCode responseCode = equipmentService.updateEquipment(equipment);
        return ResponseBase.build(responseCode);
    }

    @RequestMapping(value = "/deleteEquipment", method = RequestMethod.DELETE)
    public ResponseBase deleteEquipment(
            @RequestParam("token") String token,
            @RequestParam("id") int id
    ) {
        ResponseCode responseCode = equipmentService.deleteEquipment(id);
        return ResponseBase.build(responseCode);
    }

    @RequestMapping(value = "/getEquipment", method = RequestMethod.GET)
    public ResponseBase getEquipment(
            @RequestParam("token") String token,
            @RequestParam("id") int id
    ) {
        Equipment equipment = equipmentService.selectEquipmentById(id);
        return ResponseData.build(ResponseCode.API_SUCCESS, equipment);
    }

    @RequestMapping(value = "/getEquipments", method = RequestMethod.GET)
    public ResponseBase getEquipments(
            @RequestParam("token") String token
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS, equipmentService.selectEquipmentList());
    }

    @RequestMapping(value = "/searchEquipments", method = RequestMethod.GET)
    public ResponseBase searchEquipments(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "chipId", required = false) String chipId,
            @RequestParam(value = "boardId", required = false) String boardId,
            @RequestParam(value = "createUser", required = false) String createUser,
            @RequestParam(value = "snCode", required = false) String snCode,
            @RequestParam(value = "director", required = false) String director,
            @RequestParam(value = "mid", required = false, defaultValue = "-1") int mid,
            @RequestParam(value = "page", defaultValue = "-1", required = false) int page,
            @RequestParam(value = "pageNumber", defaultValue = "-1", required = false) int pageNumber
    ) {
        EquipmentBase equipmentBase = new EquipmentBase();
        equipmentBase.setName(name);
        equipmentBase.setChipId(chipId);
        equipmentBase.setBoardId(boardId);
        equipmentBase.setCreateTime(createUser);
        equipmentBase.setSnCode(snCode);
        equipmentBase.setMid(mid);
        equipmentBase.setDirector(director);
        equipmentBase.setLimit(page, pageNumber);
        return ResponseData.build(ResponseCode.API_SUCCESS, equipmentService.searchEquipment(equipmentBase));
    }
}
