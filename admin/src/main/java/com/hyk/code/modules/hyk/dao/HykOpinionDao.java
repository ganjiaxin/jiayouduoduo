/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykAd;
import com.hyk.code.modules.hyk.entity.HykOpinion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 意见反馈DAO接口
 * @author 霍中曦
 * @version 2018-11-12
 */
@MyBatisDao
public interface HykOpinionDao extends CrudDao<HykAd> {
    /**
     * 保存意见反馈信息
     */
	@Insert("insert into hyk_opinion (user_id,opinion,times)values(#{userId},#{opinion},now())；")
    Integer saveOpinion(@Param("userId")String userId,@Param("opinion")String opinion);
}