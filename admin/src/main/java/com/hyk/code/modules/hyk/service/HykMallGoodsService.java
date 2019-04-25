/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykMallGoodsDao;
import com.hyk.code.modules.hyk.entity.HykMallGoods;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商城商品管理Service
 * @author wyy
 * @version 2018-12-21
 */
@Service
@Transactional(readOnly = true)
public class HykMallGoodsService extends CrudService<HykMallGoodsDao, HykMallGoods> {


	public HykMallGoods get(String id) {
		return super.get(id);
	}
	
	public List<HykMallGoods> findList(HykMallGoods hykMallGoods) {
		return super.findList(hykMallGoods);
	}
	
	public Page<HykMallGoods> findPage(Page<HykMallGoods> page, HykMallGoods hykMallGoods) {
		return super.findPage(page, hykMallGoods);
	}
	
	@Transactional(readOnly = false)
	public void save(HykMallGoods hykMallGoods) {
		super.save(hykMallGoods);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykMallGoods hykMallGoods) {
		super.delete(hykMallGoods);
	}
	
}