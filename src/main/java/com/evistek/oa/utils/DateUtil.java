package com.evistek.oa.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/25
 */
public class DateUtil {
    private Logger logger = LoggerFactory.getLogger(DateUtil.class);
    private static DateUtil dateUtil;
    private SimpleDateFormat simpleDateFormat;

    private DateUtil() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }


    public static DateUtil getInstance() {
        if (null == dateUtil) {
            synchronized (DateUtil.class) {
                if (null == dateUtil) {
                    dateUtil = new DateUtil();
                }
            }
        }
        return dateUtil;
    }

    public String formatDateString(String dateLong) {
        String date;
        try {
            date = formatDateLong(Long.valueOf(dateLong));
        } catch (Exception e) {
            date = "error";
            logger.error("format error:" + dateLong);
        }
        return date;
    }

    public String formatDateLong(long date) {
        String dates;
        try {
            dates = simpleDateFormat.format(new Date(date));
        } catch (Exception e) {
            dates = "error";
            logger.error("format error:" + date);
        }
        return dates;
    }

    public Date parseDateString(String date) {
        Date dates = null;
        try {
            dates = simpleDateFormat.parse(date);
        } catch (Exception e) {
            System.out.println(e);
            logger.error("format error:" + date);
        }
        return dates;
    }

    public String parseDateString(Long date) {
        Date dateTmp = new Date(date);
        String dates="error";
        try {
            dates = simpleDateFormat.format(dateTmp);
        } catch (Exception e) {
            System.out.println(e);
            logger.error("format error:" + date);
        }
        return dates;
    }

    public Long parseDateLong(String date) {
        long dateResult = -1;
        Date dates = parseDateString(date);
        if (null != dates) {
            dateResult = dates.getTime();
        }
        return dateResult;
    }
}
