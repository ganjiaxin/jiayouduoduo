package com.hyk.code.modules.hyk.entity.vo;

import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;

/**
 * @Auther: 霍中曦
 * @Date: 2019/1/28 18:00
 * @Description:订单反馈金额详细
 */
@ApiModel(value = "订单反馈金额详细")
public class HykOrderVo {

    /*订单返现金额*/
    private BigDecimal backMoney;
    /*订单金额*/
    private BigDecimal money;
    /*订单下单用户*/
    private String phone;
    /*订单套餐*/
    private String goodsName;
    /*订单支付时间*/
    private String payDateStr;
    /*邀请员工姓名*/
    private String inviterName;

    /*员工姓名*/
    private String realName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPayDateStr() {
        return payDateStr;
    }

    public void setPayDateStr(String payDateStr) {
        this.payDateStr = payDateStr;
    }

    public String getInviterName() {
        return inviterName;
    }

    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }

    @Override
    public String toString() {
        return "HykOrderVo{" +
                "backMoney=" + backMoney +
                ", money=" + money +
                ", phone='" + phone + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", payDateStr='" + payDateStr + '\'' +
                ", inviterName='" + inviterName + '\'' +
                '}';
    }
}
