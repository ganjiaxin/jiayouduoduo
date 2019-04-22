/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 支付报文Entity
 * @author 霍中曦
 * @version 2018-12-06
 */
public class HykPayinfo extends DataEntity<HykPayinfo> {
	
	private static final long serialVersionUID = 1L;
	private String orderNo;		// 订单号
	private String reqMsg;		// 请求报文
	private String respMsg;		// 反馈报文
	private String code;		// 状态
	
	public HykPayinfo() {
		super();
	}

	public HykPayinfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="订单号长度必须介于 1 和 64 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getReqMsg() {
		return reqMsg;
	}

	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}
	
	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
	@Length(min=0, max=64, message="状态长度必须介于 0 和 64 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}