package com.evistek.oa.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/16
 */
public class CodeUtil {
    private static CodeUtil codeUtil;

    public CodeUtil() {
        atomicInteger = new AtomicInteger(0);
    }

    private AtomicInteger atomicInteger;

    public static CodeUtil getInstance() {
        if (null == codeUtil) {
            synchronized (CodeUtil.class) {
                if (null == codeUtil) {
                    codeUtil = new CodeUtil();
                }
            }
        }
        return codeUtil;
    }

    public synchronized String getCodeString(String prefix) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append(System.currentTimeMillis() / 1000);
        int tmp = atomicInteger.addAndGet(1);
        if (tmp < 10) {
            stringBuilder.append("00");
        } else if (tmp < 100) {
            stringBuilder.append("0");
        } else {
            atomicInteger.set(0);
        }
        stringBuilder.append(tmp);
        return stringBuilder.toString();
    }
}
