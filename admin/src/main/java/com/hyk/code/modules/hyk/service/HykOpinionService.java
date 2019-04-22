/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykGoodsDao;
import com.hyk.code.modules.hyk.dao.HykOpinionDao;
import com.hyk.code.modules.hyk.entity.HykGoods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品管理Service
 * @author 霍中曦
 * @version 2018-11-09
 */
@Service
@Transactional(readOnly = true)
public class HykOpinionService extends CrudService<HykGoodsDao, HykGoods> {
	@Autowired
	private HykOpinionDao hykOpinionDao;
	/**
	 * 保存意见反馈信息
	 */
	public Integer saveOpinion(String userId, String opinion){
		return hykOpinionDao.saveOpinion(userId,opinion);
	};
	
}