package com.evistek.oa.service.impl;

import com.evistek.oa.dao.CheckingInDao;
import com.evistek.oa.entity.CheckingInList;
import com.evistek.oa.entity.CheckingInRecordList;
import com.evistek.oa.entity.CheckingInUser;
import com.evistek.oa.model.*;
import com.evistek.oa.service.CheckingInService;
import com.evistek.oa.utils.DateUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/17
 */
@Service
public class CheckingInServiceImpl implements CheckingInService {
    public CheckingInServiceImpl(CheckingInDao checkingInDao) {
        this.checkingInDao = checkingInDao;
    }

    private CheckingInDao checkingInDao;


    @Override
    public int addCheckingInUser(CheckingInUser checkingInUser) {
        CheckingInUserBase checkingInUserBase = new CheckingInUserBase();
        checkingInUserBase.setPhone(checkingInUser.getPhone());
        CheckingInUser checkingInUserHis = checkingInDao.selectCheckingInUserByPhone(checkingInUserBase);
        if (null != checkingInUserHis) {
            return -1;
        }
        return checkingInDao.addCheckingInUser(checkingInUser);
    }

    @Override
    public int updateCheckingInUser(CheckingInUser checkingInUser) {
        CheckingInUserBase checkingInUserBase = new CheckingInUserBase();
        checkingInUserBase.setPhone(checkingInUser.getPhone());
        CheckingInUser checkingInUserHis = checkingInDao.selectCheckingInUserByPhone(checkingInUserBase);
        if (null == checkingInUserHis) {
            return -1;
        }
        checkingInUser.setDdId(checkingInUserHis.getDdId());
        return checkingInDao.updateCheckingInUser(checkingInUser);
    }

    @Override
    public List<CheckingInUser> selectCheckingInUserList() {
        return checkingInDao.selectCheckingInUserList();
    }

    @Override
    public Integer findCheckingInUserTotal() {
        return this.checkingInDao.findCheckingInUserTotal();
    }

    @Override
    public CheckingInUser selectCheckingInUser(CheckingInUserBase checkingInUserBase) {
        return checkingInDao.selectCheckingInUserByPhone(checkingInUserBase);
    }

    @Override
    public int addCheckingInList(CheckingInList checkingInList) {
        return 0;
    }

    @Override
    public int addCheckingInListList(List<CheckingInList> checkingInLists) {
        int result = 0;
        int repeat = 0;
        for (int a = 0; a < checkingInLists.size(); a++) {
            CheckingInList checkingInList = checkingInLists.get(a);
            CheckingInListBase checkingInListBase = new CheckingInListBase();
            checkingInListBase.setId(checkingInList.getId());
            CheckingInList checkingInListHistory = checkingInDao.selectCheckingInListById(checkingInListBase);
            if (null == checkingInListHistory) {
                int tmp = checkingInDao.addCheckingInList(checkingInList);
                if (tmp > 0) {
                    result++;
                }
            } else {
                repeat++;
            }
        }
        if ((repeat + result) != checkingInLists.size()) {
            return -1;
        }
        return result;
    }

    @Override
    public CheckingInList selectCheckingInlistById(CheckingInListBase checkingInListBase) {
        return checkingInDao.selectCheckingInListById(checkingInListBase);
    }

    @Override
    public int addCheckingInRecordList(CheckingInRecordList checkingInRecordList) {
        return 0;
    }

    @Override
    public CheckingInListDateBase selectCheckingInListDate(CheckingInListBase checkingInListBase) {
        return checkingInDao.selectCheckingInListDate(checkingInListBase);
    }

    @Override
    public List<CheckingInListDateBase> selectCheckingInListDateAll(Long date, int page, int pageNumber) {
        List<CheckingInListDateBase> checkingInListDateBases = new ArrayList<>();
        List<CheckingInUser> checkingInUsers = checkingInDao.selectCheckingInUserList(page, pageNumber);
        if (null != checkingInUsers) {
            for (int a = 0; a < checkingInUsers.size(); a++) {
                CheckingInUser checkingInUser = checkingInUsers.get(a);
                CheckingInListBase checkingInListBase = new CheckingInListBase();
                checkingInListBase.setUserId(checkingInUser.getDdId());
                checkingInListBase.setWorkDate(date);
                CheckingInListDateBase checkingInListDateBase = checkingInDao.selectCheckingInListDate(checkingInListBase);
                checkingInListDateBase.setName(checkingInUser.getName());
                checkingInListDateBase.setPhone(checkingInUser.getPhone());
                checkingInListDateBase.setType(checkingInUser.getType());
                checkingInListDateBase.setWorkDate(DateUtil.getInstance().parseDateString(date));
                checkingInListDateBases.add(checkingInListDateBase);
            }
        }
        return checkingInListDateBases;
    }

