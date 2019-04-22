/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.sys.dao;

import com.hyk.code.common.persistence.TreeDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author wyw
 *
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
