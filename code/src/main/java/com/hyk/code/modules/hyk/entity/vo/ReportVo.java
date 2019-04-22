package com.hyk.code.modules.hyk.entity.vo;

import com.hyk.code.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * @Auther: 霍中曦
 * @Date: 2019/2/20 09:35
 * @Description:
 */
public class ReportVo extends DataEntity<ReportVo> {

    /*所属公司*/
    private String companyId;
    /*邀请人数*/
    private String inviterNum;
    /*返现金额*/
    private BigDecimal backMoney;
    /*充值金额*/
    private BigDecimal money;
    /*交易笔数*/
    private String orderNum;
    /*员工id*/
    private String employeeId;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getInviterNum() {
        return inviterNum;
    }

    public void setInviterNum(String inviterNum) {
        this.inviterNum = inviterNum;
    }

    public BigDecimal getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(BigDecimal backMoney) {
        this.backMoney = backMoney;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
