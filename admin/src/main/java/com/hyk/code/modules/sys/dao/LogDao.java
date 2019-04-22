/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.sys.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.sys.entity.Log;

/**
 * 日志DAO接口
 * @author 霍中曦
 *
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

}
