/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyk.code.common.persistence.DataEntity;
import com.hyk.code.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 加油管理Entity
 *
 * @author 霍中曦
 * @version 2018-11-09
 */
public class HykOilManager extends DataEntity<HykOilManager> {

    private static final long serialVersionUID = 1L;
    private String orderNo;        // 订单编号
    private HykUser hykUser;        // 用户编号
    private HykOrder hykOrder;        //订单对象
    private HykOilCard hykOilCard;//油卡对象
    private Long periodsNum;        // 剩余期数
    private Date oilDate;        // 加油时间
    private Date planOilDate;        // 计划加油时间
    private String status;        // 状态0 待加油 1已加油 2暂停(订单暂停 或退款情况)
    private String remark;        // 备注
    private HykGoods hykGoods;   // 商品对象
    private String orderId;        // 订单id
    private String cardId;        // 油卡id
    private String goodId;//商品id
    private BigDecimal money;        // 本期计划加油金额
    private Date createDate;
    private String statusName;//数据字典字段

    //===========================================
    private String userPhone;//注册手机号
    private Date startTime;//开始时间
    private Date endTime;//结束时间
    private String other;//手机号 订单号 用户id
    private String oilDateStr;//加油时间格式化日期
    private String planOilDateStr;//计划加油时间格式化日期
    private String cardName;        //卡名mxf
    private String cardNum;         //卡号mxf
    private String cycle;           //总期数mxf
    private String goodsType;       //商品类型 1即时充值2加油套餐
    private String year;
    private String month;
    private String day;
    private String orderTypeStr;//交易类型
    private String goodsName;//商品名称
    private String periodsNumName;        // 剩余期数

    private String oilTypeStr;//加油卡类型

    public String getOilTypeStr() {
        return oilTypeStr;
    }

    public void setOilTypeStr(String oilTypeStr) {
        this.oilTypeStr = oilTypeStr;
    }

    @ExcelField(title="加油期数", align=2, sort=70)
    public String getPeriodsNumName() {
        return periodsNumName;
    }

    public void setPeriodsNumName(String periodsNumName) {
        this.periodsNumName = periodsNumName;
    }

    @ExcelField(title="商品名称", align=2, sort=80)
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @ExcelField(title="交易类型", align=2, sort=50)
    public String getOrderTypeStr() {
        return orderTypeStr;
    }

    public void setOrderTypeStr(String orderTypeStr) {
        this.orderTypeStr = orderTypeStr;
    }
    @ExcelField(title="注册手机号", align=2, sort=20)
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    @ExcelField(title="加油状态", align=2, sort=110)
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public HykOilCard getHykOilCard() {
        return hykOilCard;
    }

    public void setHykOilCard(HykOilCard hykOilCard) {
        this.hykOilCard = hykOilCard;
    }

    public HykOrder getHykOrder() {
        return hykOrder;
    }

    public void setHykOrder(HykOrder hykOrder) {
        this.hykOrder = hykOrder;
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

    @ExcelField(title="加油时间", align=2, sort=100)
    public String getOilDateStr() {
        return oilDateStr;
    }

    public void setOilDateStr(String oilDateStr) {
        this.oilDateStr = oilDateStr;
    }

    @ExcelField(title="加油计划日期", align=2, sort=90)
    public String getPlanOilDateStr() {
        return planOilDateStr;
    }

    public void setPlanOilDateStr(String planOilDateStr) {
        this.planOilDateStr = planOilDateStr;
    }

    public HykOilManager() {
        super();
    }

    public HykOilManager(String id) {
        super(id);
    }

    @ExcelField(title="加油金额", align=2, sort=60)
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Length(min = 1, max = 64, message = "订单编号长度必须介于 1 和 64 之间")
    @ExcelField(title="订单编号", align=2, sort=10)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @NotNull(message = "用户编号不能为空")
    public HykUser getHykUser() {
        return hykUser;
    }

    public void setHykUser(HykUser hykUser) {
        this.hykUser = hykUser;
    }


    public Long getPeriodsNum() {
        return periodsNum;
    }

    public void setPeriodsNum(Long periodsNum) {
        this.periodsNum = periodsNum;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getOilDate() {
        return oilDate;
    }

    public void setOilDate(Date oilDate) {
        this.oilDate = oilDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getPlanOilDate() {
        return planOilDate;
    }

    public void setPlanOilDate(Date planOilDate) {
        this.planOilDate = planOilDate;
    }

    @Length(min = 0, max = 2, message = "状态长度必须介于 0 和 2 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Length(min = 0, max = 100, message = "备注长度必须介于 0 和 100 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HykGoods getHykGoods() {
        return hykGoods;
    }

    public void setHykGoods(HykGoods hykGoods) {
        this.hykGoods = hykGoods;
    }

    @Length(min = 0, max = 64, message = "f2长度必须介于 0 和 64 之间")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Length(min = 0, max = 64, message = "f3长度必须介于 0 和 64 之间")
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }
    @ExcelField(title="持卡人姓名", align=2, sort=40)
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    @ExcelField(title="充值卡号", align=2, sort=30)
    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }


    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

}