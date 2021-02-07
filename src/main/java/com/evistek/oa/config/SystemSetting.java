package com.evistek.oa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/24
 */
@Component
@PropertySource("classpath:settings.properties")
@ConfigurationProperties(prefix = "sys.setting")
public class SystemSetting {
    /**
     * 保留日志天数
     */
    private String retainLog;
    /**
     * 销售
     */
    private String sale;
    /**
     * 仓管
     */
    private String pmc;
    /**
     * 工程师
     */
    private String engineer;
    /**
     * 财务
     */
    private String finance;

    public String getRetainLog() {
        return retainLog;
    }

    public void setRetainLog(String retainLog) {
        this.retainLog = retainLog;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getPmc() {
        return pmc;
    }

    public void setPmc(String pmc) {
        this.pmc = pmc;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getFinance() {
        return finance;
    }

    public void setFinance(String finance) {
        this.finance = finance;
    }
}
