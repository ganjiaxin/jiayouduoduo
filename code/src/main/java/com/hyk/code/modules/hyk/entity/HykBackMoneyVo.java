package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: 霍中曦
 * @Date: 2018/12/10 16:20
 * @Description:
 */
public class HykBackMoneyVo extends DataEntity<HykBackMoneyVo>{

    /**用户ID*/
    private String userId;
    /**手机号码*/
    private String phone;
    /**用户名称*/
    private String realName;

    /**截止上月交易笔数*/
    Integer totalOrderNum;
    /**截止上月累计充值金额*/
    BigDecimal totalOrderAmt;
    /**截止上月历史交易返现*/
    BigDecimal totalBackAmt;

    /**上月邀请用户充值金额*/
    BigDecimal upMonthInviterAmt;
    /**上月邀请用户交易返现金额*/
    BigDecimal upMonthBackAmt;

    /**本月充值金额*/
    BigDecimal currentMonthAmt;
    /**上月交易返现金额*/
    BigDecimal currentMonthBackAmt;

    /**所以月份*/
    private String months;
    /**每个月份的加油笔数*/
    private String monthOrderNum;
    /**每个月份交易金额*/
    private String monthPayMoney;
    /**每个月份交易返现金额*/
    private String monthBackMoney;

    private String other;//参数字段

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getMonthOrderNum() {
        return monthOrderNum;
    }

    public void setMonthOrderNum(String monthOrderNum) {
        this.monthOrderNum = monthOrderNum;
    }

    public String getMonthPayMoney() {
        return monthPayMoney;
    }

    public void setMonthPayMoney(String monthPayMoney) {
        this.monthPayMoney = monthPayMoney;
    }

    public String getMonthBackMoney() {
        return monthBackMoney;
    }

    public void setMonthBackMoney(String monthBackMoney) {
        this.monthBackMoney = monthBackMoney;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getTotalOrderNum() {
        return totalOrderNum;
    }

    public void setTotalOrderNum(Integer totalOrderNum) {
        this.totalOrderNum = totalOrderNum;
    }

    public BigDecimal getTotalOrderAmt() {
        return totalOrderAmt;
    }

    public void setTotalOrderAmt(BigDecimal totalOrderAmt) {
        this.totalOrderAmt = totalOrderAmt;
    }

    public BigDecimal getTotalBackAmt() {
        return totalBackAmt;
    }

    public void setTotalBackAmt(BigDecimal totalBackAmt) {
        this.totalBackAmt = totalBackAmt;
    }

    public BigDecimal getUpMonthInviterAmt() {
        return upMonthInviterAmt;
    }

    public void setUpMonthInviterAmt(BigDecimal upMonthInviterAmt) {
        this.upMonthInviterAmt = upMonthInviterAmt;
    }

    public BigDecimal getUpMonthBackAmt() {
        return upMonthBackAmt;
    }

    public void setUpMonthBackAmt(BigDecimal upMonthBackAmt) {
        this.upMonthBackAmt = upMonthBackAmt;
    }

    public BigDecimal getCurrentMonthAmt() {
        return currentMonthAmt;
    }

    public void setCurrentMonthAmt(BigDecimal currentMonthAmt) {
        this.currentMonthAmt = currentMonthAmt;
    }

    public BigDecimal getCurrentMonthBackAmt() {
        return currentMonthBackAmt;
    }

    public void setCurrentMonthBackAmt(BigDecimal currentMonthBackAmt) {
        this.currentMonthBackAmt = currentMonthBackAmt;
    }
}
