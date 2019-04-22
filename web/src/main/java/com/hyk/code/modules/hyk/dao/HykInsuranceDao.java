/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykAd;
import com.hyk.code.modules.hyk.entity.HykInsurance;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 油大大保险活动dao
 * @author
 * @version 2019-02-21
 */
@MyBatisDao
public interface HykInsuranceDao extends CrudDao<HykInsurance> {
    Integer save(HykInsurance hykInsurance);
    List<HykInsurance> getByUserIdAndGoodsId(@Param("userId")String userId,@Param("goodsId")String goodsId);//根据参数查
	
}