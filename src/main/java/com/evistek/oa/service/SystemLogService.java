package com.evistek.oa.service;

import com.evistek.oa.entity.SystemLog;

import java.util.Date;
import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/9
 */
public interface SystemLogService {
    List<SystemLog> findSystemLog(String type, String user, Date start, Date end, int page, int pageSize);

    Integer findSystemLogTotal(String type, String user, Date start, Date end);
}
