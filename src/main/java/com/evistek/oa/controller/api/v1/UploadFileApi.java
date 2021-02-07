package com.evistek.oa.controller.api.v1;


import com.evistek.oa.model.ResourceFileModel;
import com.evistek.oa.service.ResourceService;
import com.evistek.oa.utils.ResponseBase;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.ResponseData;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/16
 */
@RestController
@RequestMapping("/api/v1")
public class UploadFileApi {
    public UploadFileApi(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    private ResourceService resourceService;

    @RequestMapping(value = "/resUpload", method = RequestMethod.POST)
    public ResponseBase uploadApplicationFile(
            @RequestPart(value = "file") MultipartFile file,
            @RequestParam(value = "token") String token) {
        ResourceFileModel resourceFileModel = resourceService.uploadFile(file);

        return ResponseData.build(ResponseCode.API_SUCCESS, resourceFileModel);
    }
}
