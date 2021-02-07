package com.evistek.oa.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/10
 */
public class MapUtil {

    public static Map getMap(Integer total, List list) {
        Map map = new HashMap();
        map.put("total", total);
        map.put("list", list);
        return map;
    }
}
