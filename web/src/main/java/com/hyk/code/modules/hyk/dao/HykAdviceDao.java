/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykAdvice;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;


/**
 * 意见反馈DAO接口
 * @author 霍中曦
 * @version 2018-11-12
 */
@MyBatisDao
public interface HykAdviceDao extends CrudDao<HykAdvice> {
    /**
     * 保存意见反馈信息
     */
	@Insert("insert into hyk_advice (id,content,imgs,phone,status,create_Date)values(#{id},#{content},#{imgs},#{phone},0,now())")
    Integer saveOpinion(@Param("id")String id,@Param("content")String content,@Param("imgs")String imgs,@Param("phone")String phone);
}