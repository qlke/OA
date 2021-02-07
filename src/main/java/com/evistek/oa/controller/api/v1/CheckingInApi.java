package com.evistek.oa.controller.api.v1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptException;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import com.evistek.oa.annotation.Operation;
import com.evistek.oa.entity.CheckingInUser;
import com.evistek.oa.model.CheckingInListDateBase;
import com.evistek.oa.model.CheckingInRecordListBase;
import com.evistek.oa.model.CheckingInStatisticsModel;
import com.evistek.oa.model.CheckingInUserBase;
import com.evistek.oa.service.CheckingInService;
import com.evistek.oa.service.DingCheckingInSyncService;
import com.evistek.oa.service.DingService;
import com.evistek.oa.utils.*;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/16
 */
@RestController
@RequestMapping("/api/v1")
public class CheckingInApi {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private DingService dingService;
    private CheckingInService checkingInService;
    private DingCheckingInSyncService dingCheckingInSyncService;

    public CheckingInApi(DingService dingService, CheckingInService checkingInService, DingCheckingInSyncService dingCheckingInSyncService) {
        this.dingService = dingService;
        this.checkingInService = checkingInService;
        this.dingCheckingInSyncService = dingCheckingInSyncService;
    }

    /**
     * 添加员工信息
     *
     * @param checkingInUser 员工信息
     * @param token          用户token
     * @return 响应操作信息
     */
    @Operation(description = "add checkingIn user")
    @RequestMapping(value = "/addCheckingInUser", method = RequestMethod.POST)
    public ResponseBase addDDUser(
            @RequestBody CheckingInUser checkingInUser,
            @RequestParam(value = "token") String token,
            HttpServletRequest request
    ) {
        try {
            CheckingInUserBase checkingInUserBase = new CheckingInUserBase();
            checkingInUserBase.setPhone(checkingInUser.getPhone());
            CheckingInUser checkingInUserHis = checkingInService.selectCheckingInUser(checkingInUserBase);
            if (null != checkingInUserHis) {
                checkingInUser.setDdId(checkingInUserHis.getDdId());
                int result = checkingInService.updateCheckingInUser(checkingInUser);
                if (result <= 0) {
                    return ResponseBase.build(ResponseCode.API_CHECKING_IN_FAILED);
                }
                return ResponseBase.build(ResponseCode.API_SUCCESS);
            }
            OapiUserGetByMobileResponse rsp = checkDingUser(checkingInUser.getPhone(), 0);
            if (null == rsp) {
                return ResponseData.build(ResponseCode.API_CHECKING_IN_FAILED, "添加出错");
            }
            if (rsp.getErrcode() == 0) {
                checkingInUser.setDdId(rsp.getUserid());
            } else {
                return ResponseData.build(ResponseCode.API_CHECKING_IN_FAILED, "添加失败:" + rsp.getErrmsg());
            }
            int result = checkingInService.addCheckingInUser(checkingInUser);
            if (result <= 0) {
                return ResponseData.build(ResponseCode.API_CHECKING_IN_FAILED, "添加失败");
            } else {
                dingCheckingInSyncService.syncAddUser(checkingInUser);
            }
        } catch (Exception e) {
            return ResponseData.build(ResponseCode.API_CHECKING_IN_FAILED, "添加出错");
        }
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }

    /**
     * 编辑员工考勤信息
     *
     * @param checkingInUser 员工信息
     * @param token          用户token
     * @return 响应操作信息
     */
    @RequestMapping(value = "updateCheckingInUser", method = RequestMethod.PUT)
    public ResponseBase updateCheckingInUser(
            @RequestBody CheckingInUser checkingInUser,
            @RequestParam(value = "token") String token
    ) {
        CheckingInUser checkingInUsers = new CheckingInUser();
        checkingInUsers.setPhone(checkingInUser.getPhone());
        checkingInUsers.setName(checkingInUser.getName());
        int result = checkingInService.updateCheckingInUser(checkingInUsers);
        if (result <= 0) {
            return ResponseBase.build(ResponseCode.API_CHECKING_IN_FAILED);
        }
        return ResponseBase.build(ResponseCode.API_SUCCESS);
    }

