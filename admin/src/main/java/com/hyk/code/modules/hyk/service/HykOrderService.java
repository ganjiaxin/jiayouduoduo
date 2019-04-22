/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykOilCardDao;
import com.hyk.code.modules.hyk.dao.HykOilManagerDao;
import com.hyk.code.modules.hyk.dao.HykOrderDao;
import com.hyk.code.modules.hyk.entity.HykInviter;
import com.hyk.code.modules.hyk.entity.HykOilCard;
import com.hyk.code.modules.hyk.entity.HykOilManager;
import com.hyk.code.modules.hyk.entity.HykOrder;
import com.hyk.code.modules.hyk.entity.vo.CompanyMarVo;
import com.hyk.code.modules.hyk.entity.vo.HykOrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单管理Service
 *
 * @author 霍中曦
 * @version 2018-11-09
 */
@Service
@Transactional(readOnly = true)
public class HykOrderService extends CrudService<HykOrderDao, HykOrder> {
    @Autowired
    private HykOrderDao hykOrderDao;
    @Autowired
    private HykOilCardDao hykOilCardDao;
    @Autowired
    private HykOilManagerDao hykOilManagerDao;

    public HykOrder get(String id) {
        return super.get(id);
    }

    public List<HykOrder> findList(HykOrder hykOrder) {
        return super.findList(hykOrder);
    }

    public Page<HykOrder> findPage(Page<HykOrder> page, HykOrder hykOrder) {
        return super.findPage(page, hykOrder);
    }

    @Transactional(readOnly = false)
    public void save(HykOrder hykOrder) {
        super.save(hykOrder);
    }

    @Transactional(readOnly = false)
    public void delete(HykOrder hykOrder) {
        super.delete(hykOrder);
    }

    /**
     * 首页已完成订单显示
     */
    public List<HykOrder> getSuccessAll() {
        return hykOrderDao.getSuccessAll();
    }

    /**
     * 根据订单号查询该订单
     */
    public Map queryByOrderNo(String orderNo) {
        return hykOrderDao.queryByOrderNo(orderNo);
    }

    /**
     * App版本相关信息
     *
     * @return
     */
    public Map getApp() {
        return hykOrderDao.getApp();
    }

    ;

    /**
     * 个人所有订单查询
     */
    public List<HykOrder> queryListByUserId(String userId, Integer currPage, Integer pageSize) {
        List<HykOrder> list = hykOrderDao.queryListByUserId(userId);
        if (list.size() < (currPage - 1) * pageSize) {
            return new ArrayList<>();
        }
        int firstIndex = (currPage - 1) * pageSize;//从第几条数据开始
        int lastIndex = currPage * pageSize;//到第几条数据结束
        if (lastIndex > list.size()) {
            lastIndex = list.size();
        }
        return list.subList(firstIndex, lastIndex); //直接在list中截取
    }

    ;/**
     * 添加充值订单
     * @param hykOrder
     * @return
     */
    @Transactional(readOnly = false)
    public Integer insertOrder(HykOrder hykOrder){
        return hykOrderDao.insertOrder(hykOrder);
    };


    /**
     * 功能描述: 邀请管理
     * @auther: 霍中曦
     * @date: 2018/11/23 10:06
     */
    public Page<CompanyMarVo> findcompanyBackMoneyPage(Page<CompanyMarVo> page, CompanyMarVo companyMarVo) {
        companyMarVo.setPage(page);
        page.setList(dao.findcompanyBackMoneyPage(companyMarVo));
        return page;
    }
    public List<HykInviter> findInviterExcelList(HykOrder hykOrder) {
        return dao.findInviterExcelList(hykOrder);
    }

    /**
     * 邀请人上月充值交易金额
     */
    public BigDecimal getUpMonthPayAmt(HykOrder hykOrder) {
        HykOrder hykOrder1=hykOrderDao.getUpMonthPayAmt(hykOrder);
        if(hykOrder1!=null){
            return hykOrder1.getPayableMoney();
        }else{
            return BigDecimal.ZERO;
        }

    }
    /**
     * 邀请人上月交易返现金额
     */
    public BigDecimal getUpMonthBackAmt(HykOrder hykOrder) {
        HykOrder hykOrder1=hykOrderDao.getUpMonthBackAmt(hykOrder);
        if(hykOrder1!=null){
            return hykOrder1.getBackMoney();
        }else{
            return BigDecimal.ZERO;
        }

    }

    /**
     * 邀请人本月交易金额
     */
    public BigDecimal getCurrentMonthAmt(HykOrder hykOrder) {
        HykOrder hykOrder1=hykOrderDao.getCurrentMonthAmt(hykOrder);
        if(hykOrder1!=null){
            return hykOrder1.getPayableMoney();
        }else{
            return BigDecimal.ZERO;
        }

    }

    /**
     * 邀请人本月返现金额
     */
    public BigDecimal getCurrentMonthBackAmt(HykOrder hykOrder) {
        HykOrder hykOrder1=hykOrderDao.getCurrentMonthBackAmt(hykOrder);
        if(hykOrder1!=null){
            return hykOrder1.getBackMoney();
        }else{
            return BigDecimal.ZERO;
        }
    }