    @Override
    public List<CheckingInListDateBase> selectCheckingInListDatePerson(String phone, Date start, Date end) {
        CheckingInUserBase checkingInUserBase = new CheckingInUserBase();
        checkingInUserBase.setPhone(phone);
        CheckingInUser checkingInUser = checkingInDao.selectCheckingInUserByPhone(checkingInUserBase);
        if (null == checkingInUser) {
            return null;
        }
        List<CheckingInListDateBase> checkingInListDateBases = new ArrayList<>();
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(start);
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(end);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
        calendarEnd.set(Calendar.MINUTE, 0);
        calendarEnd.set(Calendar.SECOND, 0);
        calendarEnd.set(Calendar.MILLISECOND, 0);
        if (calendarStart.getTime().getTime() > calendarEnd.getTime().getTime()) {
            return null;
        }
        int count = 0;
        while (calendarStart.getTime().getTime() <= calendarEnd.getTime().getTime()) {
            if (count > 50) {
                break;
            }
            CheckingInListBase checkingInListBase = new CheckingInListBase();
            checkingInListBase.setUserId(checkingInUser.getDdId());
            checkingInListBase.setWorkDate(calendarStart.getTime().getTime());
            CheckingInListDateBase checkingInListDateBase = checkingInDao.selectCheckingInListDate(checkingInListBase);
            checkingInListDateBase.setName(checkingInUser.getName());
            checkingInListDateBase.setPhone(checkingInUser.getPhone());
            checkingInListDateBase.setType(checkingInUser.getType());
            checkingInListDateBase.setWorkDate(DateUtil.getInstance().parseDateString(checkingInListBase.getWorkDate()));
            checkingInListDateBases.add(checkingInListDateBase);
            calendarStart.add(Calendar.DATE, 1);
            count++;
        }

        return checkingInListDateBases;
    }

    @Override
    public CheckingInStatisticsModel selectCheckingInSingular(String phone, Date start, Date end) {
        CheckingInStatisticsModel checkingInStatisticsModel = new CheckingInStatisticsModel();
        CheckingInUserBase checkingInUserBase = new CheckingInUserBase();
        checkingInUserBase.setPhone(phone);
        CheckingInUser checkingInUser = checkingInDao.selectCheckingInUserByPhone(checkingInUserBase);
        if (null == checkingInUser) {
            return null;
        }
        List<CheckingInListDateBase> checkingInListDateBases = new ArrayList<>();
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(start);
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(end);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
        calendarEnd.set(Calendar.MINUTE, 0);
        calendarEnd.set(Calendar.SECOND, 0);
        calendarEnd.set(Calendar.MILLISECOND, 0);
        if (calendarStart.getTime().getTime() > calendarEnd.getTime().getTime()) {
            return checkingInStatisticsModel;
        }
        int count = 0;
        while (calendarStart.getTime().getTime() <= calendarEnd.getTime().getTime()) {
            boolean isAbnormal = false;
            if (count > 31) {
                break;
            }
            CheckingInListBase checkingInListBase = new CheckingInListBase();
            checkingInListBase.setUserId(checkingInUser.getDdId());
            checkingInListBase.setWorkDate(calendarStart.getTime().getTime());
            CheckingInListDateBase checkingInListDateBase = checkingInDao.selectCheckingInListDate(checkingInListBase);
            if (null != checkingInListDateBase) {
                checkingInListDateBase.setName(checkingInUser.getName());
                checkingInListDateBase.setPhone(checkingInUser.getPhone());
                checkingInListDateBase.setType(checkingInUser.getType());
                checkingInListDateBase.setWorkDate(DateUtil.getInstance().parseDateString(checkingInListBase.getWorkDate()));
                if (null != checkingInListDateBase.getOnDutyTimeResult() && !"Normal".equals(checkingInListDateBase.getOnDutyTimeResult())) {
                    System.out.println("on:" + checkingInListDateBase.getOnDutyTimeResult());
                    switch (checkingInListDateBase.getOnDutyTimeResult()) {
                        case "Late":
                            checkingInStatisticsModel.setLate(checkingInStatisticsModel.getLate() + 1);
                            isAbnormal = true;
                            break;
                        case "SeriousLate":
                            checkingInStatisticsModel.setSeriousLate(checkingInStatisticsModel.getSeriousLate() + 1);
                            isAbnormal = true;
                            break;
                        case "Absenteeism":
                            checkingInStatisticsModel.setNeglectWork(checkingInStatisticsModel.getNeglectWork() + 1);
                            isAbnormal = true;
                            break;
                        case "NotSigned":
                            checkingInStatisticsModel.setCardMissing(checkingInStatisticsModel.getCardMissing() + 1);
                            isAbnormal = true;
                            break;
                    }
                }
                if (null != checkingInListDateBase.getOffDutyTimeResult() && !"Normal".equals(checkingInListDateBase.getOffDutyTimeResult())) {
                    System.out.println("off:" + checkingInListDateBase.getOffDutyTimeResult());
                    switch (checkingInListDateBase.getOffDutyTimeResult()) {
                        case "Early":
                            checkingInStatisticsModel.setLeaveEarly(checkingInStatisticsModel.getLeaveEarly() + 1);
                            isAbnormal = true;
                            break;
                        case "Absenteeism":
                            checkingInStatisticsModel.setNeglectWork(checkingInStatisticsModel.getNeglectWork() + 1);
                            isAbnormal = true;
                            break;
                        case "NotSigned":
                            checkingInStatisticsModel.setCardMissing(checkingInStatisticsModel.getCardMissing() + 1);
                            isAbnormal = true;
                            break;
                    }
                }
                if (isAbnormal) {
                    checkingInListDateBases.add(checkingInListDateBase);
                }
            }
            isAbnormal = false;
            calendarStart.add(Calendar.DATE, 1);
            count++;
        }
        checkingInStatisticsModel.setCheckingInListDateBases(checkingInListDateBases);

        return checkingInStatisticsModel;
    }

