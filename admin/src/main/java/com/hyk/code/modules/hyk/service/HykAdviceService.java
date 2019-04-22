/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.entity.HykAdvice;
import com.hyk.code.modules.hyk.dao.HykAdviceDao;

/**
 * 意见反馈管理Service
 * @author 霍中曦
 * @version 2018-12-11
 */
@Service
@Transactional(readOnly = true)
public class HykAdviceService extends CrudService<HykAdviceDao, HykAdvice> {

	public HykAdvice get(String id) {
		return super.get(id);
	}
	
	public List<HykAdvice> findList(HykAdvice hykAdvice) {
		return super.findList(hykAdvice);
	}
	
	public Page<HykAdvice> findPage(Page<HykAdvice> page, HykAdvice hykAdvice) {
		return super.findPage(page, hykAdvice);
	}
	
	@Transactional(readOnly = false)
	public void save(HykAdvice hykAdvice) {
		super.save(hykAdvice);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykAdvice hykAdvice) {
		super.delete(hykAdvice);
	}
	
}