package com.evistek.oa.utils;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/6
 */
public class MD5Util {
    public static String md5() {
        String defaultPwd = "evistek";
        String md5 = "";
        try {
            md5 = DigestUtils.md5DigestAsHex(defaultPwd.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return md5;
    }
}
