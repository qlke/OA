package com.evistek.oa.model;

import com.evistek.oa.entity.CheckingInList;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/11/18
 */
public class CheckingInListDingBase {
    private int errcode;
    private List<CheckingInList> recordresult;
    private String _record;
    private boolean hasMore;
    private String errmsg;

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setRecordresult(List<CheckingInList> recordresult) {
        this.recordresult = recordresult;
    }

    public List<CheckingInList> getRecordresult() {
        return recordresult;
    }

    public void set_record(String _record) {
        this._record = _record;
    }

    public String get_record() {
        return _record;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public boolean getHasMore() {
        return hasMore;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getErrmsg() {
        return errmsg;
    }
}
