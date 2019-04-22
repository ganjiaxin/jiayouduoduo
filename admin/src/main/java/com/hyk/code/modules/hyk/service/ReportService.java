package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.service.CrudService;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.modules.hyk.dao.ReportDao;
import com.hyk.code.modules.hyk.entity.ReportIndex;
import com.hyk.code.modules.hyk.entity.bo.CompanyBackMoneyBo;
import com.hyk.code.modules.hyk.entity.vo.CompanyMarVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: 霍中曦
 * @Date: 2018/11/18 14:18
 * @Description:
 */


@Service
@Transactional(readOnly = true)
public class ReportService extends CrudService<ReportDao, ReportIndex> {
    @Autowired
    private ReportDao reportDao;

    /*昨日统计*/
    public ReportIndex getYesterDayInifo() {

        return reportDao.getYesterDayInifo();

    }

    /*历史查询统计*/
    public ReportIndex getYesterDayFindList(ReportIndex report) {

        return reportDao.getYesterDayFindList(report);

    }

    /**
     * 功能描述: 获取上个月每个公司的返现金额
     *
     * @auther: 霍中曦
     * @return: list集合
     * @date: 2019/1/23 11:05
     */
    public List<CompanyBackMoneyBo> listCompanyBackMoney() {

        return reportDao.listCompanyBackMoney();

    }


    /**
     * 邀请用户截至上月累计充值金额
     */
    @Transactional(readOnly = false)
    public BigDecimal countRechargeAmountByLastMonthAll(CompanyMarVo companyMarVo) {

        String money=reportDao.countRechargeAmountByLastMonthAll(companyMarVo);

        return StringUtils.isEmpty(money)?BigDecimal.ZERO:new BigDecimal(money);

    }

    /**
     * 邀请用户上月充值金额
     */
    @Transactional(readOnly = false)
    public BigDecimal countRechargeAmountByLastMonth(CompanyMarVo companyMarVo) {

        String money=reportDao.countRechargeAmountByLastMonth(companyMarVo);

        return StringUtils.isEmpty(money)?BigDecimal.ZERO:new BigDecimal(money);

    }


    /**
     * 邀请用户本月充值金额
     */
    @Transactional(readOnly = false)
    public BigDecimal countRechargeAmountByCurrentMonth(CompanyMarVo companyMarVo) {

        String money=reportDao.countRechargeAmountByCurrentMonth(companyMarVo);

        return StringUtils.isEmpty(money)?BigDecimal.ZERO:new BigDecimal(money);

    }

    /**
     * 邀请用户截止上月累计交易返现金额
     */
    @Transactional(readOnly = false)
    public BigDecimal countBackMoneyByLastMonthAll(CompanyMarVo companyMarVo) {

        String money=reportDao.countBackMoneyByLastMonthAll(companyMarVo);

        return StringUtils.isEmpty(money)?BigDecimal.ZERO:new BigDecimal(money);

    }
    /**
     * 邀请用户上月返现金额
     */
    @Transactional(readOnly = false)
    public BigDecimal countBackMoneyByLastMonth(CompanyMarVo companyMarVo) {

        String money=reportDao.countBackMoneyByLastMonth(companyMarVo);

        return StringUtils.isEmpty(money)?BigDecimal.ZERO:new BigDecimal(money);

    }


    /**
     * 邀请用户本月返现金额
     */
    @Transactional(readOnly = false)
    public BigDecimal countBackMoneyByCurrentMonth(CompanyMarVo companyMarVo) {

        String money=reportDao.countBackMoneyByCurrentMonth(companyMarVo);

        return StringUtils.isEmpty(money)?BigDecimal.ZERO:new BigDecimal(money);

    }


}