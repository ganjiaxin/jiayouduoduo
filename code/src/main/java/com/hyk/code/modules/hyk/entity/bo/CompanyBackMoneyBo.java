package com.hyk.code.modules.hyk.entity.bo;

import java.math.BigDecimal;

/**
 * @Auther: 霍中曦
 * @Date: 2019/1/23 10:47
 * @Description:
 */
public class CompanyBackMoneyBo {

    /*所属公司ID*/
    private String companyId;
    /*公司上月返现金额*/
    private BigDecimal backMoney;
    /*返现月份*/
    private String lastMonth;
    /*邀请人数*/
    private String inviterNum;


    public String getInviterNum() {
        return inviterNum;
    }

    public void setInviterNum(String inviterNum) {
        this.inviterNum = inviterNum;
    }

    public String getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(String lastMonth) {
        this.lastMonth = lastMonth;
    }

    public BigDecimal getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(BigDecimal backMoney) {
        this.backMoney = backMoney;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "CompanyBackMoneyBo{" +
                "companyId='" + companyId + '\'' +
                ", backMoney=" + backMoney +
                '}';
    }
}
