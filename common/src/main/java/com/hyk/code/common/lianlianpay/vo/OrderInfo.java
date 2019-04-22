package com.hyk.code.common.lianlianpay.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户订单信息
 * 
 * @version V1.0
 * @author chencheng@yintong.com.cn
 * @Date 2017年8月16日 下午2:57:07
 * @since JDK 1.6
 */
public class OrderInfo implements Serializable{
    private static final long serialVersionUID = 1L;

	private String no_order; // 商户唯一订单号

	private String name_goods; // 商品名称

	private String info_order; // 订单描述

	private String money_order; // 交易金额

	private String flag_chnl;//应用渠道

	private String user_id;//用户id

	private Date registerDate;//用户注册时间

	private String phone;//用户手机号

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getNo_order() {
		return no_order;
	}

	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}

	public String getName_goods() {
		return name_goods;
	}

	public void setName_goods(String name_goods) {
		this.name_goods = name_goods;
	}

	public String getInfo_order() {
		return info_order;
	}

	public void setInfo_order(String info_order) {
		this.info_order = info_order;
	}

	public String getMoney_order() {
		return money_order;
	}

	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}

	public String getFlag_chnl() {
		return flag_chnl;
	}

	public void setFlag_chnl(String flag_chnl) {
		this.flag_chnl = flag_chnl;
	}
}