    /**
     * 查找员工考勤
     *
     * @param phone 员工手机号码
     * @param token 用户token
     * @return 响应员工考勤信息
     */
    @Operation(description = "select checkingIn user")
    @RequestMapping(value = "/findCheckingInUser", method = RequestMethod.GET)
    public ResponseBase selectCheckingInUser(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "token") String token
    ) {
        CheckingInUserBase checkingInUserBase = new CheckingInUserBase();
        checkingInUserBase.setPhone(phone);
        CheckingInUser checkingInUser = checkingInService.selectCheckingInUser(checkingInUserBase);
        return ResponseData.build(ResponseCode.API_SUCCESS, checkingInUser);
    }

    /**
     * 查找员工考勤列表
     *
     * @return 响应员工考勤列表
     */
    @RequestMapping(value = "/allCheckingInUser", method = RequestMethod.GET)
    public ResponseBase getAllCheckingInUser() {
        return ResponseData.build(ResponseCode.API_SUCCESS, checkingInService.selectCheckingInUserList());
    }


    @RequestMapping(value = "/dingCallBack", method = RequestMethod.POST)
    public Object dingCallBack(@RequestParam(value = "signature") String signature,
                               @RequestParam(value = "timestamp") Long timestamp,
                               @RequestParam(value = "nonce") String nonce,
                               @RequestBody(required = false) JSONObject body) {
        String params = "signature:" + signature + " timestamp:" + timestamp + " nonce:" + nonce + " body:" + body;
        try {
            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(Constant.DING_TOKEN, Constant.DING_ENCODING_AES_KEY, Constant.DING_CROP_ID);
            // 从post请求的body中获取回调信息的加密数据进行解密处理
            String encrypt = body.getString("encrypt");
            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp.toString(), nonce, encrypt);
            JSONObject callBackContent = JSON.parseObject(plainText);

            // 根据回调事件类型做不同的业务处理
            String eventType = callBackContent.getString("EventType");
            if (Constant.EVENT_CHECK_CREATE_SUITE_URL.equals(eventType)) {
                log.info("验证新创建的回调URL有效性: " + plainText);
            } else if (Constant.EVENT_CHECK_UPDATE_SUITE_URL.equals(eventType)) {
                log.info("验证更新回调URL有效性: " + plainText);
            } else if (Constant.EVENT_SUITE_TICKET.equals(eventType)) {
                // suite_ticket用于用签名形式生成accessToken(访问钉钉服务端的凭证)，需要保存到应用的db。
                // 钉钉会定期向本callback url推送suite_ticket新值用以提升安全性。
                // 应用在获取到新的时值时，保存db成功后，返回给钉钉success加密串（如本demo的return）
                log.info("应用suite_ticket数据推送: " + plainText);
            } else if (Constant.EVENT_TMP_AUTH_CODE.equals(eventType)) {
                // 本事件应用应该异步进行授权开通企业的初始化，目的是尽最大努力快速返回给钉钉服务端。用以提升企业管理员开通应用体验
                // 即使本接口没有收到数据或者收到事件后处理初始化失败都可以后续再用户试用应用时从前端获取到corpId并拉取授权企业信息，进而初始化开通及企业。
                log.info("企业授权开通应用事件: " + plainText);
            } else {
                // 其他类型事件处理
                log.info("其他事件: " + plainText);
            }
            return dingTalkEncryptor.getEncryptedMap("success", timestamp, nonce);
        } catch (DingTalkEncryptException e) {
            System.out.println("process callback fail." + params + "--" + e);
            return "fail";
        }
    }

    /**
     * 检测员工的手机号码是否注册企业钉钉
     *
     * @param phone 手机号码
     * @param token 用户token
     * @return 响应操作信息
     */
    @Operation(description = "check ding user")
    @RequestMapping(value = "/checkDingUser", method = RequestMethod.GET)
    public ResponseBase getDingUser(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "token") String token,
            HttpServletRequest request
    ) {

        OapiUserGetByMobileResponse rsp = checkDingUser(phone, 0);
        if (null == rsp) {
            return ResponseData.build(ResponseCode.API_CHECKING_IN_FAILED, "服务出错");
        } else {
            if (rsp.getErrcode() == 0) {
                return ResponseBase.build(ResponseCode.API_SUCCESS);
            } else {
                return ResponseData.build(ResponseCode.API_CHECKING_IN_FAILED, Constant.PHONE_CHECKING_IN_FAILED);
            }
        }

    }

    public OapiUserGetByMobileResponse checkDingUser(String phone, int type) {
        OapiUserGetByMobileResponse rspResult = null;
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
            OapiUserGetByMobileRequest req = new OapiUserGetByMobileRequest();
            req.setMobile(phone);
            req.setHttpMethod("GET");
            rspResult = client.execute(req, dingService.getToken());

        } catch (ApiException e) {
            log.error("查询出错");
        }
        return rspResult;
    }

    /**
     * 查找所有员工的考勤记录
     *
     * @param workDate 工作日（查询某一天的考勤记录）
     * @param token    用户token
     * @return 响应员工的考勤记录
     */
    @RequestMapping(value = "/allCheckingInRecord", method = RequestMethod.GET)
    public ResponseBase getAllDingUserRecord(
            @RequestParam(value = "work_date") String workDate,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
            @RequestParam(value = "token") String token
    ) {
        Long date = DateUtil.getInstance().parseDateLong(workDate);
        if (date == -1) {
            return ResponseBase.build(ResponseCode.API_CHECKING_IN_DATE_FORMAT_ERROR);
        }
        return ResponseData.build(ResponseCode.API_SUCCESS,
                MapUtil.getMap(this.checkingInService.findCheckingInUserTotal(),
                        checkingInService.selectCheckingInListDateAll(date, page, pageSize)));
    }

    /**
     * 查找员工考勤记录
     *
     * @param phone     员工手机号码
     * @param startDate 上班打卡时间
     * @param endDate   下班打卡时间
     * @param token     用户token
     * @return 响应员工考勤记录
     */
    @RequestMapping(value = "findCheckingInRecord", method = RequestMethod.GET)
    public ResponseBase getDingUserRecordPerson(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "start_date") String startDate,
            @RequestParam(value = "end_date") String endDate,
            @RequestParam(value = "token") String token
    ) {
        Date start = DateUtil.getInstance().parseDateString(startDate);
        Date end = DateUtil.getInstance().parseDateString(endDate);
        if (null == start || null == end) {
            return ResponseBase.build(ResponseCode.API_CHECKING_IN_DATE_FORMAT_ERROR);
        }
        List<CheckingInListDateBase> listDateBases = checkingInService.selectCheckingInListDatePerson(phone, start, end);
        if (null == listDateBases) {
            return ResponseBase.build(ResponseCode.API_CHECKING_IN_FAILED);
        }
        return ResponseData.build(ResponseCode.API_SUCCESS, listDateBases);
    }

    /**
     * 查找单个员工的考勤记录
     *
     * @param phone     员工的手机号码
     * @param startDate 上班打卡时间
     * @param endDate   下班打卡时间
     * @param token     用户token
     * @return 响应打卡异常列表和考勤统计
     */
    @RequestMapping(value = "findCheckingInSingular", method = RequestMethod.GET)
    public ResponseBase getDingUserRecordSingular(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "start_date") String startDate,
            @RequestParam(value = "end_date") String endDate,
            @RequestParam(value = "token") String token
    ) {
        Date start = DateUtil.getInstance().parseDateString(startDate);
        Date end = DateUtil.getInstance().parseDateString(endDate);
        if (null == start || null == end) {
            return ResponseBase.build(ResponseCode.API_CHECKING_IN_DATE_FORMAT_ERROR);
        }
        CheckingInStatisticsModel checkingInStatisticsModel = checkingInService.selectCheckingInSingular(phone, start, end);

        return ResponseData.build(ResponseCode.API_SUCCESS, checkingInStatisticsModel);
    }

    /**
     * 查找员工某天的考勤详情
     *
     * @param recordId 记录id
     * @param token    用户token
     * @return 响应考勤详情
     */
    @RequestMapping(value = "/findCheckingInDetail", method = RequestMethod.GET)
    public ResponseBase getCheckingInDetail(
            @RequestParam(value = "record_id") String recordId,
            @RequestParam(value = "token") String token
    ) {
        CheckingInRecordListBase checkingInRecordListBase = new CheckingInRecordListBase();
        checkingInRecordListBase.setId(recordId);
        return ResponseData.build(ResponseCode.API_SUCCESS, checkingInService.selectCheckingInRecordListByRecordId(checkingInRecordListBase));
    }

    /**
     * 同步员工考勤信息
     *
     * @param phone 手机号码
     * @param token 用户token
     * @return 响应同步结果
     */
    @RequestMapping(value = "/syncUserCheckingIn", method = RequestMethod.GET)
    public ResponseBase syncUserCheckingIn(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "token") String token) {
        CheckingInListDateBase result = dingCheckingInSyncService.syncTodayCheckingIn(phone);
        return ResponseData.build(ResponseCode.API_SUCCESS, result);
    }

}
