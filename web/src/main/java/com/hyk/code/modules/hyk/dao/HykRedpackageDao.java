/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykRedpackage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 红包管理DAO接口
 * @author 霍中曦
 * @version 2018-11-14
 */
@MyBatisDao
public interface HykRedpackageDao extends CrudDao<HykRedpackage> {

    /**
     * 付款成功后更改红包状态为已使用
     * @param id
     * @return
     */
    @Update("update hyk_redpackage set `status`=1 ,use_time=NOW() where id=#{id}")
    Integer updateUseTime(String id);
    /**
     * 查询个人所有红包信息
     * @param userId
     * @return
     */
    List<HykRedpackage> queryAll(@Param("userId") String userId,@Param("status") String status);
    Integer queryAllCount(@Param("userId") String userId,@Param("status") String status);


    /**
     * 查询个人所有status=0红包信息
     * @param userId
     * @return
     */
    List<HykRedpackage> queryAllCan(@Param("userId")String userId, @Param("redType") String redType,@Param("minAmount") String minAmount,@Param("goodsId") String goodsId);

    /**
     * 不可用红包
     * @param userId
     * @param redType
     * @param minAmount
     * @param goodsId
     * @return
     */
    List<HykRedpackage> queryAllNotCan(@Param("userId")String userId, @Param("redType") String redType,@Param("minAmount") String minAmount,@Param("goodsId") String goodsId);
    /**
     * 查询个人所有status=0红包个数
     * @param userId
     * @return
     */
    @Select("SELECT count(*) from hyk_redpackage where user_id=#{userId} and status=0 and del_flag=0 and redType in(#{redType},2)")
    Integer queryAllCanCount(@Param("userId")String userId, @Param("redType") String redType);

    /**
     * 更新红包为状态已锁定:3
     *               未使用：0
     * @param status
     * @param id
     * @return
     */
    @Update("update hyk_redpackage set status =#{status} where id=#{id}")
    Integer updateStatusById(@Param("status")String status, @Param("id") String id);

    /**
     * 发红包
     * @param list
     * @return
     */
    Integer sendRegisteredRedPackage(List list);

    @Update("update hyk_redpackage set status=2 where UNIX_TIMESTAMP(NOW())>=UNIX_TIMESTAMP(over_time) and status=0")
    Integer updateStatus();

    /**
     * 查询该用户获得的邀请活动活动数量
     * @param userId
     * @return
     */
    @Select("SELECT IFNULL(sum(money),0) from hyk_redpackage where user_id=#{userId} and title=#{title}")
    Integer getCountMoneyByUserId(@Param("userId") String userId,@Param("title")String title);
}