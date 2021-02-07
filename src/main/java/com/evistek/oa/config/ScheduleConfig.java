package com.evistek.oa.config;

import com.evistek.oa.dao.SystemLogDao;
import com.evistek.oa.service.SettingService;
import com.evistek.oa.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2021/1/12
 */
@Component
public class ScheduleConfig {
    private SettingService settingService;
    private SystemLogDao systemLogDao;
    private ScheduledThreadPoolExecutor executor;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final long SETTING_TASK_INITIAL_DELAY = 1;
    private static final long SETTING_TASK_PERIOD = 24;
    private static final TimeUnit SETTING_TASK_TIME_UNIT = TimeUnit.HOURS;
    private static final int CORE_SIZE = 1;

    public ScheduleConfig(SettingService settingService, SystemLogDao systemLogDao) {
        this.settingService = settingService;
        this.systemLogDao = systemLogDao;
        this.executor = new ScheduledThreadPoolExecutor(CORE_SIZE);
        executor.scheduleAtFixedRate(new CleanSysLogTask(), SETTING_TASK_INITIAL_DELAY, SETTING_TASK_PERIOD, SETTING_TASK_TIME_UNIT);
    }

    private class CleanSysLogTask implements Runnable {
        @Override
        public void run() {
            logger.info("start the system log cleanup task.");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -(Integer.parseInt(settingService.getSetting(Constant.KEY_RETAIN_LOG))));
            Date time = calendar.getTime();
            int result = systemLogDao.deleteSysLog(time);
            if (result == 0) {
                logger.error("the system log cleanup task failed.");
            }
        }
    }
}
