package com.evistek.oa.service;


import com.evistek.oa.model.ResourceFileModel;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/16
 */
public interface ResourceService {
    ResourceFileModel uploadFile(MultipartFile file);


}
