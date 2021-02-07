package com.evistek.oa.dao;

import com.evistek.oa.entity.SystemLog;
import com.evistek.oa.utils.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/9
 */
@Component
public class SystemLogDao {
    private SqlSession sqlSession;

    public SystemLogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int addSystemLog(SystemLog systemLog) {
        return this.sqlSession.insert("addSystemLog", systemLog);
    }

    public List<SystemLog> findSystemLog(String type, String user, Date start, Date end, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("user", user);
        map.put("start", start);
        map.put("end", end);
        return this.sqlSession.selectList("findSystemLog", map, PageHelper.getRowBounds(page, pageSize));
    }

    public Integer findSystemLogTotal(String type, String user, Date start, Date end) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("user", user);
        map.put("start", start);
        map.put("end", end);
        return this.sqlSession.selectOne("findSystemLogTotal", map);
    }

    public int deleteSysLog(Date time) {
        return this.sqlSession.delete("deleteSysLog", time);
    }
}
