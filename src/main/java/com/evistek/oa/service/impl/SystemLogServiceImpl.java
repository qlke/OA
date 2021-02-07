package com.evistek.oa.service.impl;

import com.evistek.oa.dao.SystemLogDao;
import com.evistek.oa.entity.SystemLog;
import com.evistek.oa.service.SystemLogService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/9
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {
    private SystemLogDao systemLogDao;

    public SystemLogServiceImpl(SystemLogDao systemLogDao) {
        this.systemLogDao = systemLogDao;
    }

    @Override
    public List<SystemLog> findSystemLog(String type, String user, Date start, Date end, int page, int pageSize) {
        return this.systemLogDao.findSystemLog(type, user, start, end, page, pageSize);
    }

    @Override
    public Integer findSystemLogTotal(String type, String user, Date start, Date end) {
        return this.systemLogDao.findSystemLogTotal(type, user, start, end);
    }
}
