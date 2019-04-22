/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.common.utils.SmsUtils;
import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.modules.hyk.dao.HykMallGoodsDao;
import com.hyk.code.modules.hyk.dao.HykMallOrderDao;
import com.hyk.code.modules.hyk.dao.HykMessageDao;
import com.hyk.code.modules.hyk.dao.HykRechargeCardDao;
import com.hyk.code.modules.hyk.entity.HykMallGoods;
import com.hyk.code.modules.hyk.entity.HykMallOrder;
import com.hyk.code.modules.hyk.entity.HykMessage;
import com.hyk.code.modules.hyk.entity.HykUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商城商品订单Service
 *
 * @author wyy
 * @version 2018-12-21
 */
@Service
@Transactional(readOnly = true)
public class HykMallOrderService extends CrudService<HykMallOrderDao, HykMallOrder> {
    @Autowired
    private HykMallOrderDao hykMallOrderDao;
    @Autowired
    private HykMallGoodsDao hykMallGoodsDao;
    @Autowired
    private HykRechargeCardDao hykRechargeCardDao;
    @Autowired
    private HykMessageDao hykMessageDao;

    public HykMallOrder get(String id) {
        return super.get(id);
    }

    public List<HykMallOrder> findList(HykMallOrder hykMallOrder) {
        return super.findList(hykMallOrder);
    }

    public Page<HykMallOrder> findPage(Page<HykMallOrder> page, HykMallOrder hykMallOrder) {
        return super.findPage(page, hykMallOrder);
    }

    @Transactional(readOnly = false)
    public void save(HykMallOrder hykMallOrder) {
        super.save(hykMallOrder);
    }

    @Transactional(readOnly = false)
    public void delete(HykMallOrder hykMallOrder) {
        super.delete(hykMallOrder);
    }


    /**
     * //正常情况下订单
     * 1 修改商品库存
     * 2 添加订单信息
     *
     * @param hykMallGoods
     * @param num
     */
    @Transactional(readOnly = false)
    public String downOrder(HykUser hykUser, HykMallGoods hykMallGoods, Integer num, String addressId, String remark) {
        hykMallGoodsDao.updateStockById(num, hykMallGoods.getId());
        String str = ZxingHandler.getUUID();
        BigDecimal freight = hykMallGoods.getFreight();
        if (freight == null) {
            freight = BigDecimal.ZERO;
        }
        hykMallOrderDao.insert2(str, ZxingHandler.getShopOrderStr(hykUser.getId()), hykUser.getId(),
                hykUser.getPhone(), hykMallGoods.getId(),
                hykMallGoods.getGoodsName(), hykMallGoods.getGoodsType(), Integer.parseInt(num + ""),
                (hykMallGoods.getPrices().multiply(new BigDecimal(num))).add(freight),
                new Date(), "0", "0", addressId, remark);//不知道为什么用crud里的insert添加对象就所有属性getter不到
        return str;

    }

    /**
     * 定时任务
     *
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public Integer updateStatus() {
        try {
            return hykMallOrderDao.updateOverTimeMallOrder();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }

    /**
     * //更新实物商品为已签收
     *
     * @param orderno
     * @return
     */
    @Transactional(readOnly = false)
    public Integer updateupdateSendStatusOver(String orderno) {
        return hykMallOrderDao.updateupdateSendStatusOver(orderno);
    }

