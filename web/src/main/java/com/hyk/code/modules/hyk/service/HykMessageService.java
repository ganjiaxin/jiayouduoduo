/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.modules.hyk.dao.HykMessageDao;
import com.hyk.code.modules.hyk.entity.HykAd;
import com.hyk.code.modules.hyk.entity.HykMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 站内信管理Service
 *
 * @author 霍中曦
 * @version 2018-12-18
 */
@Service
@Transactional(readOnly = true)
public class HykMessageService extends CrudService<HykMessageDao, HykMessage> {
    @Autowired
    private HykMessageDao hykMessageDao;

    @Override
    public HykMessage get(String id) {
        return super.get(id);
    }

    @Override
    public List<HykMessage> findList(HykMessage hykMessage) {
        return super.findList(hykMessage);
    }

    @Override
    public Page<HykMessage> findPage(Page<HykMessage> page, HykMessage hykMessage) {
        return super.findPage(page, hykMessage);
    }

    @Transactional(readOnly = false)
    public void save(HykMessage hykMessage) {
        super.save(hykMessage);
    }

    @Transactional(readOnly = false)
    public void delete(HykMessage hykMessage) {
        super.delete(hykMessage);
    }


    /**
     * 注册站内信发送
     */
    @Transactional(readOnly = false)
    public Integer saveMessageForRegister(String phone) {
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("注册赠送红包");
        hykMessage.setContent("亲爱的用户，终于等到你，价值488元的新手注册红包已送达您的账户，感恩相聚！");
        hykMessage.setMessStatus("0");
        hykMessage.setType("1");
        hykMessage.setPhones(phone);
        hykMessage.setCreateDate(new Date());
        return hykMessageDao.insert(hykMessage);//发送站内信
    }


    /**
     * 邀请站内信发送
     *
     * @param phone           发送人
     * @param inviteUserPhone 邀请人手机号
     * @param money           金额
     * @param type            1添加加油卡使用 2充值套餐使用
     */
    @Transactional(readOnly = false)
    public Integer saveMessageForInvite(String phone, String inviteUserPhone, BigDecimal money, Integer type) {
        String ss =
                inviteUserPhone.substring(0, inviteUserPhone.length() - (inviteUserPhone.substring(2)).length()) +
                        "*******" + inviteUserPhone.substring(9);
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("邀请好友奖励");
        if (type == 1) {
            hykMessage.setContent("亲爱的用户，您邀请的用户" + ss + "用户添加油卡，" + money + "元奖励红包已到账，快去使用吧！");
        } else {
            hykMessage.setContent("亲爱的用户，您邀请的用户" + ss + "用户已充值油卡套餐，" + money + "元奖励红包已到账，快去使用吧！");
        }
        hykMessage.setMessStatus("0");
        hykMessage.setType("1");
        hykMessage.setPhones(phone);
        hykMessage.setCreateDate(new Date());
        return hykMessageDao.insert(hykMessage);//发送站内信
    }


    /**
     * 绑卡站内信发送
     *
     * @param phone           发送人
     * @param money           金额
     */
    @Transactional(readOnly = false)
    public Integer saveBingCard(String phone, BigDecimal money) {
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("绑定加油卡");
        hykMessage.setContent("亲爱的用户，您已经成功添加油卡，" + money + "元奖励红包已到账，快去使用吧！");
        hykMessage.setMessStatus("0");
        hykMessage.setType("1");
        hykMessage.setPhones(phone);
        hykMessage.setCreateDate(new Date());
        return hykMessageDao.insert(hykMessage);//发送站内信
    }

    /**
     * 更新状态为已读by  id
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public Integer updateStatusById(String id) {
        return hykMessageDao.updateStatusById(id);
    }

    /**
     * 更新状态为已读这个手机号的所有
     *
     * @param phone
     * @return
     */
    public Integer updateStatusByPhone(String phone) {
        return hykMessageDao.updateStatusByPhone(phone);
    }


