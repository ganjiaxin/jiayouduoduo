package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.common.utils.SmsUtils;
import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.modules.hyk.dao.HykGoodsDao;
import com.hyk.code.modules.hyk.dao.HykOilCardDao;
import com.hyk.code.modules.hyk.dao.HykOilManagerDao;
import com.hyk.code.modules.hyk.dao.HykOrderDao;
import com.hyk.code.modules.hyk.entity.HykGoods;
import com.hyk.code.modules.hyk.entity.HykOilCard;
import com.hyk.code.modules.hyk.entity.HykOilManager;
import com.hyk.code.modules.hyk.entity.HykOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 加油管理Service
 *
 * @author 霍中曦
 * @version 2018-11-09
 */
@Service
@Transactional(readOnly = true)
public class HykOilManagerService extends CrudService<HykOilManagerDao, HykOilManager> {
    @Autowired
    private HykOilManagerDao hykOilManagerDao;
    @Autowired
    private HykOrderDao hykOrderDao;
    @Autowired
    private HykOilCardDao hykOilCardDao;
    @Autowired
    private HykGoodsDao hykGoodsDao;
    @Autowired
    private HykMessageService hykMessageService;

    @Override
    public HykOilManager get(String id) {
        return super.get(id);
    }

    @Override
    public List<HykOilManager> findList(HykOilManager hykOilManager) {
        return super.findList(hykOilManager);
    }

    @Override
    public Page<HykOilManager> findPage(Page<HykOilManager> page, HykOilManager hykOilManager) {
        return super.findPage(page, hykOilManager);
    }

    @Transactional(readOnly = false)
    public void save(HykOilManager hykOilManager) {
        super.save(hykOilManager);
    }

    @Transactional(readOnly = false)
    public void updateStatus(HykOilManager hykOilManager) {
        dao.update(hykOilManager);

        //=============================更新订单====================================

        if (hykOilManager.getCycle().equals(hykOilManager.getPeriodsNum().toString())) {
            HykOrder order = hykOrderDao.get(hykOilManager.getOrderId());
            order.setOilStatus("1");
            hykOrderDao.update(order);
        }
        //=============================更新油卡====================================
        HykOilManager conOilManager = new HykOilManager();
        conOilManager.setCardId(hykOilManager.getCardId());
        List<HykOilManager> list = dao.findList(conOilManager);
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
            if ("1".equals(list.get(i).getStatus())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                Date plan = list.get(i).getPlanOilDate();
                String planStr = sdf.format(plan);
                String nowStr = sdf.format(new Date());
                if (nowStr.equals(planStr)) {
                    endMoney = endMoney.add(list.get(i).getMoney());
                }
            }
        }
        HykOilCard hykOilCard = hykOilCardDao.get(hykOilManager.getCardId());
        hykOilCard.setWaitMoney(waitMoney);
        hykOilCard.setSjdzMoney(endMoney);
        hykOilCard.setYdzMoney(ydzMoney);
        hykOilCardDao.update(hykOilCard);
        //=========================================================================
        //----------------------下面这段更新card表中money(累计加油金额)    及  不同情况的发短信
        HykOilManager hykOilManager1 = hykOilManagerDao.get(hykOilManager.getId());
        hykOilCardDao.updateMoney(hykOilManager1.getMoney(), hykOilManager1.getCardId());//更改card表中money(累计加油金额)
        String userPhone = hykOilCard.getHykUser().getPhone();
        HykGoods hykGoods = hykGoodsDao.get(hykOilManager1.getGoodId());

