/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.modules.hyk.dao.HykMessageDao;
import com.hyk.code.modules.hyk.entity.HykMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public HykMessage get(String id) {
        return super.get(id);
    }

    public List<HykMessage> findList(HykMessage hykMessage) {
        return super.findList(hykMessage);
    }

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
     * 加油站内信发送
     *
     * @param phone 发送人
     */
    @Transactional(readOnly = false)
    public Integer saveMessageForAddOilA(String phone, String cardNo, String cardType, String cycle, String num, String money) {
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("加油到账提醒");

        hykMessage.setContent("亲爱的惠友！您向尾号为" + cardNo + "的" + cardType + "加油卡所充值的" + cycle + "月套餐第" + num + "期" + money + "元已到账。" +
                "请尽快前往指定加油站站内圈存机进行圈存后使用。如需帮助请联系客服4008-123-511（工作时间：9:00-18:00）");

        hykMessage.setMessStatus("0");
        hykMessage.setPhones(phone);
        hykMessage.setCreateDate(new Date());
        return hykMessageDao.insert(hykMessage);//发送站内信
    }

    /**
     * 加油站内信发送
     */
    @Transactional(readOnly = false)
    public Integer saveMessageForAddOilB(String cardPhone, String userPhone, String name, String cardNo, String cardType, String cycle, String num, String money) {
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("加油到账提醒");

        hykMessage.setContent("亲爱的惠友，您好，手机号码尾号为" + userPhone + "向" + name + "尾号为" + cardNo + "的" + cardType + "加油卡的" + cycle + "月套餐充值第" + num + "期" + money + "元已到账，祝您生活愉快。");

        hykMessage.setMessStatus("0");
        hykMessage.setPhones(cardPhone);
        hykMessage.setCreateDate(new Date());
        return hykMessageDao.insert(hykMessage);//发送站内信
    }

    /**
     * 加油站内信发送
     */
    @Transactional(readOnly = false)
    public Integer saveMessageForAddOilC(String phone, String cardNo, String cardType, String money) {
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("加油到账提醒");

        hykMessage.setContent("亲爱的惠友！您向尾号为" + cardNo + "的" + cardType + "加油卡即时充值的" + money + "元已到账。请尽快前往指定加油站站内圈存机进行圈存后使用。如需帮助请联系客服4008-123-511（工作时间：9:00-18:00）");

        hykMessage.setMessStatus("0");
        hykMessage.setPhones(phone);
        hykMessage.setCreateDate(new Date());
        return hykMessageDao.insert(hykMessage);//发送站内信
    }

    /**
     * 加油站内信发送
     */
    @Transactional(readOnly = false)
    public Integer saveMessageForAddOilD(String cardPhone, String userPhone, String name, String cardNo, String cardType, String money) {
        HykMessage hykMessage = new HykMessage();
        hykMessage.setId(ZxingHandler.getUUID());
        hykMessage.setTitle("加油到账提醒");

        hykMessage.setContent("亲爱的惠友，您好，手机号码尾号为" + userPhone + "向" + name + "尾号为" + cardNo + "的" + cardType + "加油卡即时充值" + money + "元已到账，祝您生活愉快。");

        hykMessage.setMessStatus("0");
        hykMessage.setPhones(cardPhone);
        hykMessage.setCreateDate(new Date());
        return hykMessageDao.insert(hykMessage);//发送站内信
    }



}