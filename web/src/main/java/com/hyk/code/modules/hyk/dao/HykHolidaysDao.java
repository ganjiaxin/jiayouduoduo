/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykHolidays;

import java.awt.*;
import java.util.Date;
import java.util.List;


/**
 * 用户地址管理接口
 * @author 霍中曦
 * @version 2018-11-12
 */
@MyBatisDao
public interface HykHolidaysDao extends CrudDao<HykHolidays> {
    HykHolidays getHolidaysByDate(Date date);
    Integer insertHolidaysList(List list);
}