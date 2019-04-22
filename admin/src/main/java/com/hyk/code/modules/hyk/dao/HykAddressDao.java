/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykAddress;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * 用户地址管理接口
 * @author 霍中曦
 * @version 2018-11-12
 */
@MyBatisDao
public interface HykAddressDao extends CrudDao<HykAddress> {
    /**
     * 获取该用户的所有地址
     * @param userId
     * @return
     */
    @Select("SELECT * from hyk_address where user_id =#{userId} and del_flag=0 order by is_default desc")
    List<HykAddress> getListByUserId(String userId);

    /**
     * 根据地址id  查询地址信息
     * @param AddressId
     * @return
     */
    @Select("select * from hyk_address where id=#{AddressId}")
    HykAddress getByAddressId(String AddressId);

    /**
     * 更新地址信息
     * @param
     * @return
     */
    Integer updateAddress(HykAddress hykAddress);

    /**
     * 设置该用户的所有地址为非默认
     */
    @Update("update hyk_address set is_default=0 where user_id=#{userId}")
    Integer updateNotDefault(String userId);


    /**
     * 删除地址
     */
    @Select("update hyk_address set del_flag=1 where id=#{addressId}")
    Integer softDeleteAddress(String addressId);


    /**
     * 添加地址
     */
    @Insert("INSERT into hyk_address(user_id,name,phone,address,is_default)values(#{userId},#{name},#{phone},#{address},#{isDefault})")
    Integer addAddress(HykAddress hykAddress);
}