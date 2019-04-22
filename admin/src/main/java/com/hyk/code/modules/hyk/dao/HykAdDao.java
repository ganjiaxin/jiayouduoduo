/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykAd;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户红包管理DAO接口
 * @author 霍中曦
 * @version 2018-11-12
 */
@MyBatisDao
public interface HykAdDao extends CrudDao<HykAd> {
    /**
     * 获取banner中的所有
     * @return
     */
    @Select("SELECT banner_img as bannerImg, title as title,url as url,app_img as appImg from hyk_ad")
    List<HykAd> All();
	
}