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
 * 红包管理Entity
 *
 * @author 霍中曦
 * @version 2018-11-14
 */
public class HykRedpackage extends DataEntity<HykRedpackage> {

    private static final long serialVersionUID = 1L;
    private String title;        // 红包名称
    private BigDecimal money;    // 红包金额
    private String status;        // 红包状态0未使用 1已使用 2已过期
    private Date sendTime;        // 发送时间
    private Date useTime;        // 使用时间
    private Date overTime;        // 过期时间
    private String remark;        // 备注
    private String f1;        // f1
    private Integer dayNum;        //使用天长
    private String redType;        // 红包类型 0套餐 1即冲，2无限制
    private String userId;        // 用户id
    private String goodsId;       //指定商品使用限制

    private BigDecimal minAmount;    // 最少充值多少可用
    private String statusName;//逻辑字段
    private String redTypeName;

    //=======================================
    private String sendTimeStr;        // 发送时间
    private String useTimeStr;        // 使用时间
    private String overTimeStr;        // 过期时间
    private String phone;//用户手机号
    private String other;//综合查询字段
    private String idStr;//id编号
    private String goodsName;//商品名

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @ExcelField(title = "红包编号", align = 2, sort = 10)
    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @ExcelField(title = "手机号码", align = 2, sort = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public HykRedpackage() {
        super();
    }

    public HykRedpackage(String id) {
        super(id);
    }

    public String getRedTypeName() {
        return redTypeName;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public void setRedTypeName(String redTypeName) {
        this.redTypeName = redTypeName;
    }

    @ExcelField(title = "红包状态", align = 2, sort = 60)
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Length(min = 1, max = 64, message = "红包名称长度必须介于 1 和 64 之间")
    @ExcelField(title = "红包名称", align = 2, sort = 40)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull(message = "红包金额不能为空")
    @ExcelField(title = "红包金额", align = 2, sort = 50)
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Length(min = 1, max = 2, message = "红包状态长度必须介于 1 和 2 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "使用时长不能为空")
    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "红包类型不能为空")
    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
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

    public String getRedType() {
        return redType;
    }

    public void setRedType(String redType) {
        this.redType = redType;
    }

    @ExcelField(title = "用户id", align = 2, sort = 30)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @ExcelField(title = "红包发放时间", align = 2, sort = 70)
    public String getSendTimeStr() {
        return sendTimeStr;
    }

    public void setSendTimeStr(String sendTimeStr) {
        this.sendTimeStr = sendTimeStr;
    }

    public String getUseTimeStr() {
        return useTimeStr;
    }

    public void setUseTimeStr(String useTimeStr) {
        this.useTimeStr = useTimeStr;
    }

    @ExcelField(title = "红包过期时间", align = 2, sort = 80)
    public String getOverTimeStr() {
        return overTimeStr;
    }

    public void setOverTimeStr(String overTimeStr) {
        this.overTimeStr = overTimeStr;
    }

    @Override
    public String toString() {
        return "HykRedpackage{" +
                "title='" + title + '\'' +
                ", money=" + money +
                ", status='" + status + '\'' +
                ", sendTime=" + sendTime +
                ", useTime=" + useTime +
                ", overTime=" + overTime +
                ", remark='" + remark + '\'' +
                ", f1='" + f1 + '\'' +
                ", dayNum=" + dayNum +
                ", redType='" + redType + '\'' +
                ", userId='" + userId + '\'' +
                ", statusName='" + statusName + '\'' +
                ", redTypeName='" + redTypeName + '\'' +
                ", sendTimeStr='" + sendTimeStr + '\'' +
                ", useTimeStr='" + useTimeStr + '\'' +
                ", overTimeStr='" + overTimeStr + '\'' +
                ", phone='" + phone + '\'' +
                ", other='" + other + '\'' +
                ", idStr='" + idStr + '\'' +
                '}';
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

}