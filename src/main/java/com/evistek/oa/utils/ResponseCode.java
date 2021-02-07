package com.evistek.oa.utils;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/4
 */
public enum ResponseCode {
    //公共模块
    API_SUCCESS(200, "operation success"),
    API_EMAIL_OR_PWD_ERROR(1000, "email or password error."),
    API_TOKEN_EXPIRED_OR_ERROR(1001, "token error or expired."),
    API_TOKEN_IS_NULL(1002, "token is null."),
    API_NOT_PERMISSION(1003, "not permission."),
    API_PASSWORD_ERROR(1004, "old password error."),
    API_ADD_FAILED(1005, "add failed."),
    API_UPDATE_FAILED(1006, "update failed."),
    API_DELETE_FAILED(1007, "delete failed."),
    //部门管理
    DEPARTMENT_ALREADY_EXIST(2000, "department already exist."),
    DEPARTMENT_NOT_DELETE_REF(2001, "be referenced department can't delete."),
    DEPARTMENT_NOT_IS_ONESELF(2002, "father department cant't is oneself."),
    //职位管理
    POSITION_ALREADY_EXIST(2010, "position already exist."),
    POSITION_NOT_DELETE_REF(2011, "be referenced position can't delete."),
    //岗位管理
    POST_ALREADY_EXIST(2020, "post already exist."),
    //员工管理
    EMPLOYEE_USERNAME_ALREADY_EXIST(2030, "login name already exist."),
    EMPLOYEE_EMAIL_ALREADY_EXIST(2031, "email already exist."),
    EMPLOYEE_PHONE_ALREADY_EXIST(2032, "phone already exist."),
    EMPLOYEE_EXIST_UN_RETURNED_ASSET(2033, "exist unReturned asset."),
    EMPLOYEE_NOT_DELETE_ONESELF(2034, "can't delete oneself."),
    //考勤管理
    API_CHECKING_IN_FAILED(3000, "checkingIn failed"),
    API_CHECKING_IN_DATE_FORMAT_ERROR(3001, "date format error exp:1970-01-01 00:00:00"),
    //资产管理
    ASSET_NUMBER_ALREADY_EXIST(4000, "number already exist."),
    ASSET_MODEL_ALREADY_EXIST(4001, "model already exist."),
    ASSET_SERIAL_NUMBER_ALREADY_EXIST(4002, "serialNumber already exist."),
    ASSET_NOT_DELETE_REF(4003, "be referenced asset can't delete."),
    ASSET_ALLOT_FAILED(4004, "allot asset failed."),
    ASSET_RETURN_FAILED(4005, "return asset failed."),
    ASSET_IN_USE(4006, "asset in use can't allot."),
    //资产类型管理
    ASSET_TYPE_NOT_DELETE_REF(4010, "be referenced asset type can't delete."),
    //申请模块
    APPLY_NO_TASK(5000, "task not exist."),
    APPLY_FAILED(5001, "apply failed."),
    APPLY_FATHER_LEADER_IS_NULL(5002, "father leader is null."),
    APPLY_UNDISSOLVED_NOT_CAN_DELETE(5003, "undissolved apply can't delete."),
    APPLY_NO_PROCESS(5004, "process not exist."),
    APPLY_APPROVE_FAILED(5005, "approve failed."),
    //公告管理
    NOTICE_PUBLISH_FAILED(6001, "publish failed."),
    //缺陷记录管理
    API_FEEDBACK_FAILED(7000, "checkingIn failed"),
    //设备返修模块
    REPAIR_CAN_NOT_DELETE(8000, "can't delete repair in a process."),
    REPAIR_FORM_NO_EXIST(8001, "form not exist."),
    //组织机构管理
    ORGANIZATION_ALREADY_EXIST(9010, "organization already exist."),
    ORGANIZATION_NOT_DELETE_REF(9011, "be referenced organization can't delete."),
    ORGANIZATION_FATHER_NOT_FOUND(9012, "parent organization not found."),
    ORGANIZATION_NOT_FOUND(9013, "organization no exist."),
    ORGANIZATION_DEFAULT_NOT_DELETE(9014, "default organization can't delete."),
    //系统参数设置
    SETTING_NOT_FIND_KEY(9020, "not find setting key."),
    //设备管理
    EQUIPMENT_ERROR(10001, "equipment error"),
    EQUIPMENT_TYPE_DELETE_ERROR(10002, "equipment type delete error"),
    EQUIPMENT_TYPE_NOT_EXIST(10003, "equipment type not exist"),
    EQUIPMENT_TYPE_ERROR(10004, "equipment type error"),
    EQUIPMENT_MODEL_EXIST(10005, "equipment model exist"),
    EQUIPMENT_MODEL_ERROR(10006, "equipment model error"),
    EQUIPMENT_MODEL_ATTRIBUTE_NOT_FOUND(10007, "equipment model attribute not found"),
    EQUIPMENT_MODEL_TYPE_NOT_FOUND(10008, "equipment model type not found"),
    EQUIPMENT_MODEL_ATTRIBUTE_ERROR(10009, "equipment model attribute error");
    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
