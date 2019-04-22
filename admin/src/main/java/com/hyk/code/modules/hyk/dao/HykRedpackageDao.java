/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykRedpackage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 红包管理DAO接口
 * @author 霍中曦
 * @version 2018-11-14
 */
@MyBatisDao
public interface HykRedpackageDao extends CrudDao<HykRedpackage> {
    /**
     * 查询个人所有红包信息
     * @param userId
     * @return
     */
    List<HykRedpackage> queryAll(String userId);
    Integer queryAllCount(String userId);


    /**
     * 查询个人所有status=0红包信息
     * @param userId
     * @return
     */
    List<HykRedpackage> queryAllCan(@Param("userId")String userId, @Param("redType") String redType);
    /**
     * 查询个人所有status=0红包个数
     * @param userId
     * @return
     */
    Integer queryAllCanCount(@Param("userId")String userId, @Param("redType") String redType);

    /**
     * 更新红包状态
     * @param status
     * @param id
     * @return
     */
    Integer updateStatusById(@Param("status")String status, @Param("id") String id);
}