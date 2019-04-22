package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;
import com.hyk.code.common.utils.excel.annotation.ExcelField;

import java.math.BigDecimal;

/**
 * @Auther: 霍中曦
 * @Date: 2018/12/17 14:07
 * @Description:
 */
public class HykInviter extends DataEntity<HykInviter> {

    private static final long serialVersionUID = 1L;

    private  String  orderNo;//订单编号
    private  String inviterPhone;//邀请人编号
    private  String  userPhone;//注册编号
    private  String  cardNo;//加油卡
    private  String orderType;//订单类型
    private  String  payType;//支付类型
    private  String  amt;//订单总金额
    private  String  goodsId;//商品id
    private BigDecimal redpackageAmt;//红包金额

    private  String  payDate;//付款日期
    private  String  orderStatus;//订单状态
    private  String  remark;//备注
    private  String  cardId;//加油卡id
    private  String  goodsName;//商品名称
    private  String  cycle;//期数
    private  BigDecimal  payableMoney;//实际付款金额
    private BigDecimal backMoney;//返现金额

    private String payDateStr;
    private String createDateStr;

    @ExcelField(title="返现金额", align=2, sort=110)
    public BigDecimal getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(BigDecimal backMoney) {
        this.backMoney = backMoney;
    }

    @ExcelField(title="订单编号", align=2, sort=10)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    @ExcelField(title="邀请手机号", align=2, sort=30)
    public String getInviterPhone() {
        return inviterPhone;
    }

    public void setInviterPhone(String inviterPhone) {
        this.inviterPhone = inviterPhone;
    }
    @ExcelField(title="注册手机号", align=2, sort=20)
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    @ExcelField(title="充值卡号", align=2, sort=40)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    @ExcelField(title="交易类型", align=2, sort=50)
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    @ExcelField(title="商品名称", align=2, sort=70)
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }
    @ExcelField(title="付款时间", align=2, sort=100)
    public String getPayDateStr() {
        return payDateStr;
    }

    public void setPayDateStr(String payDateStr) {
        this.payDateStr = payDateStr;
    }
    @ExcelField(title="订单生成时间", align=2, sort=90)
    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }
    @ExcelField(title="红包金额", align=2, sort=80)
    public BigDecimal getRedpackageAmt() {
        return redpackageAmt;
    }

    public void setRedpackageAmt(BigDecimal redpackageAmt) {
        this.redpackageAmt = redpackageAmt;
    }
    @ExcelField(title="订单金额", align=2, sort=60)
    public BigDecimal getPayableMoney() {
        return payableMoney;
    }

    public void setPayableMoney(BigDecimal payableMoney) {
        this.payableMoney = payableMoney;
    }
}
