/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykYzm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * 验证码管理DAO接口
 * @author 霍中曦
 * @version 2018-11-12
 */
@MyBatisDao
public interface HykYzmDao extends CrudDao<HykYzm> {
    /**
     * 用过手机号获得最新的验证码对象
     * @param phone
     * @return
     */
    @Select("SELECT * from hyk_yzm where phone=#{phone} ORDER BY ID DESC limit 1")
    HykYzm queryByPhone(String phone);

    /**
     * 保存验证码信息
     * @param hykYzm
     * @return
     */
    @Insert("INSERT into hyk_yzm  (phone,times,code,type) values(#{phone},#{times},#{code},#{type})")
    Integer saveYzm(HykYzm hykYzm);
}