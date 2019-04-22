/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.hyk.code.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.hyk.code.common.persistence.DataEntity;

/**
 * 发票管理Entity
 * @author 霍中曦
 * @version 2018-11-12
 */
public class HykInvoice extends DataEntity<HykInvoice> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户编号
	private String userPhone;		// 用户手机号
	private String nature;		// 发票性质
	private String title;		// 发票抬头
	private String num;		// 税号
	private Long money;		// 金额
	private String receive;		// 收件人
	private String tel;		// 联系方式
	private String addr;		// 地址
	private String status;		// 状态 0未邮寄 1已邮寄
	private String remark;		// 备注
	private String f1;		// f1
	private String f2;		// f2
	private String f3;		// f3

	private String natureStr;

	public String getNatureStr() {
		return natureStr;
	}

	public void setNatureStr(String natureStr) {
		this.natureStr = natureStr;
	}

	public HykInvoice() {
		super();
	}

	public HykInvoice(String id){
		super(id);
	}

	@NotNull(message="用户编号不能为空")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=12, message="用户手机号长度必须介于 0 和 12 之间")
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	@Length(min=0, max=2, message="发票性质长度必须介于 0 和 2 之间")
	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}
	
	@Length(min=0, max=64, message="发票抬头长度必须介于 0 和 64 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=64, message="税号长度必须介于 0 和 64 之间")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}
	
	@Length(min=0, max=10, message="收件人长度必须介于 0 和 10 之间")
	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}
	
	@Length(min=0, max=12, message="联系方式长度必须介于 0 和 12 之间")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Length(min=0, max=100, message="地址长度必须介于 0 和 100 之间")
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=100, message="备注长度必须介于 0 和 100 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=64, message="f1长度必须介于 0 和 64 之间")
	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}
	
	@Length(min=0, max=64, message="f2长度必须介于 0 和 64 之间")
	public String getF2() {
		return f2;
	}

	public void setF2(String f2) {
		this.f2 = f2;
	}
	
	@Length(min=0, max=64, message="f3长度必须介于 0 和 64 之间")
	public String getF3() {
		return f3;
	}

	public void setF3(String f3) {
		this.f3 = f3;
	}
	
}