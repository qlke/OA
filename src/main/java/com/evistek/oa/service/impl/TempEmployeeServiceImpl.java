package com.evistek.oa.service.impl;

import com.evistek.oa.dao.TempEmployeeDao;
import com.evistek.oa.entity.TempEmployee;
import com.evistek.oa.service.TempEmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2021/1/11
 */
@Service
public class TempEmployeeServiceImpl implements TempEmployeeService {
    private TempEmployeeDao tempEmployeeDao;

    public TempEmployeeServiceImpl(TempEmployeeDao tempEmployeeDao) {
        this.tempEmployeeDao = tempEmployeeDao;
    }

    @Override
    public List<TempEmployee> findTempEmployee() {
        return this.tempEmployeeDao.findTempEmployee();
    }
}
