/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;
import com.hyk.code.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 用户银行卡信息Entity
 * @author 霍中曦
 * @version 2018-12-06
 */
public class HykUserBank extends DataEntity<HykUserBank> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String noAgree;		// 签约号
	private String idType;		// 身份证类型
	private String idNo;		// 身份证号
	private String acctName;		// 姓名
	private String cardNo;		// 卡号
	
	public HykUserBank() {
		super();
	}

	public HykUserBank(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Length(min=0, max=64, message="签约号长度必须介于 0 和 64 之间")
	public String getNoAgree() {
		return noAgree;
	}

	public void setNoAgree(String noAgree) {
		this.noAgree = noAgree;
	}
	
	@Length(min=0, max=64, message="身份证类型长度必须介于 0 和 64 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	@Length(min=0, max=64, message="身份证号长度必须介于 0 和 64 之间")
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	@Length(min=0, max=64, message="姓名长度必须介于 0 和 64 之间")
	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	
	@Length(min=0, max=64, message="卡号长度必须介于 0 和 64 之间")
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
}