    @Override
    public int addCheckingInRecordListList(List<CheckingInRecordList> checkingInRecordLists) {
        int result = -1;
        int repeat = 0;
        for (int a = 0; a < checkingInRecordLists.size(); a++) {
            CheckingInRecordList checkingInRecordList = checkingInRecordLists.get(a);
            CheckingInRecordListBase checkingInRecordListBase = new CheckingInRecordListBase();
            checkingInRecordListBase.setId(checkingInRecordList.getId());
            CheckingInRecordList checkingInRecordListHis = checkingInDao.selectCheckingRecordListById(checkingInRecordListBase);
            if (null == checkingInRecordListHis) {
                int tmp = checkingInDao.addCheckingInRecordList(checkingInRecordList);
                if (tmp > 0) {
                    result++;
                }
            } else {
                repeat++;
            }
        }
        if ((repeat + result) != checkingInRecordLists.size()) {
            return -1;
        }
        return result;
    }

    @Override
    public CheckingInRecordListModel selectCheckingInRecordListByRecordId(CheckingInRecordListBase checkingInRecordListBase) {
        CheckingInRecordListModel checkingInRecordListModel = new CheckingInRecordListModel();
        CheckingInRecordList checkingInRecordList = checkingInDao.selectCheckingRecordListById(checkingInRecordListBase);
        if (null == checkingInRecordList) {
            return null;
        }
        checkingInRecordListModel.setCheckType(checkingInRecordList.getCheckType());
        checkingInRecordListModel.setBaseMacAddr(checkingInRecordList.getBaseMacAddr());
        checkingInRecordListModel.setLocationResult(checkingInRecordList.getLocationResult());
        checkingInRecordListModel.setIsLegal(checkingInRecordList.getIsLegal());
        checkingInRecordListModel.setBaseCheckTime(checkingInRecordList.getBaseCheckTime());
        checkingInRecordListModel.setTimeResult(checkingInRecordList.getTimeResult());
        checkingInRecordListModel.setUserId(checkingInRecordList.getUserId());
        checkingInRecordListModel.setUserAddress(checkingInRecordList.getUserAddress());
        checkingInRecordListModel.setWorkDate(checkingInRecordList.getWorkDate());
        checkingInRecordListModel.setSourceType(checkingInRecordList.getSourceType());
        checkingInRecordListModel.setUserCheckTime(checkingInRecordList.getUserCheckTime());
        checkingInRecordListModel.setLocationMethod(checkingInRecordList.getLocationMethod());
        checkingInRecordListModel.setPlanId(checkingInRecordList.getPlanId() + "");
        checkingInRecordListModel.setId(checkingInRecordList.getId());
        return checkingInRecordListModel;
    }
}
