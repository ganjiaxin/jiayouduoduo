/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.modules.hyk.dao.HykOilCardDao;
import com.hyk.code.modules.hyk.dao.HykOilManagerDao;
import com.hyk.code.modules.hyk.dao.HykOrderDao;
import com.hyk.code.modules.hyk.dao.HykUserDao;
import com.hyk.code.modules.hyk.entity.HykOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private HykOilManagerDao hykOilManagerDao;
    @Autowired
    private HykUserDao hykUserDao;
    @Autowired
    private HykOilCardDao hykOilCardDao;
    @Autowired
    private HykOilManagerService hykOilManagerService;

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


    public HykOrder findByOrderNo(String orderNo) {
        return hykOrderDao.findByOrderNo(orderNo);
    }

    ;

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
    public Integer queryCountByOrderNo(String orderNo){
        return hykOrderDao.queryCountByOrderNo(orderNo);
    };//该订单数量

    /**
     * 付款成功后更改订单信息
     *
     * @param payType
     * @param orderNo
     * @return
     */
    @Transactional(readOnly = false)
    public Integer updatePayTypeByOrderNo(String payType, String orderNo) {
        return hykOrderDao.updatePayTypeByOrderNo(payType, orderNo);
    }


    /**
     * App版本相关信息
     *
     * @return
     */
    public Map getApp(String id) {
        return hykOrderDao.getApp(id);
    }

    ;

    /**
     * 个人所有订单查询
     */
    public List<HykOrder> queryListByUserId(String userId,String oilStatus, Integer currPage, Integer pageSize) {
        List<HykOrder> list = hykOrderDao.queryListByUserId(userId,oilStatus);
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

    ;

    /**
     * 添加充值订单
     *
     * @param hykOrder
     * @return
     */
    @Transactional(readOnly = false)
    public Integer insertOrder(HykOrder hykOrder) {
        return hykOrderDao.insertOrder(hykOrder);
    }

    ;


    /**
     * 功能描述: 邀请管理
     *
     * @auther: 霍中曦
     * @date: 2018/11/23 10:06
     */
    public Page<HykOrder> findInviterPage(Page<HykOrder> page, HykOrder hykOrder) {
        hykOrder.setPage(page);
        page.setList(dao.findInviterList(hykOrder));
        return page;
    }

    /**
     * 邀请人上月交易金额
     */
    public List<HykOrder> getUpMonthAmt(HykOrder hykOrder) {
        return hykOrderDao.getUpMonthAmt(hykOrder);
    }

    /**
     * 邀请人本月交易金额
     */
    public List<HykOrder> getCurrentMonthAmt(HykOrder hykOrder) {
        return hykOrderDao.getCurrentMonthAmt(hykOrder);
    }

    /**
     * 定时任务
     *
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public Integer updateStatus1() {
        try {
            return hykOrderDao.updateStatus1();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }

    /**
     * 定时任务
     *
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public Integer updateStatus2() {
        try {
            return hykOrderDao.updateStatus2();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }


    @Transactional(readOnly = false)
    public Integer updateStatusByUserId1(String userId) {
        return hykOrderDao.updateStatusByUserId1(userId);
    }

    ;

    @Transactional(readOnly = false)
    public Integer updateStatusByUserId2(String userId) {
        return hykOrderDao.updateStatusByUserId2(userId);
    }

    ;

    public List<HykOrder> queryInviterBackMoney(HykOrder hykOrder) {
        return hykOrderDao.queryInviterBackMoney(hykOrder);
    }

    /**
     * 功能描述:邀请用户返现总金额
     *
     * @auther: 霍中曦
     * @date: 2018/12/17 17:03
     */
    public HykOrder sumBackMoney(HykOrder hykOrder) {
        return hykOrderDao.sumBackMoney(hykOrder);
    }

    /**
     * 功能描述:查询返现月份
     *
     * @auther: 霍中曦
     * @date: 2018/12/17 17:06
     */
    public List<HykOrder> monthList(HykOrder hykOrder) {
        return hykOrderDao.monthList(hykOrder);
    }

    /**
     * 处理先有加油计划后绑卡方法
     * 在绑卡成功时判断是否有NullCardIdAndNullCardNo的订单
     * 有 更新order表这两个字段 同时更新加油计划中该字段
     * 没有 ...
     *
     * @return
     */
    @Transactional(readOnly = false)
    public boolean orderFun(String cardId, String cardNo, String userId) {
        boolean b = false;
        List list = hykOrderDao.getListByUserIdAndNullCardIdAndNullCardNo(userId);
        if (list.size() > 0) {//有为空的订单
            Integer num = hykOrderDao.updateCardIdAndCardNo(cardId, cardNo, userId);//更新订单
            Integer num1 = hykOilManagerDao.updateCardId(cardId, userId);
            Double userReadyMoney = hykOilManagerService.getMoneyByUserIdAndTime(userId, DateUtils.getYear() + DateUtils.getMonth(), "0");
            Integer num2 = hykOilCardDao.updateYdzmoneyByCardId(userReadyMoney, cardId);
            b = true;
        }
        return b;
    }


    /**
     * 组合支付应该做的事  更新订单信息 更新user表余额
     *
     * @param balanceMoney 用户余额支付的金额
     * @return
     */
    @Transactional(readOnly = false)
    public void orderFun2(HykOrder hykOrder, BigDecimal balanceMoney) {
        Integer zzz = hykOrderDao.updatePayableMoneyAndBalanceMoney(balanceMoney, hykOrder.getId());//更新订单信息
        Integer xxx = hykUserDao.updateAccountBalance(balanceMoney, hykOrder.getHykUser().getId());//更新user表余额
    }
    //1.2需求-->按卡id查已付款订单 加油状态付款时间排序
    public List<HykOrder> getListByCardId(String cardId, Integer currPage, Integer pageSize) {
        List<HykOrder> list = hykOrderDao.getListByCardId(cardId);
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

    ;
    /**
     * 定时任务
     *          定时删除超过指定日期的加油订单
     *
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public Integer softDeleteOrder() {
        try {
            return hykOrderDao.softDeleteOrder();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }
    @Transactional(readOnly = false)
    public Integer updateDelFlag(String id){
        return hykOrderDao.updateDelFlag(id);
    };//更新delflag为0

    public List<HykOrder> getSuccessCountByDate(String userId,String goodsId,String everyAmt ,String startTime,String endTime){
        return hykOrderDao.getSuccessCountByDate(userId,goodsId,everyAmt,startTime,endTime);
    };//获取指定时间段内成功付款且每期金额大于everyAmt的该订单；

    public Integer queryReadyOrderCount(String cardId){
        return hykOrderDao.queryReadyOrderCount(cardId);
    };//查询代付款订单数量
}