    /**
     * 邀请人累计返现金额
     */
    public BigDecimal getTotalBackAmt(HykOrder hykOrder) {
        HykOrder hykOrder1=hykOrderDao.getTotalBackAmt(hykOrder);
        if(hykOrder1!=null){
            return hykOrder1.getBackMoney();
        }else{
            return BigDecimal.ZERO;
        }
    }
    /**
     * 邀请人累计交易金额
     */
    public BigDecimal getTotalPayAmt(HykOrder hykOrder) {
        HykOrder hykOrder1=hykOrderDao.getTotalPayAmt(hykOrder);
        if(hykOrder1!=null){
            return hykOrder1.getPayableMoney();
        }else{
            return BigDecimal.ZERO;
        }
    }


    /**
     * 功能描述: 返现管理
     * @auther: 霍中曦
     * @date: 2018/11/23 10:06
     */
    public Page<HykOrderDetailVo> findOrderBackMoneyDetailPage(Page<HykOrderDetailVo> page, HykOrderDetailVo hykOrderDetailVo) {
        hykOrderDetailVo.setPage(page);
        page.setList(dao.findOrderBackMoneyDetailPage(hykOrderDetailVo));
        return page;
    }
    /**
     * 功能描述: 返现管理第二查询
     * @auther: 霍中曦
     * @date: 2018/11/23 10:06
     */
    public Page<CompanyMarVo> findMonthReportPage(Page<CompanyMarVo> page, CompanyMarVo companyMarVo) {
        companyMarVo.setPage(page);
        page.setList(dao.findMonthReportPage(companyMarVo));
        return page;
    }

    /* 订单暂停操作
     * 功能描述:
     * @auther: 霍中曦
     * @date: 2019/3/11 9:28
     */
    @Transactional(readOnly = false)
    public  void updateStop(HykOrder hykOrder) {
        dao.updateStop(hykOrder);
        //加油计划同时暂停
        HykOilManager hykOilManager=new HykOilManager();
        hykOilManager.setOrderId(hykOrder.getId());
        hykOilManager.setDelFlag("2");
        hykOilManagerDao.updateStop(hykOilManager);
        updateOilMoney(hykOrder,hykOilManager);
    }

    /* 订单暂停操作
     * 功能描述:
     * @auther: 霍中曦
     * @date: 2019/3/11 9:28
     */
    @Transactional(readOnly = false)
    public void updateStart(HykOrder hykOrder) {
        dao.updateStart(hykOrder);
        //加油计划同时恢复
        HykOilManager hykOilManager=new HykOilManager();
        hykOilManager.setOrderId(hykOrder.getId());
        hykOilManagerDao.updateStart(hykOilManager);

        //添加累计充值金额 和累计余额
        updateOilMoney(hykOrder,hykOilManager);
    }

    /* 订单暂停操作
 * 功能描述:
 * @auther: 霍中曦
 * @date: 2019/3/11 9:28
 */
    @Transactional(readOnly = false)
    public void updateRefund(HykOrder hykOrder) {
        dao.updateRefund(hykOrder);
        //加油计划同时暂停
        HykOilManager hykOilManager=new HykOilManager();
        hykOilManager.setOrderId(hykOrder.getId());
        hykOilManager.setDelFlag("1");
        hykOilManagerDao.updateStop(hykOilManager);
        updateOilMoney(hykOrder,hykOilManager);
    }

    /*暂停 退款 恢复 返还金操作*/
    public void updateOilMoney(HykOrder hykOrder,HykOilManager hykOilManager){
        //获取对应的加油卡(可能为空)
        HykOilCard hykOilCard = hykOilCardDao.get(hykOrder.getCardId());
        if(hykOilCard!=null){
            HykOilManager conOilManager = new HykOilManager();
            conOilManager.setCardId(hykOrder.getCardId());
            List<HykOilManager> list = hykOilManagerDao.findList(conOilManager);

            //剩余待充金额
            BigDecimal waitMoney = BigDecimal.ZERO;
            //本月已到账金额
            BigDecimal endMoney = BigDecimal.ZERO;
            //本月应账金额
            BigDecimal ydzMoney = BigDecimal.ZERO;

            for (int i = 0; i < list.size(); i++) {

                if ("0".equals(list.get(i).getStatus())) {
                    waitMoney = waitMoney.add(list.get(i).getMoney());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                    Date plan = list.get(i).getPlanOilDate();
                    String planStr = sdf.format(plan);
                    String nowStr = sdf.format(new Date());
                    if (nowStr.equals(planStr)) {
                        ydzMoney = ydzMoney.add(list.get(i).getMoney());
                    }
                }

                if("1".equals(list.get(i).getStatus())){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                    Date plan = list.get(i).getPlanOilDate();
                    String planStr = sdf.format(plan);
                    String nowStr = sdf.format(new Date());
                    if (nowStr.equals(planStr)) {
                        endMoney = endMoney.add(list.get(i).getMoney());
                    }
                }

            }

            hykOilCard.setWaitMoney(waitMoney);
            hykOilCard.setSjdzMoney(endMoney);
            hykOilCard.setYdzMoney(ydzMoney);
            hykOilCardDao.update(hykOilCard);
        }
    }


}