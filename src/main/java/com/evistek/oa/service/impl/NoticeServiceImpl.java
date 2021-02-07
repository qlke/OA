package com.evistek.oa.service.impl;

import com.evistek.oa.dao.NoticeDao;
import com.evistek.oa.dao.ObjectNoticeDao;
import com.evistek.oa.entity.Notice;
import com.evistek.oa.service.NoticeService;
import com.evistek.oa.utils.Constant;
import com.evistek.oa.utils.MapUtil;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.SplitString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/10
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    private NoticeDao noticeDao;
    private ObjectNoticeDao objectNoticeDao;

    public NoticeServiceImpl(NoticeDao noticeDao, ObjectNoticeDao objectNoticeDao) {
        this.noticeDao = noticeDao;
        this.objectNoticeDao = objectNoticeDao;
    }

    @Override
    public Map findAllPublishNotice(int page, int pageSize) {
        return MapUtil.getMap(this.noticeDao.findAllPublicNoticeTotal(),
                this.noticeDao.findAllPublishNotice(page, pageSize));
    }

    @Override
    public Map findPublishNotice(List<String> objectIds, int page, int pageSize) {
        return MapUtil.getMap(this.noticeDao.findPublicNoticeTotal(objectIds),
                this.noticeDao.findPublishNotice(objectIds, page, pageSize));
    }

    @Override
    public Map findNotice(String userId, int page, int pageSize) {
        return MapUtil.getMap(this.noticeDao.findNoticeTotal(userId),
                this.noticeDao.findNotice(userId, page, pageSize));
    }

    @Override
    public Notice findNoticeById(String id) {
        return this.noticeDao.findNoticeById(id);
    }

    @Override
    public ResponseCode addNotice(Notice notice) {
        int result = this.noticeDao.addNotice(notice);
        if (result == 0) {
            return ResponseCode.API_ADD_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode updateNoticeById(Notice notice) {
        int result = this.noticeDao.updateNoticeById(notice);
        if (result == 0) {
            return ResponseCode.API_UPDATE_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    @Transactional
    public ResponseCode deleteNoticeById(String id) {
        int result = this.noticeDao.deleteNoticeById(id);
        if (result == 0) {
            return ResponseCode.API_DELETE_FAILED;
        }
        if (this.objectNoticeDao.findObjectNoticeByNoticeId(id).size() != 0) {
            result = this.objectNoticeDao.deleteObjectNoticeByNoticeId(id);
            if (result == 0) {
                throw new RuntimeException("delete objectNotice failed.");
            }
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    @Transactional
    public ResponseCode updatePublishById(String id, String objectIds) {
        int result = this.noticeDao.updatePublishById(id);
        if (result == 0) {
            return ResponseCode.NOTICE_PUBLISH_FAILED;
        }
        Map map = MapUtil.getMap(0, SplitString.getList(objectIds));
        map.put("noticeId", id);
        result = this.objectNoticeDao.addObjectNotice(map);
        if (result == 0) {
            throw new RuntimeException("publish failed.");
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    @Transactional
    public ResponseCode republishNotice(Notice notice, String objectIds) {
        notice.setPublish(Constant.NOTICE_PUBLISH);
        if (updateNoticeById(notice) != ResponseCode.API_SUCCESS) {
            return ResponseCode.API_UPDATE_FAILED;
        }
        int result = this.objectNoticeDao.deleteObjectNoticeByNoticeId(notice.getId());
        if (result == 0) {
            throw new RuntimeException("delete object notice failed.");
        }
        Map map = MapUtil.getMap(0, SplitString.getList(objectIds));
        map.put("noticeId", notice.getId());
        result = this.objectNoticeDao.addObjectNotice(map);
        if (result == 0) {
            throw new RuntimeException("add object notice failed.");
        }
        return ResponseCode.API_SUCCESS;
    }
}
