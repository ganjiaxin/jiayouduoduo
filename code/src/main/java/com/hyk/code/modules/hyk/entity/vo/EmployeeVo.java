package com.hyk.code.modules.hyk.entity.vo;

import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;

/**
 * @Auther: 霍中曦
 * @Date: 2019/1/30 11:16
 * @Description:
 */
@ApiModel(value = "员工反馈详细")
public class EmployeeVo {

    /*返还月份*/
    private String month;
    /*返还金额*/
    private BigDecimal backMoney;
    /*邀请人数*/
    private String inviterNum;
    /*员工姓名*/
    private String inviterName;

    private String companyId;

    private String realName;

    private String phone;

    private String id;

    private BigDecimal amt;

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    private String registerDateStr;


    public String getRegisterDateStr() {
        return registerDateStr;
    }

    public void setRegisterDateStr(String registerDateStr) {
        this.registerDateStr = registerDateStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(BigDecimal backMoney) {
        this.backMoney = backMoney;
    }

    public String getInviterNum() {
        return inviterNum;
    }

    public void setInviterNum(String inviterNum) {
        this.inviterNum = inviterNum;
    }

    public String getInviterName() {
        return inviterName;
    }

    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }
}
