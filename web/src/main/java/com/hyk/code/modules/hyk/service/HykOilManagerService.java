package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.Constant;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.common.utils.SmsUtils;
import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.modules.hyk.dao.*;
import com.hyk.code.modules.hyk.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @Description: 加油管理Service
 * @Author: 甘佳欣
 * @Date: 2019/4/19
 */
@Service
@Transactional(readOnly = true)
public class HykOilManagerService extends CrudService<HykOilManagerDao, HykOilManager> {
    @Autowired
    private HykOilManagerDao hykOilManagerDao;
    @Autowired
    private HykOrderDao hykOrderDao;
    @Autowired
    private HykGoodsDao hykGoodsDao;
    @Autowired
    private HykRedpackageDao hykRedpackageDao;
    @Autowired
    private HykUserDao hykUserDao;
    @Autowired
    private HykOilCardDao hykOilCardDao;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private HykMessageService hykMessageService;
    @Autowired
    private HykHolidaysService hykHolidaysService;

    public HykOilManager get(String id) {
        return super.get(id);
    }

    public List<HykOilManager> findList(HykOilManager hykOilManager) {
        return super.findList(hykOilManager);
    }

    public Page<HykOilManager> findPage(Page<HykOilManager> page, HykOilManager hykOilManager) {
        return super.findPage(page, hykOilManager);
    }

    @Transactional(readOnly = false)
    public void save(HykOilManager hykOilManager) {
        super.save(hykOilManager);
    }

    @Transactional(readOnly = false)
    public void delete(HykOilManager hykOilManager) {
        super.delete(hykOilManager);
    }

    /**
     * 根据加油卡号和用户id查询本月应加油金额
     *
     * @param userId 用户id
     * @param cardNo 加油卡号
     * @return
     */
    public Double getMoeyByCardNoAndUserId(String userId, String cardNo, String status) {
        return hykOilManagerDao.getMoeyByCardNoAndUserId(userId, cardNo, status);
    }


    /**
     * 我的->加油计划
     *
     * @param userId    用户id
     * @param yearMonth 格式：201811
     * @param status    加油计划状态 0待加油 1已加油
     * @return
     */
    public Double getMoneyByUserIdAndTime(String userId, String yearMonth, String status) {
        return hykOilManagerDao.getMoneyByUserIdAndTime(userId, yearMonth, status);
    }

    /**
     * 我的->加油计划  待加油已加油金额展示
     *
     * @param userId 用户id
     * @param status 加油计划状态 0待加油 1已加油
     * @return
     */
    public Double getMoneyByUserIdAndTimeAndStatus(String userId, String time, String status) {
        return hykOilManagerDao.getMoneyByUserIdAndTimeAndStatus(userId, time, status);
    }

    ;


    /**
     * 我的->加油计划  具体信息展示
     *
     * @param userId    用户id
     * @param yearMonth 格式：201811
     * @param status    加油计划状态 0待加油 1已加油
     * @return
     */
    public List getListByUserIdAndTimeAndStatus(String userId, String yearMonth, String status) {
        return hykOilManagerDao.getListByUserIdAndTimeAndStatus(userId, yearMonth, status);
    }

    ;


    /**
     * 个人所有加油计划展示
     *
     * @param userId
     * @return
     */
    public List<HykOilManager> getAllByUserId(String userId) {
        return hykOilManagerDao.getAllByUserId(userId);
    }

    ;


    /**
     * 个人所有加油计划展示 可以根据时间查询
     *
     * @param userId
     * @param time   时间  格式 201811
     * @return
     */
    public List<HykOilManager> getAllByUserIdAndTimeAndStatus(String userId, String time, String day, String status) {
        return hykOilManagerDao.getAllByUserIdAndTimeAndStatus(userId, time, day, status);
    }

    ;

    /**
     * 查询该油卡id 未被充值的加油计划记录数
     */
    public Integer queryCountByCardId(String cardId) {
        return hykOilManagerDao.queryCountByCardId(cardId);
    }

    ;


