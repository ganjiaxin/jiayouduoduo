/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykNotice;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 公告管理DAO接口
 * @author 霍中曦
 * @version 2018-11-12
 */
@MyBatisDao
public interface HykNoticeDao extends CrudDao<HykNotice> {

    /**
     * 所有公告
     * @return
     */
    @Select("select * from hyk_notice ")
    List<HykNotice> findAll();
}