package com.evistek.oa.dao;

import com.evistek.oa.entity.CheckingInList;
import com.evistek.oa.entity.CheckingInRecordList;
import com.evistek.oa.entity.CheckingInUser;
import com.evistek.oa.model.CheckingInListBase;
import com.evistek.oa.model.CheckingInListDateBase;
import com.evistek.oa.model.CheckingInRecordListBase;
import com.evistek.oa.model.CheckingInUserBase;
import com.evistek.oa.utils.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/19
 */
@Component
public class CheckingInDao {
    public CheckingInDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private SqlSession sqlSession;


    public int addCheckingInUser(CheckingInUser checkingInUser) {
        return sqlSession.insert("addCheckingInUser", checkingInUser);
    }

    public int updateCheckingInUser(CheckingInUser checkingInUser) {
        return sqlSession.update("updateCheckingInUser", checkingInUser);
    }


    public CheckingInUser selectCheckingInUserByPhone(CheckingInUserBase checkingInUserBase) {
        return sqlSession.selectOne("selectCheckingInUserByPhone", checkingInUserBase);
    }

    public List<CheckingInUser> selectCheckingInUserList() {
        return sqlSession.selectList("selectCheckingInUserList");
    }

    public List<CheckingInUser> selectCheckingInUserList(int page, int pageNumber) {
        return sqlSession.selectList("selectCheckingInUserList", null, PageHelper.getRowBounds(page, pageNumber));
    }

    public Integer findCheckingInUserTotal() {
        return this.sqlSession.selectOne("findCheckingInUserTotal");
    }

    public int addCheckingInList(CheckingInList checkingInList) {
        return sqlSession.insert("addCheckingInList", checkingInList);
    }

    public CheckingInList selectCheckingInListById(CheckingInListBase checkingInListBase) {
        return sqlSession.selectOne("selectCheckingInListById", checkingInListBase);
    }

    public CheckingInListDateBase selectCheckingInListDate(CheckingInListBase checkingInListBase) {
        List<CheckingInList> checkingInLists = sqlSession.selectList("selectCheckingInListDate", checkingInListBase);
        CheckingInListDateBase checkingInListDateBase = new CheckingInListDateBase();
        if (null != checkingInLists) {
            for (int a = 0; a < checkingInLists.size(); a++) {
                CheckingInList checkingInList = checkingInLists.get(a);
                if ("OnDuty".equals(checkingInList.getCheckType())) {
                    checkingInListDateBase.setOnDutyTime(checkingInList.getUserCheckTime());
                    checkingInListDateBase.setOnDutyTimeResult(checkingInList.getTimeResult());
                    checkingInListDateBase.setOnDutyRecordId(checkingInList.getRecordId());
                } else if ("OffDuty".equals(checkingInList.getCheckType())) {
                    checkingInListDateBase.setOffDutyTime(checkingInList.getUserCheckTime());
                    checkingInListDateBase.setOffDutyTimeResult(checkingInList.getTimeResult());
                    checkingInListDateBase.setOffDutyRecordId(checkingInList.getRecordId());
                }
            }
        }
        return checkingInListDateBase;
    }

    public int addCheckingInRecordList(CheckingInRecordList checkingInRecordList) {
        return sqlSession.insert("addCheckingInRecordList", checkingInRecordList);
    }

    public CheckingInRecordList selectCheckingRecordListById(CheckingInRecordListBase checkingInRecordListBase) {
        return sqlSession.selectOne("selectCheckingInRecordListById", checkingInRecordListBase);
    }


}
