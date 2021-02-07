package com.evistek.oa.utils;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/4
 */
public class ResponseData extends ResponseBase {
    private Object data;

    public static ResponseData build(ResponseCode code, Object data) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(code.getCode());
        responseData.setMsg(code.getMsg());
        responseData.setData(data);
        return responseData;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
