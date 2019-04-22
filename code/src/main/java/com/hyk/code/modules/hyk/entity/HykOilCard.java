/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.utils.excel.annotation.ExcelField;
import com.hyk.code.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.hyk.code.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * 加油卡管理Entity
 * @author 霍中曦
 * @version 2018-11-12
 */
public class HykOilCard extends DataEntity<HykOilCard> {
	
	private static final long serialVersionUID = 1L;
	private HykUser hykUser;		// 注册手机号
	private String userId; 		//用户编号
	private String oliCardNo;		// 加油卡号
	private String name;		// 充值人姓名
	private String phone;		// 手机号码
	private String oilType;		// 油卡类型   1中石油 2中石化
	private BigDecimal money;		// 累计充值金额
	private BigDecimal waitMoney;		// 剩余待充金额
	private String remark;		// 备注
	private BigDecimal ydzMoney;		// 本月应到账金额
	private BigDecimal sjdzMoney;		// 本月实际已到账金额
	private String label;		// 标签

	//========================逻辑字段
	private String oilTypeStr;
	private String other;
	private String userPhone;//注册手机号
	private String realName;//充值卡姓名
	 private BigDecimal surplusAdd;//1.4剩余应加


	public BigDecimal getSurplusAdd() {
		return surplusAdd;
	}

	public void setSurplusAdd(BigDecimal surplusAdd) {
		this.surplusAdd = surplusAdd;
	}


	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@ExcelField(title="注册手机号", align=2, sort=10)
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	@ExcelField(title="油卡类型", align=2, sort=50)
	public String getOilTypeStr() {
		return oilTypeStr;
	}

	public void setOilTypeStr(String oilTypeStr) {
		this.oilTypeStr = oilTypeStr;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public HykOilCard() {
		super();
	}

	public HykOilCard(String id){
		super(id);
	}

	@NotNull(message="注册手机号不能为空")
	public HykUser getHykUser() {
		return hykUser;
	}

	public void setHykUser(HykUser hykUser) {
		this.hykUser = hykUser;
	}
	
	@Length(min=1, max=64, message="加油卡号长度必须介于 1 和 64 之间")
	@ExcelField(title="加油卡号", align=2, sort=20)
	public String getOliCardNo() {
		return oliCardNo;
	}

	public void setOliCardNo(String oliCardNo) {
		this.oliCardNo = oliCardNo;
	}
	
	@Length(min=0, max=64, message="充值人姓名长度必须介于 0 和 64 之间")
	@ExcelField(title="充值姓名", align=2, sort=30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="手机号码长度必须介于 0 和 64 之间")
	@ExcelField(title="持卡人手机号", align=2, sort=40)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=64, message="油卡类型长度必须介于 0 和 64 之间")
	public String getOilType() {
		return oilType;
	}

	public void setOilType(String oilType) {
		this.oilType = oilType;
	}
	@ExcelField(title="累计充值金额", align=2, sort=60)
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	@ExcelField(title="剩余充值金额", align=2, sort=70)
	public BigDecimal getWaitMoney() {
		return waitMoney;
	}

	public void setWaitMoney(BigDecimal waitMoney) {
		this.waitMoney = waitMoney;
	}
	
	@Length(min=0, max=100, message="备注长度必须介于 0 和 100 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@ExcelField(title="本月应到账金额", align=2, sort=80)
	public BigDecimal getYdzMoney() {
		return ydzMoney;
	}

	public void setYdzMoney(BigDecimal ydzMoney) {
		this.ydzMoney = ydzMoney;
	}
	@ExcelField(title="实际应到账金额", align=2, sort=90)
	public BigDecimal getSjdzMoney() {
		return sjdzMoney;
	}

	public void setSjdzMoney(BigDecimal sjdzMoney) {
		this.sjdzMoney = sjdzMoney;
	}
	
	@Length(min=0, max=64, message="label长度必须介于 0 和 64 之间")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}