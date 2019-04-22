/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykRechargeCard;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 充值卡管理DAO接口
 * @author 霍中曦
 * @version 2018-12-18
 */
@MyBatisDao
public interface HykRechargeCardDao extends CrudDao<HykRechargeCard> {

    HykRechargeCard getByTwoType(@Param("caredno")String caredno,@Param("password")String password);//用户名密码查询对象
	Integer updateSaleStatusById(String id);//更新状态为已使用
    List<Map<String, Object>> selectNoPassword(@Param("money") BigDecimal money, @Param("num") Integer num);
    Integer updateSaleStatusById2(@Param("id") String id, @Param("overDate") Date overDate);
}