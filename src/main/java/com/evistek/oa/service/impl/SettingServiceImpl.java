package com.evistek.oa.service.impl;

import com.evistek.oa.config.SystemSetting;
import com.evistek.oa.service.SettingService;
import com.evistek.oa.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/24
 */
@Service
public class SettingServiceImpl implements SettingService {
    private final SystemSetting setting;
    private final Logger logger;
    private Properties properties;

    public SettingServiceImpl(SystemSetting setting) {
        this.setting = setting;
        this.logger = LoggerFactory.getLogger(getClass());
        initProperties();
    }

    private void initProperties() {
        this.properties = new Properties();
        this.properties.setProperty(Constant.KEY_RETAIN_LOG, String.valueOf(setting.getRetainLog()));
        this.properties.setProperty(Constant.KEY_SALE, String.valueOf(setting.getSale()));
        this.properties.setProperty(Constant.KEY_PMC, String.valueOf(setting.getPmc()));
        this.properties.setProperty(Constant.KEY_ENGINEER, String.valueOf(setting.getEngineer()));
        this.properties.setProperty(Constant.KEY_FINANCE, String.valueOf(setting.getFinance()));
    }

    private void saveProperties(Properties properties) {
        ClassPathResource classPathResource = new ClassPathResource(Constant.SOURCE_FILE);
        FileOutputStream fos = null;
        try {
            logger.info("save properties to file: " + classPathResource.getFile());
            fos = new FileOutputStream(classPathResource.getFile());
            properties.store(fos, "System setting");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getSetting(String key) {
        String value = null;
        switch (key) {
            case Constant.KEY_RETAIN_LOG:
                value = this.setting.getRetainLog();
                break;
            case Constant.KEY_SALE:
                value = this.setting.getSale();
                break;
            case Constant.KEY_PMC:
                value = this.setting.getPmc();
                break;
            case Constant.KEY_ENGINEER:
                value = this.setting.getEngineer();
                break;
            case Constant.KEY_FINANCE:
                value = this.setting.getFinance();
                break;
        }
        return value;
    }

    @Override
    public Map<String, String> getSettings() {
        Map<String, String> map = new HashMap<>();
        map.put(Constant.KEY_RETAIN_LOG, getSetting(Constant.KEY_RETAIN_LOG));
        map.put(Constant.KEY_SALE, getSetting(Constant.KEY_SALE));
        map.put(Constant.KEY_PMC, getSetting(Constant.KEY_PMC));
        map.put(Constant.KEY_ENGINEER, getSetting(Constant.KEY_ENGINEER));
        map.put(Constant.KEY_FINANCE, getSetting(Constant.KEY_FINANCE));
        return map;
    }

    @Override
    public void setSetting(String key, String value) {
        switch (key) {
            case Constant.KEY_RETAIN_LOG:
                this.setting.setRetainLog(value);
                break;
            case Constant.KEY_SALE:
                this.setting.setSale(value);
                break;
            case Constant.KEY_PMC:
                this.setting.setPmc(value);
                break;
            case Constant.KEY_ENGINEER:
                this.setting.setEngineer(value);
                break;
            case Constant.KEY_FINANCE:
                this.setting.setFinance(value);
                break;
            default:
                return;
        }
        if (value == null) {
            return;
        }
        //发生改变时，更新到物理文件中
        if (value != properties.getProperty(key)) {
            properties.setProperty(key, value);
            saveProperties(properties);
        }
    }
}
