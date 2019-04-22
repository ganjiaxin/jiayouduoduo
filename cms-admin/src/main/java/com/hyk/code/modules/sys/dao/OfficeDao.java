/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.sys.dao;

import java.util.List;

import com.hyk.code.common.persistence.TreeDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author wyw
 *
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	public List<Office> queryByGys(Office office);
	public List<Office> queryByOfficeid(List<String> list);
	public List<Office> queryByOfficeidOne(String id);
	public List<Office> queryByGsjg(Office office);
	public List<Office> queryByOther(Office office);
}
