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

import java.util.Date;

/**
 * 充值卡管理Entity
 * @author 霍中曦
 * @version 2018-12-18
 */
@ApiModel(value = "充值卡管理")
public class HykRechargeCard extends DataEntity<HykRechargeCard> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(required=false,value="批次号 字典 card_code")
	private String code;			//批次号 字典 card_code
	@ApiModelProperty(required=false,value="卡号")
	private String caredno;		// 卡号
	@ApiModelProperty(required=false,value="卡密")
	private String password;		// 卡密
	@ApiModelProperty(required=false,value="金额")
	private Long money;			// 金额
	@ApiModelProperty(required=false,value="卡密用途 0印卡 1虚拟商品 字典 ")
	private String useMethod;		//  卡密用途 0印卡 1虚拟商品 字典 card_useMethod
	@ApiModelProperty(required=false,value="卡密状态 0未兑换 1已兑换 2已过期 3未发放 字典 card_saleStatus")
	private String saleStatus;	// 卡密状态 0未兑换 1已兑换 2已过期 3未销售 字典 card_saleStatus
	@ApiModelProperty(required=false,value="创建时间")
	private Date createDate;		//创建时间
	@ApiModelProperty(required=false,value="过时时间")
	private Date overDate;			// 过时时间
	@ApiModelProperty(required=false,value="有效天数")
	private Integer days;			//有效天数
	@ApiModelProperty(required=false,value="是否激活 0未激活  1激活 字典 card_status")
	private String status;			// 是否激活 0未激活  1激活 字典 card_status
	//=========================================
	private int cardNum;//生产卡片数量
	@ApiModelProperty(required=false,value="卡密用途字典")
	private String useMethodStr;		//  卡密用途 0印卡 1虚拟商品 字典 card_useMethod
	@ApiModelProperty(required=false,value="卡密状态字典")
	private String saleStatusStr;	// 卡密状态 0未兑换 1已兑换 2已过期  字典 card_saleStatus
	@ApiModelProperty(required=false,value="创建时间")
	private String createDateStr;		//创建时间
	@ApiModelProperty(required=false,value="过时时间")
	private String overDateStr;			// 过时时间
	@ApiModelProperty(required=false,value="是否激活字典")
	private String statusStr;			// 是否激活 0未激活  1激活 字典 card_status

	@ApiModelProperty(required=false,value="开始时间")
	private Date startDate;//开始时间
	@ApiModelProperty(required=false,value="结束时间")
	private Date endDate;//结束时间
	@ApiModelProperty(required=false,value="条件参数")
	private String other;//条件参数

	@ApiModelProperty(required=false,value="兑换时间")
	private String useDateStr;//兑换时间
	@ApiModelProperty(required=false,value="兑换用户")
	private String userName;//兑换用户



	public String getUseDateStr() {
		return useDateStr;
	}

	public void setUseDateStr(String useDateStr) {
		this.useDateStr = useDateStr;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getUseMethodStr() {
		return useMethodStr;
	}

	public void setUseMethodStr(String useMethodStr) {
		this.useMethodStr = useMethodStr;
	}

	public String getSaleStatusStr() {
		return saleStatusStr;
	}

	public void setSaleStatusStr(String saleStatusStr) {
		this.saleStatusStr = saleStatusStr;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	@ExcelField(title="过期时间", align=2, sort=30)
	public String getOverDateStr() {
		return overDateStr;
	}

	public void setOverDateStr(String overDateStr) {
		this.overDateStr = overDateStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
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

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public HykRechargeCard() {
		super();
	}

	public HykRechargeCard(String id){
		super(id);
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Date getOverDate() {
		return overDate;
	}

	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

	public String getUseMethod() {
		return useMethod;
	}

	public void setUseMethod(String useMethod) {
		this.useMethod = useMethod;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Length(min=0, max=64, message="卡号长度必须介于 0 和 64 之间")
	@ExcelField(title="卡号", align=2, sort=10)
	public String getCaredno() {
		return caredno;
	}

	public void setCaredno(String caredno) {
		this.caredno = caredno;
	}
	
	@Length(min=0, max=64, message="卡密长度必须介于 0 和 64 之间")
	@ExcelField(title="卡密", align=2, sort=20)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}
	
	@Length(min=0, max=2, message="售卖状态长度必须介于 0 和 2 之间")
	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}
	

}