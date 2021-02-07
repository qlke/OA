package com.evistek.oa.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.evistek.oa.service.DingService;
import com.evistek.oa.utils.Constant;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/18
 */
@Service
public class DingServiceImpl implements DingService {
    private Map<Integer, String> tokenMap;
    private boolean isRefreshing = true;
    private boolean isRegister = false;
    private String token = "";

    @PostConstruct
    public void init() {
        tokenMap = Collections.synchronizedMap(new HashMap<>());
        getDDToken();
    }


    @PreDestroy
    public void destroy() {

    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getToken(int type) {
        return null;
    }

    @Override
    public boolean isRefreshing() {
        return isRefreshing;
    }

    @Override
    public void refreshToken() {
        getDDToken();
    }

    @Override
    public void refreshToken(int type) {

    }

    public void getDDToken() {
        String ddToken = "";
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest req = new OapiGettokenRequest();
            req.setAppkey(Constant.DING_APP_KEY);
            req.setAppsecret(Constant.DING_APP_SECRET);
            req.setHttpMethod("GET");
            OapiGettokenResponse rsp = client.execute(req);
            ddToken = rsp.getAccessToken();
            if (!"".equals(ddToken)) {
                token = ddToken;
                isRefreshing = false;
            }
            if (!"".equals(ddToken) && !isRegister) {
                //registerDingCallback(ddToken);
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void getDDToken(String type) {
        String ddToken = "";
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest req = new OapiGettokenRequest();
            req.setAppkey(Constant.DING_APP_KEY);
            req.setAppsecret(Constant.DING_APP_SECRET);
            req.setHttpMethod("GET");
            OapiGettokenResponse rsp = client.execute(req);
            ddToken = rsp.getAccessToken();
            if (!"".equals(ddToken)) {
                token = ddToken;
                isRefreshing = false;
            }
            if (!"".equals(ddToken) && !isRegister) {
                //registerDingCallback(ddToken);
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public long registerDingCallback(String token) {
        long result = -1;
        try {
            List<String> callBackList = new ArrayList<>();
            callBackList.add("check_in");
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/register_call_back");
            OapiCallBackRegisterCallBackRequest req = new OapiCallBackRegisterCallBackRequest();
            req.setCallBackTag(callBackList);
            req.setToken(Constant.DING_TOKEN);
            req.setAesKey(Constant.DING_ENCODING_AES_KEY);
            req.setUrl("http://274z8419u3.qicp.vip/oa/api/v1/dd/dingCallBack");
            OapiCallBackRegisterCallBackResponse rsp = client.execute(req, token);
            System.out.println("test" + rsp.getBody());
            result = rsp.getErrcode();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return result;
    }
}
