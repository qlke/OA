package com.evistek.oa.utils;

import com.evistek.oa.entity.Employee;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/9
 */
public class PageHelper {
    /**
     * 分页
     *
     * @param pageNum  每页数量
     * @param pageSize 页数
     * @return
     */
    public static RowBounds getRowBounds(int pageNum, int pageSize) {
        int num = pageNum > 0 ? pageNum : RowBounds.NO_ROW_OFFSET;
        int size = pageSize > 0 ? pageSize : RowBounds.NO_ROW_LIMIT;
        return new RowBounds(size * (num - 1), size);
    }

    public static List<Object> pageList(List<Object> list, int page, int pageSize) {
        int count = list.size();
        List pageList = new ArrayList<>();
        int currId = page > 1 ? (page - 1) * pageSize : 0;
        for (int i = 0; i < pageSize && i < count - currId; i++) {
            pageList.add(list.get(currId + i));
        }
        return pageList;
    }

    public static List<Employee> empPages(List<Employee> employees, int page, int pageSize) {
        int count = employees.size();
        List pageList = new ArrayList<>();
        int currId = page > 1 ? (page - 1) * pageSize : 0;
        for (int i = 0; i < pageSize && i < count - currId; i++) {
            pageList.add(employees.get(currId + i));
        }
        return pageList;
    }
}
