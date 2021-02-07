package com.evistek.oa.service.impl;

import com.evistek.oa.model.ResourceFileModel;
import com.evistek.oa.service.ResourceService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/16
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    public static final String BASE_PATH = System.getProperty("catalina.base") + File.separator +
            "webapps" + File.separator + "ROOT";

    @Override
    public ResourceFileModel uploadFile(MultipartFile file) {
        String path = BASE_PATH + File.separator + "resource" + File.separator + "annex";
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            path = "D:" + File.separator + "resource" + File.separator + "annex";
        }
        File filApplicationDir = new File(path);
        if (!filApplicationDir.exists()) {
            boolean result = filApplicationDir.mkdirs();
            if (!result) {
                return null;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(filApplicationDir.getAbsolutePath());
        stringBuilder.append(File.separator);
        String name = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        stringBuilder.append(name);
        ResourceFileModel resourceFileModel=new ResourceFileModel();
        File newFile = new File(stringBuilder.toString());
        try {
            file.transferTo(newFile);
            resourceFileModel.setUrl("/resource/" + "annex" + "/" + newFile.getName());

        } catch (Exception e) {
            if (newFile.exists()) {
                newFile.delete();
            }
            return null;
        }
        return resourceFileModel;
    }
}
