/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyk.code.common.persistence.DataEntity;
import com.hyk.code.common.utils.excel.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商城商品订单Entity
 *
 * @author wyy
 * @version 2018-12-21
 */
@ApiModel(value = "商城商品订单")
public class HykMallOrder extends DataEntity<HykMallOrder> {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(required = false, value = "订单编号")
    private String orderno;        // 订单编号
    @ApiModelProperty(required = false, value = "用户Id")
    private String userId;        // 用户Id
    @ApiModelProperty(required = false, value = "用户手机")
    private String phone;        // 用户手机
    @ApiModelProperty(required = false, value = "商品id")
    private String goodsId;        // 商品id
    @ApiModelProperty(required = false, value = "商品名称")
    private String goodsName;        // 商品名称
    @ApiModelProperty(required = false, value = "商品类型 mall_goodsType")
    private String goodsType;        // 商品类型 0虚拟物品 1实物商品
    @ApiModelProperty(required = false, value = "购买数量")
    private Integer num;        // 购买数量
    @ApiModelProperty(required = false, value = "订单总金额")
    private BigDecimal money;        // 订单总金额
    @ApiModelProperty(required = false, value = "支付时间")
    private Date payDate;        // 支付时间
    @ApiModelProperty(required = false, value = "支付类型 字典 payType")
    private String payType;        // 支付类型
    @ApiModelProperty(required = false, value = "订单状态 字典 mallorder_status")
    private String status;        // 订单状态 订单状态 0待支付 1支付中 2已支付 3支付失败 4订单已失效
    @ApiModelProperty(required = false, value = "发送状态 字典 mallorder_sendStatus")
    private String sendStatus;        // 发送状态
    @ApiModelProperty(required = false, value = "买家备注")
    private String remark;        // 买家备注
    private String wltype;        // 物流公司缩写
    @ApiModelProperty(required = false, value = "公司名称")
    private String wltypeStr;//公司名称
    @ApiModelProperty(required = false, value = "物流编号")
    private String postid;        // 物流编号
    private String addressId;//地址id
    @ApiModelProperty(required = false, value = "收货人")
    private String addressName;//收货人
    @ApiModelProperty(required = false, value = "收货手机号")
    private String addressPhone;//收货手机号
    @ApiModelProperty(required = false, value = "收货地址")
    private String address;//收货地址
    private Date createDate;

    private String goodsTypeStr;
    private String payTypeStr;
    private String statusStr;

    private String sendStatusStr;
    private String userName;        // 用户名称
    private String sysTime;
    private String createTime;
    private String img;
    private String payDateStr;        // 支付时间
    private String createDateStr;

    @ApiModelProperty(required = false, value = "开始时间")
    private Date startDate;//开始时间
    @ApiModelProperty(required = false, value = "结束时间")
    private Date endDate;//结束时间
    @ApiModelProperty(required = false, value = "条件参数")
    private String other;//条件参数

    private String category;//商品模块

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWltypeStr() {
        return wltypeStr;
    }

    public void setWltypeStr(String wltypeStr) {
        this.wltypeStr = wltypeStr;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @ExcelField(title="付款时间", align=2, sort=60)
    public String getPayDateStr() {
        return payDateStr;
    }

    public void setPayDateStr(String payDateStr) {
        this.payDateStr = payDateStr;
    }
    @ExcelField(title="订单生成时间", align=2, sort=55)
    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @ExcelField(title="商品类型", align=2, sort=50)
    public String getGoodsTypeStr() {
        return goodsTypeStr;
    }

    public void setGoodsTypeStr(String goodsTypeStr) {
        this.goodsTypeStr = goodsTypeStr;
    }
    @ExcelField(title="付款方式", align=2, sort=65)
    public String getPayTypeStr() {
        return payTypeStr;
    }

    public void setPayTypeStr(String payTypeStr) {
        this.payTypeStr = payTypeStr;
    }
    @ExcelField(title="订单状态", align=2, sort=70)
    public String getStatusStr() {
        return statusStr;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    @ExcelField(title="发货状态", align=2, sort=70)
    public String getSendStatusStr() {
        return sendStatusStr;
    }

    public void setSendStatusStr(String sendStatusStr) {
        this.sendStatusStr = sendStatusStr;
    }

    public HykMallOrder() {
        super();
    }

    public HykMallOrder(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "订单编号长度必须介于 0 和 64 之间")
    @ExcelField(title="订单编号", align=2, sort=10)
    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }


    @Length(min = 0, max = 64, message = "用户手机长度必须介于 0 和 64 之间")
    @ExcelField(title="用户手机", align=2, sort=20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min = 0, max = 64, message = "商品id长度必须介于 0 和 64 之间")
    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Length(min = 0, max = 64, message = "商品名称长度必须介于 0 和 64 之间")
    @ExcelField(title="商品名称", align=2, sort=30)
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Length(min = 0, max = 64, message = "商品类型长度必须介于 0 和 64 之间")
    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
    @ExcelField(title="购买数量", align=2, sort=40)
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
    @ExcelField(title="订单金额", align=2, sort=45)
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @Length(min = 0, max = 2, message = "支付类型长度必须介于 0 和 2 之间")
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Length(min = 0, max = 2, message = "订单状态长度必须介于 0 和 2 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Length(min = 0, max = 2, message = "发送状态长度必须介于 0 和 2 之间")
    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    @Length(min = 0, max = 255, message = "备注长度必须介于 0 和 255 之间")
    @ExcelField(title="卖家备注", align=2, sort=74)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWltype() {
        return wltype;
    }

    public void setWltype(String wltype) {
        this.wltype = wltype;
    }
    @ExcelField(title="运单号", align=2, sort=75)
    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
    @ExcelField(title="收件人", align=2, sort=71)
    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
    @ExcelField(title="收件手机", align=2, sort=72)
    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }
    @ExcelField(title="收件地址", align=2, sort=73)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}