        msg(hykOilCard, userPhone, hykGoods, hykOilManager1);//发送短信站内信
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
    public List<HykOilManager> getAllByUserIdAndTimeAndStatus(String userId, String time, String status) {
        return hykOilManagerDao.getAllByUserIdAndTimeAndStatus(userId, time, status);
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
    public Integer insertOilManagerList(String orderNo) {
        List list = new ArrayList<>();
        HykOrder hykOrder = hykOrderDao.queryByOrder(orderNo);
        int cycle = Integer.parseInt(hykOrder.getCycel());
        for (int i = 0; i < cycle; i++) {
            HykOilManager hykOilManager = new HykOilManager();
            hykOilManager.setId(ZxingHandler.getUUID());
            hykOilManager.setOrderNo(orderNo);
            hykOilManager.setHykUser(hykOrder.getHykUser());
            hykOilManager.setPeriodsNum(Long.valueOf(cycle - i - 1));

            if (i == 0) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, 2);
                hykOilManager.setPlanOilDate(c.getTime());
            } else {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, i);
                hykOilManager.setPlanOilDate(c.getTime());
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
     * 恶心的短信  四个短信模板都有参与。。。 发完短信 还得来一条一模一杨的站内信。。。
     * 1 如果是套餐 1.1注册手机号和加油卡手机号相同  发送短信给给手机号  1.2二者不同 各发一个
     * 2 如果是即时 2.1注册手机号和加油卡手机号相同  发送短信给给手机号  2.2二者不同 各发一个
     */
    public void msg(HykOilCard hykOilCard, String userPhone, HykGoods hykGoods, HykOilManager hykOilManager1) {
//        Integer num = 0;
//        if (hykOilCard.getPhone().equals(userPhone)) {
//            num = 1;
//        }
        String oilCardNo = hykOilCard != null ?
                hykOilCard.getOliCardNo().substring(hykOilCard.getOliCardNo().length() - 4) : "****";
        String oilCardType = hykOilCard != null ? hykOilCard.getOilType().equals("1") ? "中石油" : "中石化" : "***";
        if (hykGoods.getGoodsType().equals("0")) {
            //套餐str.substring(str.length()-n)
            SmsUtils.packageMsgQuery(userPhone, oilCardNo, oilCardType, hykGoods.getCycle() + "",
                    hykOilManager1.getPeriodsNum() + "", hykOilManager1.getMoney() + "");
            hykMessageService.saveMessageForAddOilA(userPhone, oilCardNo, oilCardType, hykGoods.getCycle() + "",
                    hykOilManager1.getPeriodsNum() + "", hykOilManager1.getMoney() + "");
//            if (num == 0) {
//                SmsUtils.packageMsgQuery2(hykOilCard.getPhone(), userPhone.substring(7, 11), ZxingHandler
// .replaceNameX(hykOilCard.getName()), hykOilCard.getOliCardNo().substring(hykOilCard.getOliCardNo().length() - 4),
//                        hykOilCard.getOilType().equals("1") ? "中石油" : "中石化", hykGoods.getCycle() + "",
// hykOilManager1.getPeriodsNum() + "", hykOilManager1.getMoney() + "");
//                hykMessageService.saveMessageForAddOilB(hykOilCard.getPhone(), userPhone.substring(7, 11),
// ZxingHandler.replaceNameX(hykOilCard.getName()), hykOilCard.getOliCardNo().substring(hykOilCard.getOliCardNo()
// .length() - 4),
//                        hykOilCard.getOilType().equals("1") ? "中石油" : "中石化", hykGoods.getCycle() + "",
// hykOilManager1.getPeriodsNum() + "", hykOilManager1.getMoney() + "");
//            }
        } else {
            //即时
            SmsUtils.immediatelyMsgQuery(userPhone, oilCardNo, oilCardType, hykOilManager1.getMoney() + "");
            hykMessageService.saveMessageForAddOilC(userPhone, oilCardNo, oilCardType, hykOilManager1.getMoney() + "");
//            if (num == 0) {
//                SmsUtils.immediatelyMsgQuery2(hykOilCard.getPhone(), userPhone.substring(7, 11), ZxingHandler
// .replaceNameX(hykOilCard.getName()), hykOilCard.getOliCardNo().substring(hykOilCard.getOliCardNo().length() - 4),
//                        hykOilCard.getOilType().equals("1") ? "中石油" : "中石化", hykOilManager1.getMoney() + "");
//
//            }
        }
    }

}