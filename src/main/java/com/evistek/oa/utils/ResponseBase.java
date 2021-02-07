package com.evistek.oa.utils;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/4
 */
public class ResponseBase {
    private int code;
    private String msg;

    public static ResponseBase build(ResponseCode code) {
        ResponseBase responseBase = new ResponseBase();
        responseBase.setCode(code.getCode());
        responseBase.setMsg(code.getMsg());
        return responseBase;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
