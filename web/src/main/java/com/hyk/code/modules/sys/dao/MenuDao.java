/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.sys.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.sys.entity.Menu;

import java.util.List;

/**
 * 菜单DAO接口
 * @author 霍中曦
 *
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);

	public List<Menu> findHykByUserId(Menu menu);

	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
	public List<Menu> findChildByParent(Menu menu);
	
}
