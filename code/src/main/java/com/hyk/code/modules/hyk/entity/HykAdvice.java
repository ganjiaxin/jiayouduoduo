/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import com.hyk.code.common.persistence.DataEntity;

import java.util.Date;

/**
 * 意见反馈管理Entity
 * @author 霍中曦
 * @version 2018-12-11
 */
@ApiModel(value = "意见反馈",description = "意见反馈对象")
public class HykAdvice extends DataEntity<HykAdvice> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(required=false,value="反馈内容")
	private String content;		// 反馈内容
	private String imgs;		// 图片
	private String phone;		// 注册手机号
	private String status;		// 状态
	private String answer;		// 回复内容
	private String f1;		// f1
	private String f2;		// f2

	private Date createDate;//创建日期
	private String createDateStr;//创建日期格式化

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public HykAdvice() {
		super();
	}

	public HykAdvice(String id){
		super(id);
	}

	@Length(min=0, max=100, message="反馈内容长度必须介于 0 和 100 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=100, message="图片长度必须介于 0 和 100 之间")
	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	
	@Length(min=0, max=11, message="注册手机号长度必须介于 0 和 11 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=100, message="回复内容长度必须介于 0 和 100 之间")
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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
	
}