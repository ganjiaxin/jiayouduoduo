/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykRechargeCardDao;
import com.hyk.code.modules.hyk.entity.HykRechargeCard;
import com.hyk.code.modules.sys.dao.DictDao;
import com.hyk.code.modules.sys.entity.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 充值卡管理Service
 * @author 霍中曦
 * @version 2018-12-18
 */
@Service
@Transactional(readOnly = true)
public class HykRechargeCardService extends CrudService<HykRechargeCardDao, HykRechargeCard> {

	@Autowired
	protected DictDao dictDao;

	public HykRechargeCard get(String id) {
		return super.get(id);
	}
	
	public List<HykRechargeCard> findList(HykRechargeCard hykRechargeCard) {
		return super.findList(hykRechargeCard);
	}
	
	public Page<HykRechargeCard> findPage(Page<HykRechargeCard> page, HykRechargeCard hykRechargeCard) {
		return super.findPage(page, hykRechargeCard);
	}
	
	@Transactional(readOnly = false)
	public void save(HykRechargeCard hykRechargeCard) {
		super.save(hykRechargeCard);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykRechargeCard hykRechargeCard) {
		super.delete(hykRechargeCard);
	}

	@Transactional(readOnly = false)
	public void saveBatch(List<HykRechargeCard> list) {
		dao.saveBatch(list);
		Dict dict=new Dict();
		dict.setType("card_code");
		List<Dict> dicList=dictDao.findList(dict);
		dicList.get(0).setValue(list.get(0).getCode());
		dictDao.update(dicList.get(0));
	}

	@Transactional(readOnly = false)
	public void updateBatch(HykRechargeCard hykRechargeCard) {
		dao.updateBatch(hykRechargeCard);
	}


	
}