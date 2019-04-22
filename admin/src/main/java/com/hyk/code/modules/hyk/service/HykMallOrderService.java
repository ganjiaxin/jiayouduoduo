/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.common.utils.SmsUtils;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.modules.hyk.dao.*;
import com.hyk.code.modules.hyk.entity.HykMallOrder;
import com.hyk.code.modules.hyk.entity.HykMessage;
import com.hyk.code.modules.hyk.entity.HykOilCard;
import com.hyk.code.modules.hyk.entity.HykUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    private HykMessageDao hykMessageDao;
    @Autowired
    private HykRechargeCardDao hykRechargeCardDao;
    @Autowired
    private HykMallGoodsDao hykMallGoodsDao;
    @Autowired
    private HykOilCardDao hykOilCardDao;

    public HykMallOrder get(String id) {
        return super.get(id);
    }

    public List<HykMallOrder> findList(HykMallOrder hykMallOrder) {
        return super.findList(hykMallOrder);
    }

    public Page<HykMallOrder> findPage(Page<HykMallOrder> page, HykMallOrder hykMallOrder) {
        return super.findPage(page, hykMallOrder);
    }
    public Page<HykMallOrder> findPage2(Page<HykMallOrder> page, HykMallOrder hykMallOrder) {
        hykMallOrder.setPage(page);
        page.setList(dao.findList2(hykMallOrder));
        return page;
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
     * 1 修改商城订单发送状态
     * 1.5 更新查询出来的充值卡的售卖状态
     * 2 发送站内信
     * 3 发送短信
     */
    @Transactional(readOnly = false)
    public boolean updateSendStatus(HykMallOrder hykMallOrder,String cardNo) throws Exception{
        hykMallOrderDao.update(hykMallOrder);
        if(StringUtils.isNotBlank(cardNo)){
            if("中石化加油卡".equals(hykMallOrder.getGoodsName())||"中石油加油卡".equals(hykMallOrder.getGoodsName())){
                HykOilCard card=new HykOilCard();
                HykUser hykUser=new HykUser();
                hykUser.setId(hykMallOrder.getUserId());

                card.setOliCardNo(cardNo);
                card.setHykUser(hykUser);
                card.setName("HYK"+hykMallOrder.getAddressName());
                card.setPhone(hykMallOrder.getAddressPhone());
                if("中石化加油卡".equals(hykMallOrder.getGoodsName())){
                    card.setOilType("1");//中石油
                }else{
                    card.setOilType("2");//中石化
                }
                card.setMoney(BigDecimal.ZERO);
                card.setWaitMoney(BigDecimal.ZERO);
                card.setRemark("平台领取 自动绑定");
                card.setDelFlag("0");
                card.setYdzMoney(BigDecimal.ZERO);
                card.setSjdzMoney(BigDecimal.ZERO);
                card.preInsert();
                hykOilCardDao.insert(card);
            }
        }
        return saveMessageForRegister(hykMallOrder);
    }

/**
 * 功能描述:站内信通知 未使用
 * @auther: 霍中曦
 * @param:
 * @return: 
 * @date: 2018/12/26 13:39
 */
    public boolean saveMessageForRegister(HykMallOrder hykMallOrder)throws Exception{
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("发货通知");
        //【油大大】亲爱的用户您好，您购买的商品 #goodsName#，数量为#num#;#txt#请及时兑换，祝您生活愉快。
        SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日");
        String payDate=sdf.format(hykMallOrder.getPayDate());
        hykMessage.setContent("亲爱的惠友，您于"+payDate+"在油大大APP购买的"+hykMallOrder.getGoodsName()+"商品现已发货，请前往油大大APP，点击我的订单查看商品物流信息，祝您使用愉快。");
        hykMessage.setMessStatus("1");
        hykMessage.setPhones(hykMallOrder.getPhone());
        hykMessage.setCreateDate(new Date());
        hykMessageDao.insert(hykMessage);//发送站内信

        //短信
        boolean status=SmsUtils.sendGoodsMess(hykMallOrder.getPhone(),payDate,hykMallOrder.getGoodsName());

        return status;
    }
    public List<HykMallOrder> findList2(HykMallOrder hykMallOrder) {
        return dao.findList2(hykMallOrder);
    }


}