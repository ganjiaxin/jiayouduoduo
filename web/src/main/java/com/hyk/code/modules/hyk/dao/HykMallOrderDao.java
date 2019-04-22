/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykMallGoods;
import com.hyk.code.modules.hyk.entity.HykMallOrder;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商城商品订单DAO接口
 *
 * @author wyy
 * @version 2018-12-21
 */
@MyBatisDao
public interface HykMallOrderDao extends CrudDao<HykMallOrder> {
    @Update("update hyk_mall_order set send_status=2 where send_status=1 and orderno=#{orderno}")
    Integer updateupdateSendStatusOver(@Param("orderno")String orderno);//更新实物商品为已签收

    Integer insert2(@Param("id") String id, @Param("orderno") String orderno, @Param("userId") String userId, @Param("phone") String phone,
                    @Param("goodsId") String goodsId, @Param("goodsName") String goodsName, @Param("goodsType") String goodsType, @Param("num") Integer num,
                    @Param("money") BigDecimal money, @Param("createDate") Date createDate, @Param("status") String status, @Param("sendStatus") String sendStatus,
                    @Param("addressId") String addressId,@Param("remark") String remark);

    Integer updateOverTimeMallOrder();//定时任务 过期订单 更新 并恢复goods表stock
    Integer updateOverTimeMallOrderByUserId(@Param("userId")String userId);//过期订单 更新 并恢复goods表stock
    List<HykMallOrder> getListByUserId(@Param("userId") String userId,@Param("sendStatus")String sendStatus);//查询个人所有订单status asc
    Integer updateFailStatusByOrderno(@Param("status")String status,@Param("orderno")String orderno);//更新statusBy订单号
    Integer updateSuccessStatusByOrder(@Param("status")String status,@Param("orderno")String orderno,@Param("payType")String payType);

    /**
     * 修改订单状态为已发送 1.2版本虚拟商品发送改为已完成
     * @param orderno
     * @return
     */
    Integer updateSendStatusByOrderno(String orderno);

    /**
     * 根据订单号获取订单对象
     * @param orderno
     * @return
     */
    HykMallOrder getByOrderno(String orderno);
    Integer getCountBuyOilCardByTime(@Param("goodsName")String goodsName,@Param("userId")String userId);//获取该用户今日购买该商品数量
    Integer getCountBuyOilCard(@Param("goodsName")String goodsName,@Param("userId")String userId);//获取该用户共购买该商品数量
    Integer softDeleteMallOrder();//定时删除超过指定日期的商城订单

}