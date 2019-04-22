/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import java.util.Date;

/**
 * 验证码管理Entity
 *
 * @author 霍中曦
 * @version 2018-11-12
 */
public class HykYzm extends DataEntity<HykYzm> {

    private static final long serialVersionUID = 1L;
    private String phone;        // 手机号
    private Integer type;     //类型 1注册 2找回密码
    private Long code;        // 验证码
    private Long times;        // 有效时长
    private String remark;        // 备注
	private String ip;//请求ip

    private Date createDate;//创建日期
	private String channel;
	private String createDateStr;
	private String typeStr;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}


	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public HykYzm() {
        super();
    }

	public HykYzm(String id){
		super(id);
	}

	@Length(min=1, max=11, message="手机号长度必须介于 1 和 11 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@NotNull(message="验证码不能为空")
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@NotNull(message="有效时长不能为空")
	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}
	
	@Length(min=0, max=100, message="备注长度必须介于 0 和 100 之间")
	public String getRemark() {
		return remark;
	}

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}