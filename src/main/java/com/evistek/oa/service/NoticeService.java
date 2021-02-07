package com.evistek.oa.service;

import com.evistek.oa.entity.Notice;
import com.evistek.oa.utils.ResponseCode;

import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/10
 */
public interface NoticeService {

    Map findAllPublishNotice(int page, int pageSize);

    Map findPublishNotice(List<String> objectIds, int page, int pageSize);

    Map findNotice(String userId, int page, int pageSize);

    Notice findNoticeById(String id);

    ResponseCode addNotice(Notice notice);

    ResponseCode updateNoticeById(Notice notice);

    ResponseCode deleteNoticeById(String id);

    ResponseCode updatePublishById(String id, String objectIds);

    ResponseCode republishNotice(Notice notice, String objectIds);
}
