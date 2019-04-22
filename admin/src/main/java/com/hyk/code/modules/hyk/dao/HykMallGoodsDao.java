/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykMallGoods;

/**
 * 商城商品管理DAO接口
 * @author wyy
 * @version 2018-12-21
 */
@MyBatisDao
public interface HykMallGoodsDao extends CrudDao<HykMallGoods> {
	
}