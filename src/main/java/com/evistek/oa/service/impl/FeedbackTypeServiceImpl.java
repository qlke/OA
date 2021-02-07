package com.evistek.oa.service.impl;

import com.evistek.oa.dao.FeedbackTypeDao;
import com.evistek.oa.entity.FeedbackType;
import com.evistek.oa.entity.TempEmployee;
import com.evistek.oa.model.FeedbackTypeModel;
import com.evistek.oa.service.FeedbackTypeService;
import com.evistek.oa.service.TempEmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/14
 */
@Service
public class FeedbackTypeServiceImpl implements FeedbackTypeService {

    private FeedbackTypeDao feedbackTypeDao;
    private TempEmployeeService tempEmployeeService;

    public FeedbackTypeServiceImpl(FeedbackTypeDao feedbackTypeDao, TempEmployeeService tempEmployeeService) {
        this.feedbackTypeDao = feedbackTypeDao;
        this.tempEmployeeService = tempEmployeeService;
    }

    @Override
    public List<FeedbackType> selectFeedbackList() {
        return feedbackTypeDao.selectFeedBackTypeList();
    }

    @Override
    public List<FeedbackTypeModel> selectFeedbackListModel() {
        List<FeedbackTypeModel> feedbackTypeModelList = feedbackTypeDao.selectFeedBackTypeModelList();
        List<TempEmployee> tempEmployeeList = tempEmployeeService.findTempEmployee();
        if (null != feedbackTypeModelList && null != tempEmployeeList) {
            for (int a = 0; a < feedbackTypeModelList.size(); a++) {
                String director = feedbackTypeModelList.get(a).getDirector();
                if (!"".equals(director) && null != director) {
                    for (int b = 0; b < tempEmployeeList.size(); b++) {
                        if (director.equals(tempEmployeeList.get(b).getEmployeeId())) {
                            feedbackTypeModelList.get(a).setTempEmployee(tempEmployeeList.get(b));
                            break;
                        }
                    }
                }
            }
        }
        return feedbackTypeModelList;
    }
}
