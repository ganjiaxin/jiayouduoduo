/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyk.code.common.persistence.DataEntity;
import com.hyk.code.modules.sys.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值卡兑换记录Entity
 *
 * @author 霍中曦
 * @version 2018-12-18
 */
@ApiModel(value = "充值卡兑换记录",description = "充值卡兑换记录")
public class HykCardHis extends DataEntity<HykCardHis> {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(required=false,value="油卡ID")
    private String cardId;        // 油卡ID
    @ApiModelProperty(required=false,value="油卡编号")
    private String cardno;        // 油卡编号
    @ApiModelProperty(required=false,value="油卡金额")
    private BigDecimal money;        // 油卡金额
    @ApiModelProperty(required=false,value="兑换时间")
    private Date useDate;        // 兑换时间
    @ApiModelProperty(required=false,value="用户余额")
    private String accumulativeRechargeAmount;//余额
    @ApiModelProperty(required=false,value="用户累计兑换总额")
    private String accountBalance;//累计总额
    @ApiModelProperty(required=false,value="兑换数量")
    private String total;//兑换数量
    //--------------------------------------------------------------------------------
    @ApiModelProperty(required=false,value="兑换日期Str年月日")
    private String useDateStr; //兑换日期Str 年.月.日
    private String timeStr; //兑换时间 16：22
    @ApiModelProperty(required=false,value="用户id")
    private String userId;
    @ApiModelProperty(required=false,value="用户注册手机号")
    private String phone;//用户注册手机号
    @ApiModelProperty(required=false,value="卡的类型")
    private String useMethodStr;//卡的类型


    @ApiModelProperty(required=false,value="开始时间")
    private Date startDate;//开始时间
    @ApiModelProperty(required=false,value="结束时间")
    private Date endDate;//结束时间
    @ApiModelProperty(required=false,value="条件参数")
    private String other;//条件参数

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public String getUseMethodStr() {
        return useMethodStr;
    }

    public void setUseMethodStr(String useMethodStr) {
        this.useMethodStr = useMethodStr;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAccumulativeRechargeAmount() {
        return accumulativeRechargeAmount;
    }

    public void setAccumulativeRechargeAmount(String accumulativeRechargeAmount) {
        this.accumulativeRechargeAmount = accumulativeRechargeAmount;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public HykCardHis() {
        super();
    }

    public HykCardHis(String id) {
        super(id);
    }


    @Length(min = 0, max = 64, message = "油卡ID长度必须介于 0 和 64 之间")
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Length(min = 0, max = 64, message = "油卡编号长度必须介于 0 和 64 之间")
    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public String getUseDateStr() {
        return useDateStr;
    }

    public void setUseDateStr(String useDateStr) {
        this.useDateStr = useDateStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}