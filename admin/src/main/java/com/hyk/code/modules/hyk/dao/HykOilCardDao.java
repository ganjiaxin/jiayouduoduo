/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykOilCard;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

/**
 * 加油卡管理DAO接口
 *
 * @author 霍中曦
 * @version 2018-11-12
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
    @Select("SELECT * from hyk_oil_card where user_id=#{userId} and del_flag=0")
    List<HykOilCard> findById(String userId);


    /**
     * 删除加油卡
     *
     * @param
     * @return
     */
    @Select("update hyk_oil_card set del_flag=1 where id=#{id} and user_id=#{userId}")
    Integer softDeleteById(@Param("id") String id, @Param("userId") String userId);
    /**
     * 加油计划变更为已加油时，更改card表中money(累计加油金额)
     * @param money
     * @param cardId
     * @return
     */
    @Update(" update hyk_oil_card set money=money + #{money} where id=#{cardId} ")
    Integer updateMoney(@Param("money") BigDecimal money, @Param("cardId")String cardId);

}