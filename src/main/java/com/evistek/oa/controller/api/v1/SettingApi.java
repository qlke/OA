package com.evistek.oa.controller.api.v1;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.service.SettingService;
import com.evistek.oa.utils.ResponseBase;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/24
 */
@RestController
@RequestMapping("/api/v1")
public class SettingApi {
    private SettingService settingService;

    public SettingApi(SettingService settingService) {
        this.settingService = settingService;
    }

    /**
     * 设置系统参数
     *
     * @param key     键值对中的key值
     * @param value   键值对中的value值
     * @param token   用户token
     * @param request HTTP请求
     * @return 响应操作信息
     */
    @Operation(description = "set parameter")
    @RequestMapping(value = "/setParameter", method = RequestMethod.PUT)
    public ResponseBase setParameter(
            @RequestParam(value = "key", required = true) String key,
            @RequestParam(value = "value", required = true) String value,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        this.settingService.setSetting(key, value);
        String result = this.settingService.getSetting(key);
        if (result == null) {
            return ResponseBase.build(ResponseCode.SETTING_NOT_FIND_KEY);
        }
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }

    /**
     * 查找系统参数
     *
     * @param key     键值对中的key值
     * @param token   用户token
     * @param request HTTP请求
     * @return 响应系统参数
     */
    @RequestMapping(value = "/findParameter", method = RequestMethod.GET)
    public ResponseBase findParameter(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request
    ) {
        if (key != null) {
            Object result = this.settingService.getSetting(key);
            if (result == null) {
                return ResponseBase.build(ResponseCode.SETTING_NOT_FIND_KEY);
            }
            return ResponseData.build(ResponseCode.API_SUCCESS, new Setting(key, result));
        }
        List<Setting> settingList = new ArrayList<>();
        for (Map.Entry<String, String> entry : this.settingService.getSettings().entrySet()) {
            settingList.add(new Setting(entry.getKey(), entry.getValue()));
        }
        return ResponseData.build(ResponseCode.API_SUCCESS, settingList);

    }

    class Setting {
        String key;
        Object value;

        public Setting(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
