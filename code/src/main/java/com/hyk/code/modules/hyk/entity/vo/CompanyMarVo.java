package com.hyk.code.modules.hyk.entity.vo;

import com.hyk.code.common.persistence.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: 霍中曦
 * @Date: 2019/2/20 16:06
 * @Description:
 */
@ApiModel(value = "商户管理")
public class CompanyMarVo extends DataEntity<CompanyMarVo> {
    /*所属公司id*/
    @ApiModelProperty(required = false, value = "所属公司id")
    private String companyId;
    /*所属公司名*/
    @ApiModelProperty(required = false, value = "所属公司名")
    private String companyName;
    /*商户主id*/
    @ApiModelProperty(required = false, value = "商户主id")
    private String userId;
    /*商户主手机号*/
    @ApiModelProperty(required = false, value = "商户主手机号")
    private String phone;
    /*员工数量*/
    @ApiModelProperty(required = false, value = "员工数量")
    private String employee;
    /*累计充值金额*/
    @ApiModelProperty(required = false, value = "累计提现金额")
    private String totalMoney;
    /*账户余额*/
    @ApiModelProperty(required = false, value = "账户余额")
    private String useMoney;
    /*所有公司截止上月交易笔数*/
    @ApiModelProperty(required = false, value = "所有公司截止上月交易笔数")
    private String orderNum;
    /*所有公司截止上月累计充值金额*/
    @ApiModelProperty(required = false, value = "所有公司截止上月累计充值金额")
    private String orderAmt;
    /*所有公司截止上月历史交易返现*/
    @ApiModelProperty(required = false, value = "所有公司截止上月历史交易返现")
    private String totalBackMoney;
    /*所有公司邀请用户上月充值金额*/
    @ApiModelProperty(required = false, value = "所有公司邀请用户上月充值金额")
    private String lastMonthOrderAmt;
    /*所有公司邀请用户上月交易返现*/
    @ApiModelProperty(required = false, value = "所有公司邀请用户上月交易返现")
    private String lastMonthBackMoney;
    /*所有公司邀请用户当月充值金额*/
    @ApiModelProperty(required = false, value = "所有公司邀请用户当月充值金额")
    private String currentMonthOrderAmt;
    /*所有公司邀请用户当月返现金额*/
    @ApiModelProperty(required = false, value = "所有公司邀请用户当月返现金额")
    private String currentMonthBackMoney;
    /*所有公司邀请用户数量*/
    @ApiModelProperty(required = false, value = "所有公司邀请用户数量")
    private String totalInviterNum;

    /*员工邀请码*/
    @ApiModelProperty(required = false, value = "员工邀请码")
    private String inviterCode;
    /*员工类型*/
    @ApiModelProperty(required = false, value = "员工类型")
    private String isBoss;

    private String other;

    public String getInviterCode() {
        return inviterCode;
    }

    public void setInviterCode(String inviterCode) {
        this.inviterCode = inviterCode;
    }

    public String getIsBoss() {
        return isBoss;
    }

    public void setIsBoss(String isBoss) {
        this.isBoss = isBoss;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCurrentMonthOrderAmt() {
        return currentMonthOrderAmt;
    }

    public void setCurrentMonthOrderAmt(String currentMonthOrderAmt) {
        this.currentMonthOrderAmt = currentMonthOrderAmt;
    }

    public String getCurrentMonthBackMoney() {
        return currentMonthBackMoney;
    }

    public void setCurrentMonthBackMoney(String currentMonthBackMoney) {
        this.currentMonthBackMoney = currentMonthBackMoney;
    }

    public String getTotalInviterNum() {
        return totalInviterNum;
    }

    public void setTotalInviterNum(String totalInviterNum) {
        this.totalInviterNum = totalInviterNum;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(String useMoney) {
        this.useMoney = useMoney;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getTotalBackMoney() {
        return totalBackMoney;
    }

    public void setTotalBackMoney(String totalBackMoney) {
        this.totalBackMoney = totalBackMoney;
    }

    public String getLastMonthOrderAmt() {
        return lastMonthOrderAmt;
    }

    public void setLastMonthOrderAmt(String lastMonthOrderAmt) {
        this.lastMonthOrderAmt = lastMonthOrderAmt;
    }

    public String getLastMonthBackMoney() {
        return lastMonthBackMoney;
    }

    public void setLastMonthBackMoney(String lastMonthBackMoney) {
        this.lastMonthBackMoney = lastMonthBackMoney;
    }
}
