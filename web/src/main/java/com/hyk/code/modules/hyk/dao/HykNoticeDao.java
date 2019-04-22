/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykNotice;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Select(" select id as id,title as title, FROM_UNIXTIME(UNIX_TIMESTAMP(start_time),'%Y-%m-%d %H:%i:%s') as startTimeStr,content as content ," +
            " FROM_UNIXTIME(UNIX_TIMESTAMP(start_time),'%Y-%m-%d') as startTimeStrShort from hyk_notice where del_flag=0 and status=1 and NOW() BETWEEN start_time and end_time ORDER BY start_time desc ")
    List<HykNotice> findAll();

    /**
     * 返回最新的标题
     * @return
     */
    @Select("select title as title ,id as id from hyk_notice where del_flag=0 and status=1 and NOW() BETWEEN start_time and end_time  ORDER BY start_time desc limit 1 ")
    HykNotice findNewestTitle();

    @Update("update hyk_notice set status=0 where status=1 and UNIX_TIMESTAMP(NOW())>=UNIX_TIMESTAMP(end_time)")
    Integer updateStatus();
}