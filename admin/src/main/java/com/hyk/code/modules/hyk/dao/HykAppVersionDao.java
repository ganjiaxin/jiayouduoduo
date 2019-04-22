/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykAppVersion;

/**
 * 版本管理DAO接口
 * @author 霍中曦
 * @version 2018-12-18
 */
@MyBatisDao
public interface HykAppVersionDao extends CrudDao<HykAppVersion> {
	
}