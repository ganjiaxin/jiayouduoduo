/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykOilManager;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 加油管理DAO接口
 *
 * @author 霍中曦
 * @version 2018-11-09
 */
@MyBatisDao
public interface HykOilManagerDao extends CrudDao<HykOilManager> {
    /**
     * 根据加油卡号和用户id查询本月应加油金额
     *
     * @param userId 用户id
     * @param cardNo 加油卡号
     * @return
     */
    @Select("SELECT SUM(money) from hyk_oil_manager where order_no in(SELECT order_no " +
            " from hyk_order where user_id=#{userId} and card_no=#{cardNo}) and DATE_FORMAT( plan_oil_date, '%Y%m' " +
            ") = DATE_FORMAT( CURDATE( ) , '%Y%m' ) and `status`=#{status} and del_flag=0")
    Double getMoeyByCardNoAndUserId(@Param("userId") String userId, @Param("cardNo") String cardNo, @Param("status") String status);

    /**
     * 我的->加油计划  待加油已加油金额展示
     *
     * @param userId    用户id
     * @param yearMonth 格式：201811
     * @param status    加油计划状态 0待加油 1已加油
     * @return
     */
    @Select("SELECT IFNULL(SUM(money),0) from hyk_oil_manager where user_id=#{userId} and DATE_FORMAT( plan_oil_date, '%Y%m')=#{yearMonth} and status=#{status} and del_flag=0")
    Double getMoneyByUserIdAndTime(@Param("userId") String userId, @Param("yearMonth") String yearMonth, @Param("status") String status);

    /**
     * 我的->加油计划  待加油已加油金额展示
     *
     * @param userId 用户id
     * @param status 加油计划状态 0待加油 1已加油
     * @return
     */
    @Select("SELECT SUM(money) from hyk_oil_manager where user_id=#{userId} and FROM_UNIXTIME(UNIX_TIMESTAMP(plan_oil_date),'%Y%m') =#{time} " +
            "and status=#{status} and del_flag=0")
    Double getMoneyByUserIdAndTimeAndStatus(@Param("userId") String userId, @Param("time") String time, @Param("status") String status);


    /**
     * 我的->加油计划  具体信息展示
     *
     * @param userId    用户id
     * @param yearMonth 格式：201811
     * @param status    加油计划状态 0待加油 1已加油
     * @return
     */
    @Select("SELECT * from hyk_oil_manager where user_id=#{userId} and DATE_FORMAT( plan_oil_date, '%Y%m')=#{yearMonth} and status=#{status} and del_flag=0")
    List<HykOilManager> getListByUserIdAndTimeAndStatus(@Param("userId") String userId, @Param("yearMonth") String yearMonth, @Param("status") String status);

    /**
     * 个人所有加油计划展示
     *
     * @param userId
     * @return
     */
    @Select(" SELECT date_format(a.plan_oil_date,'%Y') as year,date_format(a.plan_oil_date,'%m') as month,date_format(a.plan_oil_date,'%d') as day ,date_format(a.plan_oil_date,'%Y%m') as registerDateStr, " +
            " a.status as status ,a.money as money ,c.`name` as cardName,c.oli_card_no as cardNum , " +
            " a.periods_num as periodsNum,b.cycle as cycle,b.goods_type as goodsType from hyk_oil_manager a " +
            " left join hyk_goods b on a.goodId=b.id left join hyk_oil_card c on a.cardId=c.id where a.user_id=#{userId} and a.del_flag=0")
    List<HykOilManager> getAllByUserId(String userId);


    /**
     * 个人所有加油计划展示 可以根据时间查询
     *
     * @param userId
     * @param time   时间  格式 201811
     * @return
     */
//    @Select(" SELECT date_format(a.plan_oil_date,'%Y') as year,date_format(a.plan_oil_date,'%m') as month,date_format(a.plan_oil_date,'%d') as day ," +
//            " date_format(a.plan_oil_date,'%Y%m%d') as registerDateStr, a.status as status ,a.money as money ,c.`name` as cardName , " +
//            " insert( c.oli_card_no,5,case LENGTH(c.oli_card_no) when 16 then 8 when 19 then 11 else 1 end, case LENGTH(c.oli_card_no) when 16 then '********' when 19 then '***********' else '*' end ) as cardNum , " +
//            " a.periods_num as periodsNum,b.cycle as cycle,b.goods_type as goodsType from hyk_oil_manager a left join hyk_goods b on a.goodId=b.id " +
//            " left join hyk_oil_card c on a.cardId=c.id where a.user_id=#{userId} and FROM_UNIXTIME(UNIX_TIMESTAMP(a.plan_oil_date),'%Y%m%d') = #{time} and a.`status`=#{status} and a.del_flag=0")
    List<HykOilManager> getAllByUserIdAndTimeAndStatus(@Param("userId") String userId, @Param("time") String time, @Param("day") String day,@Param("status") String status);

    /**
     * 查询该油卡id 未被充值的加油计划记录数
     */
    @Select("SELECT COUNT(*) from hyk_oil_manager where `status`=0 and cardId=#{cardId} and (del_flag=0 or del_flag=2) ")//0 正常 2暂停
    Integer queryCountByCardId(String cardId);

    /**
     * 添加加油计划
     * @param list
     * @return
     */
    Integer insertOilManagerList(List list);

    /**
     * 根据userId更新cardId为空的字段
     * @param cardId
     * @param userId
     * @return
     */
    Integer updateCardId(@Param("cardId")String cardId,@Param("userId")String userId);

    /**
     * 查询个人未加油加油计划
     * @param userId
     * @return
     */
    Integer getCountOilManagerByUserId(String userId);

    List<String> getTimeGroupByUserId(@Param("userId") String userId, @Param("time") String time);//获得当月该用户有加油计划的日期

    List<HykOilManager> getAllByUserIdAndTimeAndStatusNew(@Param("userId") String userId, @Param("time") String time);//获得该用户当日的所有加油计划
}