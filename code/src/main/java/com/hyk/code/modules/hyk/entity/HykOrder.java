package com.hyk.code.modules.hyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyk.code.common.persistence.DataEntity;
import com.hyk.code.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单管理Entity
 *
 * @author 霍中曦
 * @version 2018-11-09
 */
public class HykOrder extends DataEntity<HykOrder> {

    private static final long serialVersionUID = 1L;
    private String orderNo;        // 订单编号
    private HykUser hykUser;        // 用户编号
    private String userPhone;        // 用户手机号
    private String cardNo;        // 充值卡号
    private String orderType;        // 交易类型 0即时充值 1套餐充值
    private String payType;        // 付款方式  1微信 2支付宝 3快捷 4银联 6余额支付 字典 payType
    private BigDecimal everyAmt;        // 每期金额
    private BigDecimal amt;        // 订单交易总金额
    private String goodsId;        // 商品编号
    private String redpackageId;        // 红包编号
    private BigDecimal redpackageAmt;        // 红包金额
    private Date payDate;        // 付款时间
    private String oilStatus;       //0代充值/加油计划执行中  1已完成
    private String orderStatus;        // 订单状态 0待充值 1充值中 2已充值 3充值失败 4已失效 5 暂停 6 退款
    private String remark;        // 备注
    private String f1;        // f1
    private BigDecimal payableMoney;        // 应付金额
    private String cardId;        // 加油卡id
    private String userId;
    private Date createDate;//创建日期
    //===================================逻辑字段
    private Date startTime;//开始时间
    private Date endTime;//结束时间
    private String other;//手机号 订单号 用户id
    private String payDateStr;//格式化日期
    private String createDateStr;//格式化日期
    private String goodsName; //订单名称mxf
    private String goodsType; //订单类型mxf
    private String cycel;//商品周期
    private String discount;//商品折扣
    private String activityDiscount;//商品折扣
    private String prices;//商品价格
    private BigDecimal backMoney;//返现金额
    private String cycle;//周期
    private BigDecimal balancePayment;//余额支付金额
    private String orderTypeStr;
    private String payTypeStr;
    private String orderStatusStr;
    private List<HykOrder> months;
    private String month;
    //用户渠道
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<HykOrder> getMonths() {
        return months;
    }

    public void setMonths(List<HykOrder> months) {
        this.months = months;
    }

    @ExcelField(title = "订单类型", align = 2, sort = 100)
    public String getOrderStatusStr() {
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }

    @ExcelField(title = "付款方式", align = 2, sort = 40)
    public String getPayTypeStr() {
        return payTypeStr;
    }

    public void setPayTypeStr(String payTypeStr) {
        this.payTypeStr = payTypeStr;
    }

    @ExcelField(title = "交易类型", align = 2, sort = 40)
    public String getOrderTypeStr() {
        return orderTypeStr;
    }

    public void setOrderTypeStr(String orderTypeStr) {
        this.orderTypeStr = orderTypeStr;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public BigDecimal getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(BigDecimal backMoney) {
        this.backMoney = backMoney;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getOilStatus() {
        return oilStatus;
    }

    public void setOilStatus(String oilStatus) {
        this.oilStatus = oilStatus;
    }

    private String goodsImg;        //商品图片

    @ExcelField(title = "订单生成日期", align = 2, sort = 80)
    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    @ExcelField(title = "付款日期", align = 2, sort = 90)
    public String getPayDateStr() {
        return payDateStr;
    }

    public void setPayDateStr(String payDateStr) {
        this.payDateStr = payDateStr;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public HykOrder() {
        super();
    }

    public HykOrder(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "订单编号长度必须介于 0 和 64 之间")
    @ExcelField(title = "订单编号", align = 2, sort = 10)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public HykUser getHykUser() {
        return hykUser;
    }

    public void setHykUser(HykUser hykUser) {
        this.hykUser = hykUser;
    }

    @Length(min = 0, max = 11, message = "用户手机号长度必须介于 0 和 11 之间")
    @ExcelField(title = "注册手机号", align = 2, sort = 20)
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Length(min = 0, max = 64, message = "充值卡号长度必须介于 0 和 64 之间")
    @ExcelField(title = "充值卡号", align = 2, sort = 30)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public BigDecimal getBalancePayment() {
        return balancePayment;
    }

    public void setBalancePayment(BigDecimal balancePayment) {
        this.balancePayment = balancePayment;
    }

    @Length(min = 0, max = 2, message = "订单类型长度必须介于 0 和 2 之间")
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Length(min = 0, max = 10, message = "付款方式长度必须介于 0 和 10 之间")
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getEveryAmt() {
        return everyAmt;
    }

    public void setEveryAmt(BigDecimal everyAmt) {
        this.everyAmt = everyAmt;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    @Length(min = 0, max = 64, message = "商品编号长度必须介于 0 和 64 之间")
    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Length(min = 0, max = 64, message = "红包编号长度必须介于 0 和 64 之间")
    public String getRedpackageId() {
        return redpackageId;
    }

    public void setRedpackageId(String redpackageId) {
        this.redpackageId = redpackageId;
    }

    @ExcelField(title = "红包金额", align = 2, sort = 70)
    public BigDecimal getRedpackageAmt() {
        return redpackageAmt;
    }

    public void setRedpackageAmt(BigDecimal redpackageAmt) {
        this.redpackageAmt = redpackageAmt;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title = "付款时间", align = 2, sort = 70)
    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @Length(min = 0, max = 2, message = "订单状态长度必须介于 0 和 2 之间")
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Length(min = 0, max = 100, message = "备注长度必须介于 0 和 100 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Length(min = 0, max = 64, message = "f1长度必须介于 0 和 64 之间")
    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    @ExcelField(title = "订单金额(实际支付)", align = 2, sort = 50)
    public BigDecimal getPayableMoney() {
        return payableMoney;
    }

    public void setPayableMoney(BigDecimal payableMoney) {
        this.payableMoney = payableMoney;
    }


    @Length(min = 0, max = 64, message = "f3长度必须介于 0 和 64 之间")
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @ExcelField(title = "商品名称", align = 2, sort = 60)
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getCycel() {
        return cycel;
    }

    public void setCycel(String cycel) {
        this.cycel = cycel;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getActivityDiscount() {
        return activityDiscount;
    }

    public void setActivityDiscount(String activityDiscount) {
        this.activityDiscount = activityDiscount;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

}