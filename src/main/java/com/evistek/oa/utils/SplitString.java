package com.evistek.oa.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/10
 */
public class SplitString {

    public static List<String> getList(String string) {
        String[] roleIds = string.split(",");
        List<String> ids = new ArrayList<>();
        for (String id : roleIds) {
            ids.add(id);
        }
        return ids;
    }
}
