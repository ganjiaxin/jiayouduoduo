/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.HykUserImg;
import com.hyk.code.modules.hyk.entity.HykAddress;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * 用户地址管理接口
 * @author 霍中曦
 * @version 2018-11-12
 */
@MyBatisDao
public interface HykUserImgDao extends CrudDao<HykUserImg> {

    Integer insert(@Param("userId")String userId,@Param("AdId")String AdId);
    Integer queryOne(@Param("userId")String userId,@Param("AdId")String AdId);
}