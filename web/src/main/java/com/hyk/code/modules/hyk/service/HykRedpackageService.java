/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.modules.hyk.dao.HykMessageDao;
import com.hyk.code.modules.hyk.entity.HykMessage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.entity.HykRedpackage;
import com.hyk.code.modules.hyk.dao.HykRedpackageDao;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 红包管理Service
 *
 * @author 霍中曦
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class HykRedpackageService extends CrudService<HykRedpackageDao, HykRedpackage> {
    @Autowired
    private HykRedpackageDao hykRedpackageDao;
    @Autowired
    private HykMessageService hykMessageService;

    public HykRedpackage get(String id) {
        return super.get(id);
    }

    public List<HykRedpackage> findList(HykRedpackage hykRedpackage) {
        return super.findList(hykRedpackage);
    }

    public Page<HykRedpackage> findPage(Page<HykRedpackage> page, HykRedpackage hykRedpackage) {
        return super.findPage(page, hykRedpackage);
    }

    @Transactional(readOnly = false)
    public void save(HykRedpackage hykRedpackage) {
        super.save(hykRedpackage);
    }

    @Transactional(readOnly = false)
    public void delete(HykRedpackage hykRedpackage) {
        super.delete(hykRedpackage);
    }


    /**
     * 付款成功后更改红包状态为已使用
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public Integer updateUseTime(String id) {
        return hykRedpackageDao.updateUseTime(id);
    }

    /**
     * 查询个人所有红包信息
     *
     * @param userId
     * @return
     */
    public List<HykRedpackage> queryAll(String userId, String status, Integer currPage, Integer pageSize) {
        //查询全部数据
        List<HykRedpackage> list = hykRedpackageDao.queryAll(userId, status);
        if (list.size() < (currPage - 1) * pageSize) {
            return new ArrayList<>();
        }
        //从第几条数据开始
        int firstIndex = (currPage - 1) * pageSize;
        //到第几条数据结束
        int lastIndex = currPage * pageSize;
        if (lastIndex > list.size()) {
            lastIndex = list.size();
        }
        return list.subList(firstIndex, lastIndex); //直接在list中截取
    }

    ;

    public Integer queryAllCount(String userId, String status) {
        return hykRedpackageDao.queryAllCount(userId, status);
    }

    ;

    /**
     * 查询个人所有status=0红包信息
     *
     * @param userId
     * @return
     */
    public List<HykRedpackage> queryAllCan(String userId, String redType, String minAmount, String goodsId) {
        return hykRedpackageDao.queryAllCan(userId, redType, minAmount, goodsId);
    }

    ;

    /**
     * 不可用红包
     *
     * @param userId
     * @param redType
     * @param minAmount
     * @param goodsId
     * @return
     */
    public List<HykRedpackage> queryAllNotCan(String userId, String redType, String minAmount, String goodsId) {
        return hykRedpackageDao.queryAllNotCan(userId, redType, minAmount, goodsId);
    }

    ;

    /**
     * 查询个人所有status=0红包个数
     *
     * @param userId
     * @return
     */
    public Integer queryAllCanCount(String userId, String redType) {
        return hykRedpackageDao.queryAllCanCount(userId, redType);
    }

    ;


    /**
     * 更新红包状态
     *
     * @param status
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public Integer updateStatusById(String status, String id) {
        return hykRedpackageDao.updateStatusById(status, id);
    }

    ;

    /**
     * 发送注册红包  保存红包并发送相应站内信
     *
     * @return 1个8元的 6个10元的
     */
    @Transactional(readOnly = false)
    public Integer sendRegisteredRedPackage(String userId, String phone) {

        List list = new ArrayList();
        int dayNum = 30;
        Date sendTime = new Date();
        Date overTime = DateUtils.addDay(sendTime, dayNum);
        Calendar cal = Calendar.getInstance();
        cal.setTime(overTime);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        overTime = cal.getTime();//overTime=2018-11-20 23:59:59
        for (int i = 1; i <= 10; i++) {
            HykRedpackage hykRedpackage = new HykRedpackage();
            hykRedpackage.setId(ZxingHandler.getUUID());
            hykRedpackage.setTitle("注册红包");
            if (i == 1) {
                hykRedpackage.setMoney(new BigDecimal(10));
                hykRedpackage.setMinAmount(new BigDecimal(500));
                hykRedpackage.setGoodsId("3727db8bd29b45bbb5e039059d3c097e");
            } else if (i == 2) {
                hykRedpackage.setMoney(new BigDecimal(18));
                hykRedpackage.setMinAmount(new BigDecimal(1000));
                hykRedpackage.setGoodsId("3727db8bd29b45bbb5e039059d3c097e");
            } else if (i == 3) {
                hykRedpackage.setMoney(new BigDecimal(22));
                hykRedpackage.setMinAmount(new BigDecimal(500));
                hykRedpackage.setGoodsId("76fd38857a4340dbb47fdf9a44afb4dd");
            } else if (i == 4) {
                hykRedpackage.setMoney(new BigDecimal(28));
                hykRedpackage.setMinAmount(new BigDecimal(1000));
                hykRedpackage.setGoodsId("76fd38857a4340dbb47fdf9a44afb4dd");
            } else if (i == 5) {
                hykRedpackage.setMoney(new BigDecimal(32));
                hykRedpackage.setMinAmount(new BigDecimal(500));
                hykRedpackage.setGoodsId("c1d283fcef0a44dbb124d7a882448c84");
            } else if (i == 6) {
                hykRedpackage.setMoney(new BigDecimal(38));
                hykRedpackage.setMinAmount(new BigDecimal(1000));
                hykRedpackage.setGoodsId("c1d283fcef0a44dbb124d7a882448c84");
            } else if (i == 7) {
                hykRedpackage.setMoney(new BigDecimal(52));
                hykRedpackage.setMinAmount(new BigDecimal(500));
                hykRedpackage.setGoodsId("6e4347adce4d46d6a7b5b9d7542af7a8");
            } else if (i == 8) {
                hykRedpackage.setMoney(new BigDecimal(58));
                hykRedpackage.setMinAmount(new BigDecimal(1000));
                hykRedpackage.setGoodsId("6e4347adce4d46d6a7b5b9d7542af7a8");
            } else if (i == 9) {
                hykRedpackage.setMoney(new BigDecimal(72));
                hykRedpackage.setMinAmount(new BigDecimal(500));
                hykRedpackage.setGoodsId("36df659645b440e3bc07505f6239c41b");
            } else if (i == 10) {
                hykRedpackage.setMoney(new BigDecimal(88));
                hykRedpackage.setMinAmount(new BigDecimal(1000));
                hykRedpackage.setGoodsId("36df659645b440e3bc07505f6239c41b");
            }


            hykRedpackage.setUserId(userId);
            hykRedpackage.setStatus("0");
            hykRedpackage.setSendTime(sendTime);
            hykRedpackage.setOverTime(overTime);
            hykRedpackage.setRedType("0");
            list.add(hykRedpackage);
        }
        try {
            int num = hykRedpackageDao.sendRegisteredRedPackage(list);//保存红包
            int num1 = hykMessageService.saveMessageForRegister(phone);//发送站内信
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 定时任务
     *
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public Integer updateStatus() {
        try {
            return hykRedpackageDao.updateStatus();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }

    ;

    /**
     * 查询该用户获得的邀请活动活动数量
     *
     * @param userId
     * @return
     */
    public Integer getCountMoneyByUserId(@Param("userId") String userId, @Param("title") String title) {
        return hykRedpackageDao.getCountMoneyByUserId(userId, title);
    }

    ;
}