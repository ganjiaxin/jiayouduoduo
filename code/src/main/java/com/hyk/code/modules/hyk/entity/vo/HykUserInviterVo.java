package com.hyk.code.modules.hyk.entity.vo;

import io.swagger.annotations.ApiModel;

/**
 * @Auther: 霍中曦
 * @Date: 2019/1/29 11:18
 * @Description:
 */
@ApiModel(value = "订单反馈金额详细")
public class HykUserInviterVo {

    /*邀请用户id*/
    private String id;
    /*邀请用户手机号*/
    private String phone;
    /*邀请员工*/
    private String realName;
    /*邀请时间*/
    private String registerDateStr;

    /*所属公司ID*/
    private String companyId;
    /*返现月份*/
    private String lastMonth;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(String lastMonth) {
        this.lastMonth = lastMonth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRegisterDateStr() {
        return registerDateStr;
    }

    public void setRegisterDateStr(String registerDateStr) {
        this.registerDateStr = registerDateStr;
    }
}



