package com.evistek.oa.service;

import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/24
 */
public interface SettingService {
    String getSetting(String key);

    Map<String, String> getSettings();

    void setSetting(String key, String value);
}
