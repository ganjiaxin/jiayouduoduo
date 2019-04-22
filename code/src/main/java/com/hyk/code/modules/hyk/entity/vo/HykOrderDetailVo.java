package com.hyk.code.modules.hyk.entity.vo;

import com.hyk.code.common.persistence.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: 霍中曦
 * @Date: 2019/2/13 11:54
 * @Description:
 */
@ApiModel(value = "奖励明细")
public class HykOrderDetailVo extends DataEntity<HykOrderDetailVo> {

    @ApiModelProperty(required = false, value = "订单编号")
    private String  orderNo;
    @ApiModelProperty(required = false, value = "注册手机号码")
    private String  phone;
    @ApiModelProperty(required = false, value = "邀请人号码")
    private String  inviterPhone;
    @ApiModelProperty(required = false, value = "充值卡号")
    private String  cardNo;
    @ApiModelProperty(required = false, value = "交易类型")
    private String  orderTypeStr;
    @ApiModelProperty(required = false, value = "订单金额")
    private String  amt;
    @ApiModelProperty(required = false, value = "商品名称")
    private String  goodsName;
    @ApiModelProperty(required = false, value = "红包")
    private String  redpackageAmt;
    @ApiModelProperty(required = false, value = "订单生成时间")
    private String  createDateStr;
    @ApiModelProperty(required = false, value = "付款时间")
    private String  payDateStr;
    @ApiModelProperty(required = false, value = "返现金额")
    private String  backMoney;

    private String other;

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInviterPhone() {
        return inviterPhone;
    }

    public void setInviterPhone(String inviterPhone) {
        this.inviterPhone = inviterPhone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOrderTypeStr() {
        return orderTypeStr;
    }

    public void setOrderTypeStr(String orderTypeStr) {
        this.orderTypeStr = orderTypeStr;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getRedpackageAmt() {
        return redpackageAmt;
    }

    public void setRedpackageAmt(String redpackageAmt) {
        this.redpackageAmt = redpackageAmt;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getPayDateStr() {
        return payDateStr;
    }

    public void setPayDateStr(String payDateStr) {
        this.payDateStr = payDateStr;
    }

    public String getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(String backMoney) {
        this.backMoney = backMoney;
    }
}
