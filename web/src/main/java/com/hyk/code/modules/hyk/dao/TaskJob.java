package com.hyk.code.modules.hyk.dao;

import com.hyk.code.modules.hyk.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class TaskJob {
    private final static Logger logger = LoggerFactory.getLogger(TaskJob.class);


    @Autowired
    private HykOrderService hykOrderService;
    @Autowired
    private HykRedpackageService hykRedpackageService;
    @Autowired
    private HykAdService hykAdService;
    @Autowired
    private HykNoticeService hykNoticeService;
    @Autowired
    private HykOilCardService hykOilCardService;
    @Autowired
    private HykUserService hykUserService;
    @Autowired
    private HykMallOrderService hykMallOrderService;
    @Autowired
    private HykHolidaysService hykHolidaysService;
    @Autowired
    private HykOilPriceService hykOilPriceService;

    /**
     * @Title: getToken_ac
     * @Description: TODO 定时获取 access_token
     * @date 2017年9月1日 上午10:18:11
     * @author wangyw
     */

    //@Scheduled(cron = "0 0 ** 1 * * ?")
    @Scheduled(cron = "0 0/5 * * * ?")//5分一次
    //@Scheduled(cron = "0/5 * *  * * ? ")
    public void updateOrderStatus() {
//        SimpleDateFormat  sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//        System.out.println(sdf.format(new Date()));
        try {
            Integer num = hykUserService.updateAllOrder();//只返回余额不更新状态
            Integer num1 = hykOrderService.updateStatus1();//有红包的
            Integer num2 = hykOrderService.updateStatus2();
            logger.info("本次订单超时" + (num1 + num2) + "条,其中红包复原" + num1 + "条,恢复余额"+num+"人;-----------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0/5 * * * ?")//5分一次
    public void updateMallOrderStatus() {
        try {
            hykMallOrderService.updateStatus();
            logger.info("商城订单超时更新成功;-----------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Scheduled(cron = "0 0 2 * * ?")//凌晨2点
    public void insertOilPriceList() {
        try {
            hykOilPriceService.insertOilPriceList();
            logger.info("今日油价更新成功;-----------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Scheduled(cron = "0 0 3 * * ?")//凌晨3点
    public void softDeleteOrder() {
        try {
            Integer order = hykOrderService.softDeleteOrder();
            Integer mallOrder=hykMallOrderService.softDeleteMallOrder();
            logger.info("超过指定日期--加油订单删除"+order+"条,商城订单删除"+mallOrder+";-----------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Scheduled(cron = "0 0 0/1 * * ?")//一小时一次
    public void updateRedPackageStatus() {
        try {
            Integer num = hykRedpackageService.updateStatus();
            logger.info("本次红包过期" + num + "条-----------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //@Scheduled(cron = "0 0 0/1 * * ?")//一小时一次
    public void updateAdStatus() {
        try {
            Integer num = hykAdService.updateStaus();
            logger.info("本次广告过期" + num + "条-----------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //@Scheduled(cron = "0 0 0/1 * * ?")//一小时一次
    public void updateNoticeStatus() {
        try {
            Integer num = hykNoticeService.updateStatus();
            logger.info("本次公告过期" + num + "条-----------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //@Scheduled(cron = "0 0 23 * * ?")//一小时一次
    public void updateMoney() {
        try {
            Integer num = hykOilCardService.updateMoney();
            logger.info("本次更新money " + num + "条-----------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Scheduled(cron = " 0 0 2 1 * ? ")
    public void updateMoneyMonthBegin() {
        try {
            Integer num = hykOilCardService.updateMoneyMonthBegin();
            logger.info("月初更新油卡本月应到账金额已到账金额成功-----------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = " 0 0 0 31 12 ? ")
    public void updateHolidays() {//获取3年中的节假日信息  一年执行一次
        try {
            Integer num = hykHolidaysService.holidaysTask();
            logger.info("每年更新节假日成功-----------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}



