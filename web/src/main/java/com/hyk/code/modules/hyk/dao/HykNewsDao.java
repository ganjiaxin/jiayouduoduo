/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykAd;
import com.hyk.code.modules.hyk.entity.HykNews;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 新闻管理DAO接口
 * @author 霍中曦
 * @version 2018-11-12
 */
@MyBatisDao
public interface HykNewsDao extends CrudDao<HykNews> {

    List<HykNews> getAllNews(@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);//所有可展示新闻
    HykNews get(String id);
}