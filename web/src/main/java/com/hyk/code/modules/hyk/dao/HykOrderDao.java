/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单管理DAO接口
 * @author 霍中曦
 * @version 2018-11-09
 */
@MyBatisDao
public interface HykOrderDao extends CrudDao<HykOrder> {


    @Select("select * from hyk_order where order_no=#{orderNo} and del_flag=0")
    HykOrder findByOrderNo(String orderNo);

    /**
     首页已完成订单显示 4条
     */
    @Select("select insert( a.user_phone,4,6, '****')  as userPhone ,b.goods_name as goodsId from `hyk_order`  a  left join hyk_goods b on a.goods_id=b.id " +
            "where a.`pay_date` is not null and a.del_flag=0 and a.order_status=2  ORDER BY  a.`pay_date`  desc limit 4 ")
    List<HykOrder> getSuccessAll();

    /**
     * 根据订单号查询该订单
     * @param orderNo 不知道为什么用对象返回android端就是有一个字段接受不到  没办法改用map
     * @return
     */
//    @Select("SELECT a.order_status as orderStatus,a.card_no as cardNo ,a.amt as amt,a.redpackage_amt as redpackageAmt,a.order_no as orderNo ,a.create_date as createDateStr, " +
//            " a.pay_date as payDateStr ,b.goods_name as goodsName , b.cycle as cycel ,b.discount as discount,b.activity_discount as activityDiscount, " +
//            " b.goods_type as goodsType,b.prices as prices from hyk_order a left join hyk_goods b on a.goods_id=b.id where a.order_no=#{orderNo}")
     Map queryByOrderNo(String orderNo);

     @Select("select count(*) from hyk_order where order_no=#{orderNo}")
     Integer queryCountByOrderNo(String orderNo);//该订单数量

    /**
     * 查询该订单详细信息
     * @param orderNo
     * @return
     */
    @Select("select *,user_id as userId from hyk_order where order_no=#{orderNo} and del_flag=0")
    HykOrder queryByOrder(String orderNo);

    /**
     * 付款成功后
     * @param payType
     * @param orderNo
     * @return
     */
    @Update("update hyk_order set pay_type=#{payType},pay_date=NOW(),order_status=2 where order_no=#{orderNo} and del_flag=0")
    Integer updatePayTypeByOrderNo(@Param("payType")String payType,@Param("orderNo")String orderNo);

    /**
     * App版本相关信息
     * @return  url  下载链接
     * @return  version  版本
     * @return  msg  更新内容
     * @return  updateSign  是否必更新版本   0否  1是
     */
    Map getApp(@Param("id")String id);

    /**
     * 个人所有订单查询 oilStatus 0执行中 1已完成
     */

        List<HykOrder> queryListByUserId(@Param("userId") String userId,@Param("oilStatus") String oilStatus);


    /**
     * 添加充值订单
     * @param hykOrder
     * @return
     */
    Integer insertOrder(HykOrder hykOrder);


    List<HykOrder> findInviterList(HykOrder hykOrder);

    List<HykOrder> getUpMonthAmt(HykOrder hykOrder);

    List<HykOrder> getCurrentMonthAmt(HykOrder hykOrder);


    @Update(" UPDATE hyk_order,hyk_redpackage set hyk_order.order_status=3,hyk_redpackage.`status`=0 where hyk_order.redpackage_id=hyk_redpackage.id " +
            " and UNIX_TIMESTAMP(NOW())>=UNIX_TIMESTAMP(hyk_order.create_date)+1800 and hyk_order.order_status=0 and hyk_order.del_flag=0")
    Integer updateStatus1();
    @Update(" update hyk_order set order_status=3 where UNIX_TIMESTAMP(NOW())>=UNIX_TIMESTAMP(hyk_order.create_date)+1800 and hyk_order.order_status=0 and hyk_order.del_flag=0 ")
    Integer updateStatus2();
    @Update(" UPDATE hyk_order,hyk_redpackage set hyk_order.order_status=3,hyk_redpackage.`status`=0 where hyk_order.redpackage_id=hyk_redpackage.id " +
            " and UNIX_TIMESTAMP(NOW())>=UNIX_TIMESTAMP(hyk_order.create_date)+1800 and hyk_order.order_status=0 and hyk_order.user_id=#{userId} and hyk_order.del_flag=0 ")
    Integer updateStatusByUserId1(String userId);
    @Update(" update hyk_order set order_status=3 where UNIX_TIMESTAMP(NOW())>=UNIX_TIMESTAMP(hyk_order.create_date)+1800 and hyk_order.order_status=0 and hyk_order.user_id=#{userId} and hyk_order.del_flag=0")
    Integer updateStatusByUserId2(String userId);


    /**
     * 查询个人付款成功且是套餐且付款成功且单笔金额>1000
     * @param userId
     * @return
     */
    @Select("SELECT COUNT(*) from hyk_order where amt>=1000 and order_status=2 and order_type=1 and user_id=#{userId} and del_flag=0")
    Integer findCountByUserIdAndType(String userId);

    HykOrder sumBackMoney(HykOrder hykOrder);

    List<HykOrder> queryInviterBackMoney(HykOrder hykOrder);
    List<HykOrder> monthList(HykOrder hykOrder);

    List<HykOrder> getListByUserIdAndNullCardIdAndNullCardNo(String userId);//查这个人没有cardId，cardNo的订单

    /**
     * 更新为空的cardId ，cardNo
     * @param cardId
     * @param cardNo
     * @param userId
     * @return
     */
    Integer updateCardIdAndCardNo(@Param("cardId") String cardId,@Param("cardNo")String cardNo,@Param("userId") String userId);

    /**
     * 更新快捷支付金额 ，余额支付金额
     */
    Integer updatePayableMoneyAndBalanceMoney(@Param("balanceMoney") BigDecimal balanceMoney, @Param("orderId")String orderId);

    Integer updateFailStatus(String orderNo);//更新状态为失败
    List<HykOrder> getListByCardId(String cardId);//1.2需求-->按卡id查已付款订单 加油状态付款时间排序
    Integer softDeleteOrder();//定时删除超过指定日期的加油订单

    Integer updateDelFlag(String id);//更新delflag为0

    List<HykOrder> getSuccessCountByDate(@Param("userId") String userId,@Param("goodsId") String goodsId,@Param("everyAmt") String everyAmt ,
                                         @Param("startTime") String startTime,@Param("endTime") String endTime);//获取指定时间段内成功付款且每期金额大于everyAmt的该订单；

    Integer queryReadyOrderCount(String cardId);//查询代付款订单数量
}