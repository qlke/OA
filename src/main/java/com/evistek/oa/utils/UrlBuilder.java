package com.evistek.oa.utils;

import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/29
 */
@Component
public class UrlBuilder {
    private final String SOURCE_DIR;

    public UrlBuilder() {
        this.SOURCE_DIR = System.getProperty("os.name").toLowerCase().startsWith("windows") ?
                "D:" + File.separator : System.getProperty("catalina.base") + File.separator;
    }

    public String buildExcelUrl(String fileName) {
        return new StringBuilder()
                .append("/").append("resource")
                .append("/").append("excel")
                .append("/").append(fileName)
                .toString();
    }

    public String getResourcePath() {
        return new StringBuilder()
                .append(SOURCE_DIR).append("resource")
                .append(File.separator).toString();
    }

    public String getExcelPhysicalPath() {
        return new StringBuilder()
                .append(getResourcePath())
                .append("excel").append(File.separator)
                .toString();
    }
}
