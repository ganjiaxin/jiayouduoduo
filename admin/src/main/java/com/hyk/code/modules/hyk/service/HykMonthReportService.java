/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykMonthReportDao;
import com.hyk.code.modules.hyk.entity.HykMonthReport;
import com.hyk.code.modules.hyk.entity.vo.ReportVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 每月信息汇总Service
 * @author 霍中曦
 * @version 2019-02-18
 */
@Service
@Transactional(readOnly = true)
public class HykMonthReportService extends CrudService<HykMonthReportDao, HykMonthReport> {

	public HykMonthReport get(String id) {
		return super.get(id);
	}
	
	public List<HykMonthReport> findList(HykMonthReport hykMonthReport) {
		return super.findList(hykMonthReport);
	}
	
	public Page<HykMonthReport> findPage(Page<HykMonthReport> page, HykMonthReport hykMonthReport) {
		return super.findPage(page, hykMonthReport);
	}
	
	@Transactional(readOnly = false)
	public void save(HykMonthReport hykMonthReport) {
		super.save(hykMonthReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykMonthReport hykMonthReport) {
		super.delete(hykMonthReport);
	}


	/*查询当前月份数据*/
	public List<HykMonthReport> listByMonth(HykMonthReport hykMonthReport) {
		return dao.listByMonth(hykMonthReport);
	}

	public List<ReportVo> countOrderNumByLastMonth() {
		return dao.countOrderNumByLastMonth();
	}
	public List<ReportVo> countOrderMoneyByLastMonth() {
		return dao.countOrderMoneyByLastMonth();
	}
	public List<ReportVo> countOrderBackMoneyLastMonth() {
		return dao.countOrderBackMoneyLastMonth();
	}
	public List<ReportVo> countOrderMoneyByUpMonth() {
		return dao.countOrderMoneyByUpMonth();
	}

	public List<ReportVo> countOrderBackMoneyUpMonth() {
		return dao.countOrderBackMoneyUpMonth();
	}

	@Transactional(readOnly = false)
	public void saveList(List<HykMonthReport> list){

		dao.saveList(list);
	}

	public List<ReportVo> countCurrentMonthInviterNum() {
		return dao.countCurrentMonthInviterNum();
	}

	public List<ReportVo> countCurrentMonthOrderAmt() {
		return dao.countCurrentMonthOrderAmt();
	}

	public List<ReportVo> countCurrentMonthBackMoney() {
		return dao.countCurrentMonthBackMoney();
	}


	public List<ReportVo> countOrderNumByLastMonthEmployee() {
		return dao.countOrderNumByLastMonthEmployee();
	}

	public List<ReportVo> countOrderMoneyByLastMonthEmployee() {
		return dao.countOrderMoneyByLastMonthEmployee();
	}

	public List<ReportVo> countOrderBackMoneyLastMonthEmployee() {
		return dao.countOrderBackMoneyLastMonthEmployee();
	}

	public List<ReportVo> countOrderMoneyByUpMonthEmployee() {
		return dao.countOrderMoneyByUpMonthEmployee();
	}

	public List<ReportVo> countOrderBackMoneyUpMonthEmployee() {
		return dao.countOrderBackMoneyUpMonthEmployee();
	}
	public List<ReportVo> countCurrentMonthOrderAmtEmployee() {
		return dao.countCurrentMonthOrderAmtEmployee();
	}
	public List<ReportVo> countCurrentMonthBackMoneyEmployee() {
		return dao.countCurrentMonthBackMoneyEmployee();
	}
	public List<ReportVo> countCurrentMonthInviterNumEmployee() {
		return dao.countCurrentMonthInviterNumEmployee();
	}



}