    /**
     * 添加加油计划
     *
     * @param
     * @return
     */
    @Transactional(readOnly = false)
    public Integer insertOilManagerList(HykOrder hykOrder) {
        List list = new ArrayList<>();
        HykGoods hykGoods = hykGoodsDao.get(hykOrder.getGoodsId());
        HykUser hykUser = new HykUser();
        hykUser.setId(hykOrder.getUserId());
        int cycle = hykGoods.getCycle().intValue();

        for (int i = 0; i < cycle; i++) {
            HykOilManager hykOilManager = new HykOilManager();
            hykOilManager.setId(ZxingHandler.getUUID());
            hykOilManager.setOrderNo(hykOrder.getOrderNo());
            hykOilManager.setHykUser(hykUser);
            hykOilManager.setPeriodsNum(i + 1L);
            Calendar c = Calendar.getInstance();
            if (i == 0) {
                c.add(Calendar.DAY_OF_MONTH, 1);
                hykOilManager.setPlanOilDate(hykHolidaysService.getHolidaysByDate(c.getTime()));
            } else {
                c.add(Calendar.MONTH, i);
                hykOilManager.setPlanOilDate(hykHolidaysService.getHolidaysByDate(c.getTime()));
            }

            hykOilManager.setMoney(hykOrder.getEveryAmt());
            hykOilManager.setStatus("0");
            hykOilManager.setCreateDate(new Date());
            hykOilManager.setGoodId(hykOrder.getGoodsId());
            hykOilManager.setOrderId(hykOrder.getId());
            hykOilManager.setCardId(hykOrder.getCardId());
            list.add(hykOilManager);
        }
        return hykOilManagerDao.insertOilManagerList(list);
    }


    /**
     * 付款成功要做的事 : 1.根据订单号生成加油计划
     * 2.更改掉订单表中的付款时间，付款方式，付款状态
     * 3.更新user中waitAmt字段
     * 4.如果订单使用了红包，更改红包状态
     * 5.更新应到帐金额
     *
     * @param orderNo
     * @param payType
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public String updateOrderStatus(String orderNo, String payType) {//payType 2组合/支付宝  3 组合/快捷  6余额支付
        String msg = "false";
        try {
            HykOrder hykOrder = hykOrderDao.findByOrderNo(orderNo);
            //防止重复回调
            if (Constant.ORDER_STATUS_ZERO.equals(hykOrder.getOrderStatus())) {
                //生成加油计划
                this.insertOilManagerList(hykOrder);
                // 更改order状态 我这里随便写的payType  记得根据付款方式更改掉  1. 2. 3. 4
                hykOrderDao.updatePayTypeByOrderNo(payType, orderNo);

                BigDecimal cycle = new BigDecimal(hykGoodsDao.get(hykOrder.getGoodsId()).getCycle());
                BigDecimal waitMoney = hykOrder.getEveryAmt().multiply(cycle);
                //更新user中waitAmt字段
                hykUserDao.updateWaitAmt(waitMoney, hykOrder.getUserId());

                if (hykOrder.getRedpackageId() != null) {
                    //如果使用了红包 更改红包状态
                    hykRedpackageDao.updateUseTime(hykOrder.getRedpackageId());
                }

                //付款成功更新应到账金额等
                hykOilCardDao.updateYdzMoney(hykOrder.getCardId(), waitMoney);

                //----------------------------------里面是拉新活动-------------------------------------------------------
                activityService.inviteFriendActivityB(hykOrder.getUserId());
                //----------------------------------里面是拉新活动-------------------------------------------------------

                //充值成功发短信
                sendMessageByType(hykOrder, Integer.valueOf(cycle.toString()));
            }
            msg = "true";
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.info("付款成功生成计划异常");
        }
        return msg;
    }


    /**
     * 支付失败做的事
     * 1检查该订单用没用过红包 并恢复
     * 2检查该订单用没用过余额 并恢复
     * 3更新订单状态为 4 已失效
     *
     * @param orderNo
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public String updateOrderStatus2(String orderNo) {
        String msg = "false";
        try {
            HykOrder hykOrder = hykOrderDao.findByOrderNo(orderNo);
            if (hykOrder.getRedpackageId() != null) {
                Integer zzz = hykRedpackageDao.updateStatusById(Constant.REDPACKAGE_STATUS_ZERO,
                        hykOrder.getRedpackageId());
            }
            if (hykOrder.getBalancePayment().compareTo(BigDecimal.ZERO) > 0) {
                Integer xxx = hykUserDao.updateAccountBalance2(hykOrder.getBalancePayment(),
                        hykOrder.getHykUser().getId());
            }
            hykOrderDao.updateFailStatus(orderNo);
            msg = "true";
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.info("付款失败恢复数据异常");
        }
        return msg;
    }

    /**
     * 查询个人未加油加油计划
     *
     * @param userId
     * @return
     */
    public Integer getCountOilManagerByUserId(String userId) {
        return hykOilManagerDao.getCountOilManagerByUserId(userId);
    }

