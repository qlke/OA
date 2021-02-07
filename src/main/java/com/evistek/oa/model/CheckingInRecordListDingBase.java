package com.evistek.oa.model;

import com.evistek.oa.entity.CheckingInRecordList;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/20
 */
public class CheckingInRecordListDingBase {
    private int errcode;

    private List<CheckingInRecordList> recordresult;
    private String _record;
    private boolean hasMore;
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public List<CheckingInRecordList> getRecordresult() {
        return recordresult;
    }

    public void setRecordresult(List<CheckingInRecordList> recordresult) {
        this.recordresult = recordresult;
    }

    public String get_record() {
        return _record;
    }

    public void set_record(String _record) {
        this._record = _record;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }


}
