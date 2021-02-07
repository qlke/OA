package com.evistek.oa.service;

import com.evistek.oa.entity.CheckingInList;
import com.evistek.oa.entity.CheckingInRecordList;
import com.evistek.oa.entity.CheckingInUser;
import com.evistek.oa.model.*;

import java.util.Date;
import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/17
 */
public interface CheckingInService {
    int addCheckingInUser(CheckingInUser checkingInUser);

    int updateCheckingInUser(CheckingInUser checkingInUser);

    List<CheckingInUser> selectCheckingInUserList();

    Integer findCheckingInUserTotal();

    CheckingInUser selectCheckingInUser(CheckingInUserBase checkingInUserBase);

    int addCheckingInList(CheckingInList checkingInList);

    int addCheckingInListList(List<CheckingInList> checkingInLists);

    CheckingInList selectCheckingInlistById(CheckingInListBase checkingInListBase);

    int addCheckingInRecordList(CheckingInRecordList checkingInRecordList);

    CheckingInListDateBase selectCheckingInListDate(CheckingInListBase checkingInListBase);

    List<CheckingInListDateBase> selectCheckingInListDateAll(Long date, int page, int pageNumber);

    List<CheckingInListDateBase> selectCheckingInListDatePerson(String phone, Date start, Date end);

    CheckingInStatisticsModel selectCheckingInSingular(String phone, Date start, Date end);

    int addCheckingInRecordListList(List<CheckingInRecordList> checkingInRecordLists);

    CheckingInRecordListModel selectCheckingInRecordListByRecordId(CheckingInRecordListBase checkingInRecordListBase);

}
