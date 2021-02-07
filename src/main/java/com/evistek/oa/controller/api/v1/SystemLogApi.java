package com.evistek.oa.controller.api.v1;

import com.evistek.oa.service.SystemLogService;
import com.evistek.oa.utils.MapUtil;
import com.evistek.oa.utils.ResponseBase;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.ResponseData;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/10
 */
@RestController
@RequestMapping("/api/v1")
public class SystemLogApi {
    private SystemLogService systemLogService;

    public SystemLogApi(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    /**
     * 查找系统日志
     *
     * @param user      操作用户
     * @param type      操作类型（添加、删除等）
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param page      页脚
     * @param pageSize  每页数量
     * @param token     用户token
     * @return 响应日志列表
     */
    @RequestMapping(value = "/findSystemLog", method = RequestMethod.GET)
    public ResponseBase findSystemLog(
            @RequestParam(value = "user", required = false) String user,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "startTime", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @RequestParam(value = "endTime", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
            @RequestParam(value = "token", required = true) String token
    ) {
        return ResponseData.build(ResponseCode.API_SUCCESS, MapUtil.getMap(
                this.systemLogService.findSystemLogTotal(type, user, startTime, endTime),
                this.systemLogService.findSystemLog(type, user, startTime, endTime, page, pageSize)));
    }
}
