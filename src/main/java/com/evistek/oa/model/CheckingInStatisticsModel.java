package com.evistek.oa.model;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/1
 */
public class CheckingInStatisticsModel {
    private int leave;
    private int late;
    private int seriousLate;
    private int leaveEarly;
    private int neglectWork;
    private int cardMissing;

    public int getLeave() {
        return leave;
    }

    public void setLeave(int leave) {
        this.leave = leave;
    }

    public int getLate() {
        return late;
    }

    public void setLate(int late) {
        this.late = late;
    }
    public int getSeriousLate() {
        return seriousLate;
    }

    public void setSeriousLate(int seriousLate) {
        this.seriousLate = seriousLate;
    }
    public int getLeaveEarly() {
        return leaveEarly;
    }

    public void setLeaveEarly(int leaveEarly) {
        this.leaveEarly = leaveEarly;
    }

    public int getNeglectWork() {
        return neglectWork;
    }

    public void setNeglectWork(int neglectWork) {
        this.neglectWork = neglectWork;
    }

    public int getCardMissing() {
        return cardMissing;
    }

    public void setCardMissing(int cardMissing) {
        this.cardMissing = cardMissing;
    }

    public List<CheckingInListDateBase> getCheckingInListDateBases() {
        return checkingInListDateBases;
    }

    public void setCheckingInListDateBases(List<CheckingInListDateBase> checkingInListDateBases) {
        this.checkingInListDateBases = checkingInListDateBases;
    }

    private List<CheckingInListDateBase> checkingInListDateBases;
}