    /**
     * 获取所有
     *
     * @param phone
     * @return
     */
    public List<HykMessage> getAll(String phone) {//, Integer currPage, Integer pageSize
        List<HykMessage> list = hykMessageDao.getAll(phone);
//        if (list.size() < (currPage - 1) * pageSize) {
//            return new ArrayList<>();
//        }
//        int firstIndex = (currPage - 1) * pageSize;//从第几条数据开始
//        int lastIndex = currPage * pageSize;//到第几条数据结束
//        if (lastIndex > list.size()) {
//            lastIndex = list.size();
//        }
        return list;//.subList(firstIndex, lastIndex); //直接在list中截取
    }


    /**
     * 获取最新标题
     *
     * @param phone
     * @return
     */
    public HykMessage getNewTitle(String phone) {
        return hykMessageDao.getNewTitle(phone);
    }

    /**
     * 获取未读站内信数量
     *
     * @param phone
     * @return
     */
    public Integer getUnreadCount(String phone) {
        return hykMessageDao.getUnreadCount(phone);
    }

    /**
     * 即时充值付款已绑卡加油站内信发送
     */
    @Transactional(readOnly = false)
    public Integer saveMessageForAddOilE(String phone, String cardNo, String cardType, String money, String month,
                                         String day) {
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("付款成功提醒");
        hykMessage.setContent("亲爱的用户！您尾号为" + cardNo + "的" + cardType + "加油卡于" + month + "月" + day + "日充值" + money +
                "元。充值金额将于1个工作日内到账。如需帮助请联系客服400*-***-***（工作时间：9:00-18:00）");

        hykMessage.setMessStatus("0");
        hykMessage.setType("0");
        hykMessage.setPhones(phone);
        hykMessage.setCreateDate(new Date());
        return hykMessageDao.insert(hykMessage);//发送站内信
    }

    /**
     * 即时充值付款已绑卡加油站内信发送
     */
    @Transactional(readOnly = false)
    public Integer saveMessageForAddOilA(String phone, String cardNo, String cardType, String money, String month,
                                         String day) {
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("付款成功提醒");
        hykMessage.setContent("亲爱的用户！您尾号为" + cardNo + "的" + cardType + "加油卡于" + month + "月" + day + "日充值" + money +
                "元。充值金额将在您成功绑定油卡后的1个工作日内到账，请及时绑定加油卡以便进行后续操作。如需帮助请联系客服****-***-***（工作时间：9:00-18:00）。");

        hykMessage.setMessStatus("0");
        hykMessage.setType("0");
        hykMessage.setPhones(phone);
        hykMessage.setCreateDate(new Date());
        return hykMessageDao.insert(hykMessage);//发送站内信
    }

    /**
     * 套餐充值付款已绑卡站内信发送
     */
    @Transactional(readOnly = false)
    public Integer saveMessageForAddOilF(String phone, String cardNo, String cardType, String cycle, String money,
                                         String month, String day) {
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("付款成功提醒");
        hykMessage.setContent("亲爱的用户！您尾号为" + cardNo + "的" + cardType + "加油卡于" + month + "月" + day + "日充值" + money +
                "元" + cycle + "期加油套餐。第一期套餐金额将于1个工作日内到账。如需帮助请联系客服****-***-***（工作时间：9:00-18:00）");

        hykMessage.setMessStatus("0");
        hykMessage.setType("0");
        hykMessage.setPhones(phone);
        hykMessage.setCreateDate(new Date());
        return hykMessageDao.insert(hykMessage);//发送站内信
    }

    /**
     * 套餐充值付款未绑卡站内信发送
     */
    @Transactional(readOnly = false)
    public Integer saveMessageForAddOilB(String phone, String cardNo, String cardType, String cycle, String money,
                                         String month, String day) {
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("付款成功提醒");
        hykMessage.setContent("亲爱的用户！您尾号为" + cardNo + "的" + cardType + "加油卡于" + month + "月" + day + "日充值" + money +
                "元" + cycle + "期加油套餐。第一期套餐金额将于您成功绑定油卡后的1个工作日内到账，请及时绑定加油卡以便进行后续操作。如需帮助请联系客服****-***-***（工作时间：9:00-18" +
                ":00）");
        hykMessage.setMessStatus("0");
        hykMessage.setType("0");
        hykMessage.setPhones(phone);
        hykMessage.setCreateDate(new Date());
        return hykMessageDao.insert(hykMessage);//发送站内信
    }
}