    /**
     * 查询所有商城订单
     *
     * @param
     */
    @Transactional(readOnly = false)
    public List allOrder(String userId, String sendStatus, Integer currPage, Integer pageSize) {
        Integer num = hykMallOrderDao.updateOverTimeMallOrderByUserId(userId);//先更新已过期的

        List list = hykMallOrderDao.getListByUserId(userId, sendStatus);
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

    @Transactional(readOnly = false)//更新statusBy订单号 失败
    public Integer updateFailStatusByOrderno(String status, String orderno) {
        return hykMallOrderDao.updateFailStatusByOrderno(status, orderno);
    }

    ;

    /**
     * 进入方法判断订单状态 如果状态是2无动作  //防止支付方连续回调
     * 0.5 更新statusBy订单号 成功
     * 1 修改商城订单发送状态
     * 1.5 更新查询出来的充值卡的售卖状态
     * 2 发送站内信
     * 3 发送短信
     * <p>
     * 进入方法查看该商品 如果该商品是虚拟商品 修改汇优订单状态  查出几条可售卖的余额卡并修改为已售出带兑换 发送站内信 短信
     * 如果是实物商品啥也不做 等后台更新
     *
     * @param orderno 用到手机号 金额 数量 订单号
     */
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public Integer updateSuccessStatusByOrder(String status, String orderno, String payType) {
        try {
            //根据订单号获取订单对象
            HykMallOrder hykMallOrder = hykMallOrderDao.getByOrderno(orderno);
            if ("0".equals(hykMallOrder.getStatus())) {
                hykMallOrderDao.updateSuccessStatusByOrder(status, orderno, payType);//0.5
                HykMallGoods hykMallGoods = hykMallGoodsDao.get(hykMallOrder.getGoodsId());
                //虚拟商品操作
                if (hykMallGoods.getGoodsType().equals("0")) {
                    //修改订单状态为已发送
                    hykMallOrderDao.updateSendStatusByOrderno(hykMallOrder.getOrderno());//1
                    List<Map<String, Object>> list = hykRechargeCardDao.selectNoPassword(hykMallGoods.getMoney(),
                            hykMallOrder.getNum());
                    for (int i = 0; i < list.size(); i++) {
                        String id = list.get(i).get("id").toString();
                        System.out.println("============" + Integer.valueOf(list.get(i).get("days").toString()));
                        Date overDate = DateUtils.addDay(new Date(),
                                Integer.valueOf(list.get(i).get("days").toString()));

                        hykRechargeCardDao.updateSaleStatusById2(id, overDate);
                    }
                    Integer ccc = saveMessageForRegister(hykMallOrder.getPhone(), hykMallOrder.getGoodsName(),
                            hykMallOrder.getNum(), list);//2
                    SmsUtils.balanceMessage(hykMallOrder.getPhone(), hykMallOrder.getGoodsName(),
                            hykMallOrder.getNum(), list);
                //实物商品操作
                } else {
                    Integer ccc = saveMessageForRealBuySuccess(hykMallOrder.getPhone(), hykMallOrder.getGoodsName());
                    SmsUtils.realGoodsBuySuccess(hykMallOrder.getPhone(), hykMallOrder.getGoodsName());

                }
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.info("付款成功生成计划异常");
            return 0;
        }
    }

    ;

//    /**
//     * 1 修改商城订单发送状态
//     * 1.5 更新查询出来的充值卡的售卖状态
//     * 2 发送站内信
//     * 3 发送短信
//     * <p>
//     * 进入方法查看该商品 如果该商品是虚拟商品 修改汇优订单状态  查出几条可售卖的余额卡并修改为已售出带兑换 发送站内信 短信
//     *   如果是实物商品啥也不做 等后台更新
//     *
//     * @param orderno 用到手机号 金额 数量 订单号
//     */
//    @Transactional(readOnly = false)
//    public void updateSendStatus(String orderno) {
//        HykMallOrder hykMallOrder = hykMallOrderDao.getByOrderno(orderno);//根据订单号获取订单对象
//        HykMallGoods hykMallGoods = hykMallGoodsDao.get(hykMallOrder.getGoodsId());
//        if (hykMallGoods.getGoodsType().equals("0")) {
//            Integer zzz = hykMallOrderDao.updateSendStatusByOrderno(hykMallOrder.getOrderno());//1
//            List<Map<String, Object>> list = hykRechargeCardDao.selectNoPassword(hykMallGoods.getMoney(),
// hykMallOrder.getNum());
//            for (int i = 0; i < list.size(); i++) {
//                String id = list.get(i).get("id").toString();
//                System.out.println("============"+Integer.valueOf(list.get(i).get("days").toString()));
//                Date overDate = DateUtils.addDay(new Date(),Integer.valueOf(list.get(i).get("days").toString()));
//
//                Integer xxx = hykRechargeCardDao.updateSaleStatusById2(id, overDate);
//            }
//            Integer ccc = saveMessageForRegister(hykMallOrder.getPhone(), hykMallOrder.getGoodsName(), hykMallOrder
// .getNum(), list);//2
//            SmsUtils.balanceMessage(hykMallOrder.getPhone(), hykMallOrder.getGoodsName(), hykMallOrder.getNum(),
// list);
//        }
//    }


    /**
     * 虚拟商品余额充值卡购买成功发送站内信
     *
     * @param phone
     * @param goodsName
     * @param num
     * @param list
     * @return
     */
    public Integer saveMessageForRegister(String phone, String goodsName, Integer num, List<Map<String, Object>> list) {
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("充值卡已送达");//回来问一下
        //【油大大】亲爱的用户您好，您购买的商品 #goodsName#，数量为#num#;#txt#请及时兑换，祝您生活愉快。
        hykMessage.setContent("亲爱的用户您好，您购买的商品 " + goodsName + "，数量为" + num + "；" + SmsUtils.createTxt2(list) +
                "请注意查收短信并及时兑换，祝您生活愉快。");
        hykMessage.setMessStatus("0");
        hykMessage.setPhones(phone);
        hykMessage.setCreateDate(new Date());
        return hykMessageDao.insert(hykMessage);//发送站内信
    }

    /**
     * 实物商品购买成功发送站内信
     *
     * @param phone
     * @param goodsName
     * @return
     */
    public Integer saveMessageForRealBuySuccess(String phone, String goodsName) {
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("购买成功");
        hykMessage.setContent("亲爱的用户，感谢您使用加油多多APP，您已成功购买" + goodsName + "商品，我们将尽快为您发货，祝您使用愉快。");
        hykMessage.setMessStatus("0");
        hykMessage.setPhones(phone);
        hykMessage.setCreateDate(new Date());
        return hykMessageDao.insert(hykMessage);//发送站内信
    }


    /**
     * //获取该用户今日购买该商品几个
     *
     * @param goodsName
     * @param userId
     * @return
     */
    public Integer getCountBuyOilCardByTime(String goodsName, String userId) {
        return hykMallOrderDao.getCountBuyOilCardByTime(goodsName, userId);
    }

    ;

    /**
     * //获取该用户共购买该商品数量
     *
     * @param goodsName
     * @param userId
     * @return
     */
    public Integer getCountBuyOilCard(String goodsName, String userId) {
        return hykMallOrderDao.getCountBuyOilCard(goodsName, userId);
    }

    ;


    /**
     * 定时任务
     * //定时删除超过指定日期的商城订单
     *
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public Integer softDeleteMallOrder() {
        try {
            return hykMallOrderDao.softDeleteMallOrder();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }
}