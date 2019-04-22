/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykMonthReport;
import com.hyk.code.modules.hyk.entity.vo.ReportVo;

import java.util.List;

/**
 * 每月信息汇总DAO接口
 * @author 霍中曦
 * @version 2019-02-18
 */
@MyBatisDao
public interface HykMonthReportDao extends CrudDao<HykMonthReport> {

    public List<HykMonthReport> listByMonth(HykMonthReport hykMonthReport);


    public List<ReportVo> countOrderNumByLastMonth();

    public List<ReportVo> countOrderMoneyByLastMonth();

    public List<ReportVo> countOrderBackMoneyLastMonth();


    public List<ReportVo> countOrderMoneyByUpMonth();

    public List<ReportVo> countOrderBackMoneyUpMonth();


    public void saveList(List<HykMonthReport> list);

    public List<ReportVo> countCurrentMonthInviterNum();

    public List<ReportVo> countCurrentMonthOrderAmt();

    public List<ReportVo> countCurrentMonthBackMoney();

    public List<ReportVo> countOrderNumByLastMonthEmployee();

    public List<ReportVo> countOrderMoneyByLastMonthEmployee();

    public List<ReportVo> countOrderBackMoneyLastMonthEmployee();

    public List<ReportVo> countOrderMoneyByUpMonthEmployee();

    public List<ReportVo> countOrderBackMoneyUpMonthEmployee();

    public List<ReportVo> countCurrentMonthOrderAmtEmployee();

    public List<ReportVo> countCurrentMonthBackMoneyEmployee();

    public List<ReportVo> countCurrentMonthInviterNumEmployee();


}