package com.evistek.oa.utils;

import java.util.UUID;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/5
 */
public class UuidUtil {
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
