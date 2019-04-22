/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykMallOrder;

import java.util.List;

/**
 * 商城商品订单DAO接口
 * @author wyy
 * @version 2018-12-21
 */
@MyBatisDao
public interface HykMallOrderDao extends CrudDao<HykMallOrder> {

    Integer updateSendStatusByOrderno(String orderno);//修改订单状态为已发送

    public List<HykMallOrder> findList2(HykMallOrder hykMallOrder);
}