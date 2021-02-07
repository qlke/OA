package com.evistek.oa.utils;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/2
 */
public class RemoveRepetitionUtil {
    //去重函数
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> set = ConcurrentHashMap.newKeySet();
        return t -> set.add(keyExtractor.apply(t));
    }
}
