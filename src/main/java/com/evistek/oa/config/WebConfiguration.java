package com.evistek.oa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/16
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private Environment environment;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            registry.addResourceHandler(environment.getProperty("dir.annex"))
                    .addResourceLocations(environment.getProperty("dir.annex.resource.win"));
            registry.addResourceHandler(environment.getProperty("dir.excel"))
                    .addResourceLocations(environment.getProperty("dir.excel.resource.win"));
        } else {
            registry.addResourceHandler(environment.getProperty("dir.annex"))
                    .addResourceLocations(environment.getProperty("dir.annex.resource.linux"));
            registry.addResourceHandler(environment.getProperty("dir.excel"))
                    .addResourceLocations(environment.getProperty("dir.excel.resource.linux"));
        }
    }
}
