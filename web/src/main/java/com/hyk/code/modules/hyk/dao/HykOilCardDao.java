package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykOilCard;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 加油卡管理DAO接口
 * @Author: 甘佳欣
 * @Date: 2019/4/19
 */
@MyBatisDao
public interface HykOilCardDao extends CrudDao<HykOilCard> {
    /**
     * 添加加油卡
     *
     * @param hykOilCard
     * @return
     */
    Integer addCard(HykOilCard hykOilCard);

    /**
     * 查询该用户的所有加油卡信息
     *
     * @param userId
     * @return
     */
    @Select("SELECT * ,ydz_money as surplusAdd from hyk_oil_card where user_id=#{userId} and del_flag=0")
    List<HykOilCard> findById(String userId);

    /**
     * 更新应到账金额等
     *
     * @param cardId
     * @param waitMoney
     * @return
     */
    @Update("update hyk_oil_card set ydz_money=(SELECT ifnull(sum(money),0) from hyk_oil_manager where del_flag=0 and" +
            " FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()),'%Y%m')=FROM_UNIXTIME(UNIX_TIMESTAMP(plan_oil_date),'%Y%m') and " +
            "cardId=#{cardId}), " +
            " wait_money=wait_money+#{waitMoney}  where id=#{cardId} ")
    Integer updateYdzMoney(@Param("cardId") String cardId, @Param("waitMoney") BigDecimal waitMoney);

    /**
     * 删除加油卡
     *
     * @param
     * @return
     */
    @Update("update hyk_oil_card set del_flag=1 where id=#{id} and user_id=#{userId}")
    Integer softDeleteById(@Param("id") String id, @Param("userId") String userId);

    /**
     * 应到账金额，已到账金额
     *
     * @return
     */
    @Update("update hyk_oil_card a,(select sum(hyk_oil_manager.money) as money ,hyk_oil_manager.cardId as cardId from" +
            " hyk_oil_manager,hyk_oil_card where " +
            " hyk_oil_manager.del_flag=0 and hyk_oil_manager.status=0 and hyk_oil_manager.cardId=hyk_oil_card.id and " +
            " FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()),'%Y%m')=FROM_UNIXTIME(UNIX_TIMESTAMP(hyk_oil_manager.plan_oil_date)" +
            ",'%Y%m') " +
            " GROUP BY hyk_oil_manager.cardId) b , " +
            "(select sum(hyk_oil_manager.money) as money ,hyk_oil_manager.cardId as cardId from hyk_oil_manager," +
            "hyk_oil_card where hyk_oil_manager.cardId=hyk_oil_card.id " +
            " and FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()),'%Y%m')=FROM_UNIXTIME(UNIX_TIMESTAMP(hyk_oil_manager" +
            ".plan_oil_date),'%Y%m') and hyk_oil_manager.status=1 " +
            " GROUP BY hyk_oil_manager.cardId) c set a.ydz_money = b.money , a.sjdz_money=c.money where a.id=b.cardId" +
            " and a.id= c.cardId")
    Integer updateMoney();

    /**
     * 月初更新应到账金额，已到账金额
     *
     * @return
     */
    @Update("update hyk_oil_card set ydz_money=0,sjdz_money=0")
    Integer updateMoneyMonthBegin();

    /**
     * 月初更新应到账金额，已到账金额
     *
     * @return
     */
    @Update("update hyk_oil_card a ,(SELECT cardId as cardId,ifnull(sum(money),0) as money from hyk_oil_manager where" +
            " del_flag=0 and status=0 and FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()),'%Y%m')=FROM_UNIXTIME(UNIX_TIMESTAMP" +
            "(plan_oil_date),'%Y%m') GROUP BY cardId)b " +
            "set a.ydz_money=b.money where a.id=b.cardId")
    Integer updateMoneyMonthBegin2();

    /**
     * 查询该用户绑定过得所有加油卡数量包含已删除加油卡
     *
     * @param userId
     * @return
     */
    @Select("SELECT COUNT(*) from hyk_oil_card where user_id=#{userId}")
    Integer findCountById(String userId);

    /**
     * 查询该卡信息
     *
     * @param userId
     * @return
     */
    HykOilCard getByCardNoAndUserId(String userId);

    /**
     * 先下单后绑卡 更新本月应到账金额
     */
    Integer updateYdzmoneyByCardId(@Param("ydzMoney") double ydzMoney, @Param("cardId") String cardId);
}