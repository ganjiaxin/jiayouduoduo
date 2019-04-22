package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.ReportIndex;
import com.hyk.code.modules.hyk.entity.bo.CompanyBackMoneyBo;
import com.hyk.code.modules.hyk.entity.vo.CompanyMarVo;

import java.util.List;

/**
 * @Auther: 霍中曦
 * @Date: 2018/11/18 14:19
 * @Description:
 */
@MyBatisDao
public interface ReportDao extends CrudDao<ReportIndex> {

    public ReportIndex getYesterDayInifo();

    public ReportIndex getYesterDayFindList(ReportIndex report);

    public List<CompanyBackMoneyBo> listCompanyBackMoney();

    public String countRechargeAmountByLastMonthAll(CompanyMarVo companyMarVo);

    public String countRechargeAmountByLastMonth(CompanyMarVo companyMarVo);

    public String countRechargeAmountByCurrentMonth(CompanyMarVo companyMarVo);

    public String countBackMoneyByLastMonthAll(CompanyMarVo companyMarVo);

    public String countBackMoneyByLastMonth(CompanyMarVo companyMarVo);

    public String countBackMoneyByCurrentMonth(CompanyMarVo companyMarVo);



}