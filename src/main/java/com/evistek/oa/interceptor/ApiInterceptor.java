package com.evistek.oa.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.evistek.oa.utils.ApiSecurityChecker;
import com.evistek.oa.utils.ResponseBase;
import com.evistek.oa.utils.ResponseCode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/2
 */
@Component
public class ApiInterceptor implements HandlerInterceptor {
    private ApiSecurityChecker apiSecurityChecker;

    public ApiInterceptor(ApiSecurityChecker apiSecurityChecker) {
        this.apiSecurityChecker = apiSecurityChecker;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ResponseCode responseCode = apiSecurityChecker.checker(request);
        if (responseCode.getCode() != ResponseCode.API_SUCCESS.getCode()) {
            returnJson(response, JSONObject.toJSONString(ResponseBase.build(responseCode)));
            return false;
        }
        return true;
    }

    private void returnJson(HttpServletResponse response, String common) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(common);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
