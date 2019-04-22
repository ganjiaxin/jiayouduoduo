/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 每月信息汇总Entity
 * @author 霍中曦
 * @version 2019-02-18
 */
public class HykMonthReport extends DataEntity<HykMonthReport> {
	
	private static final long serialVersionUID = 1L;
	private String companyId;		// 公司id
	private String num;		// 统计结果
	private String type;		// 类型1截止上月交易笔数2截止上月累计充值金额3截止上月历史交易返现4邀请用户上月充值金额5邀请用户上月交易返现
	private String isboss;		// 0员工统计1公司统计
	private String employeeId;		// 员工id
	private Date createDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public HykMonthReport() {
		super();
	}

	public HykMonthReport(String id){
		super(id);
	}

	@Length(min=1, max=64, message="公司id长度必须介于 1 和 64 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@NotNull(message="统计金额不能为空")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@Length(min=1, max=2, message="类型1截止上月交易笔数2截止上月累计充值金额3截止上月历史交易返现4邀请用户上月充值金额5邀请用户上月交易返现长度必须介于 1 和 2 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=1, max=2, message="0员工统计1公司统计长度必须介于 1 和 2 之间")
	public String getIsboss() {
		return isboss;
	}

	public void setIsboss(String isboss) {
		this.isboss = isboss;
	}
	
	@Length(min=0, max=64, message="员工id长度必须介于 0 和 64 之间")
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
}