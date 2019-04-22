/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykMessage;

import java.util.List;

/**
 * 站内信管理DAO接口
 * @author 霍中曦
 * @version 2018-12-18
 */
@MyBatisDao
public interface HykMessageDao extends CrudDao<HykMessage> {
    /**
     * 更新状态为已读
     * @param id 站内信id
     * @return
     */
    Integer updateStatusById(String id);

    /**
     * 更新状态为已读
     * @param phone
     * @return
     */
    Integer updateStatusByPhone(String phone);

    /**
     * 获取所有
     * @param phone
     * @return
     */
    List<HykMessage> getAll(String phone);

    /**
     * 获取最新标题
     * @param phone
     * @return
     */
    HykMessage getNewTitle(String phone);

    /**
     * 获取未读站内信数量
     * @param phone
     * @return
     */
    Integer getUnreadCount(String phone);
}