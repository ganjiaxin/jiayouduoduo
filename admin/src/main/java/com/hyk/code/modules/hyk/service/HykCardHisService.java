/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykCardHisDao;
import com.hyk.code.modules.hyk.entity.HykCardHis;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 充值卡兑换记录Service
 * @author 霍中曦
 * @version 2018-12-18
 */
@Service
@Transactional(readOnly = true)
public class HykCardHisService extends CrudService<HykCardHisDao, HykCardHis> {

	public HykCardHis get(String id) {
		return super.get(id);
	}
	
	public List<HykCardHis> findList(HykCardHis hykCardHis) {
		return super.findList(hykCardHis);
	}
	
	public Page<HykCardHis> findPage(Page<HykCardHis> page, HykCardHis hykCardHis) {
		return super.findPage(page, hykCardHis);
	}
	
	@Transactional(readOnly = false)
	public void save(HykCardHis hykCardHis) {
		super.save(hykCardHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykCardHis hykCardHis) {
		super.delete(hykCardHis);
	}
	
}