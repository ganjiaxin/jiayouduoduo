/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykPayinfoDao;
import com.hyk.code.modules.hyk.entity.HykPayinfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 支付报文Service
 * @author 霍中曦
 * @version 2018-12-06
 */
@Service
@Transactional(readOnly = true)
public class HykPayinfoService extends CrudService<HykPayinfoDao, HykPayinfo> {

	public HykPayinfo get(String id) {
		return super.get(id);
	}
	
	public List<HykPayinfo> findList(HykPayinfo hykPayinfo) {
		return super.findList(hykPayinfo);
	}
	
	public Page<HykPayinfo> findPage(Page<HykPayinfo> page, HykPayinfo hykPayinfo) {
		return super.findPage(page, hykPayinfo);
	}
	
	@Transactional(readOnly = false)
	public void save(HykPayinfo hykPayinfo) {
		super.save(hykPayinfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykPayinfo hykPayinfo) {
		super.delete(hykPayinfo);
	}
	
}