    ;

    /**
     * 加油付款成功
     *
     * @return
     */
    public void sendMessageByType(HykOrder hykOrder, Integer cycle) {
        HykOilCard hykOilCard = hykOilCardDao.get(hykOrder.getCardId());//cardno cardtype
        String phone = hykOrder.getUserPhone();
        String oilCardNo = hykOilCard != null ?
                hykOilCard.getOliCardNo().substring(hykOilCard.getOliCardNo().length() - 4) : "****";
        String oilCardType = hykOilCard != null ? hykOilCard.getOilType().equals("1") ? "中石油" : "中石化" : "***";

        if (hykOrder.getOrderType().equals("0")) {//即时
            if (hykOilCard != null) {//已绑卡
                hykMessageService.saveMessageForAddOilE(phone, oilCardNo, oilCardType,
                        hykOrder.getEveryAmt().toString(), DateUtils.getMonth(), DateUtils.getDay());
                SmsUtils.packagePayMsg1(phone, oilCardNo, oilCardType, hykOrder.getEveryAmt().toString(),
                        DateUtils.getMonth(), DateUtils.getDay());
            } else {//未绑卡
                hykMessageService.saveMessageForAddOilA(phone, oilCardNo, oilCardType,
                        hykOrder.getEveryAmt().toString(), DateUtils.getMonth(), DateUtils.getDay());
                SmsUtils.packagePayMsg3(phone, oilCardNo, oilCardType, hykOrder.getEveryAmt().toString(),
                        DateUtils.getMonth(), DateUtils.getDay());
            }
        } else {//套餐
            if (hykOilCard != null) {//已绑卡
                hykMessageService.saveMessageForAddOilF(phone, oilCardNo, oilCardType, cycle.toString(),
                        hykOrder.getEveryAmt().toString(), DateUtils.getMonth(), DateUtils.getDay());
                SmsUtils.packagePayMsg2(phone, oilCardNo, oilCardType, cycle.toString(),
                        hykOrder.getEveryAmt().toString(), DateUtils.getMonth(), DateUtils.getDay());
            } else {//未绑卡
                hykMessageService.saveMessageForAddOilB(phone, oilCardNo, oilCardType, cycle.toString(),
                        hykOrder.getEveryAmt().toString(), DateUtils.getMonth(), DateUtils.getDay());
                SmsUtils.packagePayMsg4(phone, oilCardNo, oilCardType, cycle.toString(),
                        hykOrder.getEveryAmt().toString(), DateUtils.getMonth(), DateUtils.getDay());
            }
        }


    }


    public List<String> getTimeGroupByUserId(String userId, String time) {
        return hykOilManagerDao.getTimeGroupByUserId(userId, time);
    }

    ;//获得当月该用户有加油计划的日期

    public List<HykOilManager> getAllByUserIdAndTimeAndStatusNew(String userId, String time) {
        return hykOilManagerDao.getAllByUserIdAndTimeAndStatusNew(userId, time);
    }

    ;//获得该用户当日的所有加油计划

}