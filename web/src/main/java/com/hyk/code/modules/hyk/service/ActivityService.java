package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.modules.hyk.dao.HykOilCardDao;
import com.hyk.code.modules.hyk.dao.HykOrderDao;
import com.hyk.code.modules.hyk.dao.HykRedpackageDao;
import com.hyk.code.modules.hyk.dao.HykUserDao;
import com.hyk.code.modules.hyk.entity.HykRedpackage;
import com.hyk.code.modules.hyk.entity.HykUser;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 活动Service
 *
 * @author 霍中曦
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class ActivityService {
    @Autowired
    private HykRedpackageDao hykRedpackageDao;
    @Autowired
    private HykOilCardDao hykOilCardDao;
    @Autowired
    private HykUserDao hykUserDao;
    @Autowired
    private HykOrderDao hykOrderDao;
    @Autowired
    private HykMessageService hykMessageService;

    /**
     * 好友成功绑定加油卡  即送15元惠优红包
     * 先判断绑卡个数  符合要求判断inviterId 是否是手机号
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = false)
    public Integer inviteFriendActivityA(String userId) {
        Integer num = -1;
        try {
            Integer count = hykOilCardDao.findCountById(userId);
            HykUser hykUser = hykUserDao.get(userId);//被邀请人信息
            String inviterId = hykUser.getInviterId();
            if (count == 1) {
                //发送邀请人红包福利
                if (!StringUtils.isEmpty(inviterId)) {
                    BigDecimal money = new BigDecimal("15");
                    //判断是否手机号码
                    if (inviterId.length() == 11) {
                        hykUser.setPhone(inviterId);
                        //通过手机号码查询用户
                        HykUser userPhone = hykUserDao.getByPhone(hykUser);
                        //发送红包
                        num = sendRedPackage(userPhone.getId(), money, 30);
                    } else {
                        //发送红包
                        num = sendRedPackage(inviterId, money, 30);
                    }
                    //邀请人信息
                    HykUser user = hykUserDao.get(inviterId);
                    if (user != null) {
                        //发送站内信给邀请人
                        hykMessageService.saveMessageForInvite(user.getPhone(), hykUser.getPhone(), money, 1);
                    }
                }

                //发送绑卡福利
                BigDecimal money = new BigDecimal("5");
                //发送红包
                num = sendRedPackage(inviterId, money, 30);
                HykUser user = hykUserDao.get(inviterId);
                //发送站内信给邀请人
                hykMessageService.saveBingCard(user.getPhone(), money);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }


    /**
     * 好友首次购买加油套餐单笔满1000元 即送20元惠优红包
     * <p>
     * 判断是否符合活动要求
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = false)
    public Integer inviteFriendActivityB(String userId) {
        final BigDecimal money = new BigDecimal("20");
        Integer num = -1;
        try {
            num = hykOrderDao.findCountByUserIdAndType(userId);
            HykUser hykUser = hykUserDao.get(userId);//被邀请人信息
            String inviterId = hykUser.getInviterId();
            if (num == 1) {
                if (!StringUtils.isEmpty(inviterId)) {
                    if (inviterId.length() == 11) {
                        hykUser.setPhone(inviterId);
                        HykUser hykUser1 = hykUserDao.getByPhone(hykUser);
                        num = sendRedPackage(hykUser1.getId(), money, 30);
                    } else {
                        num = sendRedPackage(inviterId, money, 30);
                    }
                    HykUser hykUser2 = hykUserDao.get(inviterId);//邀请人信息
                    if (hykUser2 != null) {
                        hykMessageService.saveMessageForInvite(hykUser2.getPhone(), hykUser.getPhone(), money, 2);
                        //发送站内信给邀请人
                    }
                }
            } else {
                //啥也不做 这个人绑过卡了
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * @param userId   接收红包用户id
     * @param redMoney 红包金额
     * @param time     红包时长
     * @return
     */
    @Transactional(readOnly = false)
    public Integer sendRedPackage(String userId, BigDecimal redMoney, Integer time) {
        List list = new ArrayList();
        int dayNum = time;
        Date sendTime = new Date();
        Date overTime = DateUtils.addDay(sendTime, dayNum);
        Calendar cal = Calendar.getInstance();
        cal.setTime(overTime);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        overTime = cal.getTime();//overTime=2018-11-20 23:59:59

        HykRedpackage hykRedpackage = new HykRedpackage();
        hykRedpackage.setId(ZxingHandler.getUUID());
        hykRedpackage.setTitle("邀请好友赠送");
        hykRedpackage.setMoney(redMoney);
        hykRedpackage.setUserId(userId);
        hykRedpackage.setStatus("0");
        hykRedpackage.setSendTime(sendTime);
        hykRedpackage.setOverTime(overTime);
        hykRedpackage.setRedType("0");  //红包类型 0套餐 1即冲，2无限制
        list.add(hykRedpackage);
        int num = -1;
        try {
            num = hykRedpackageDao.sendRegisteredRedPackage(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }


}