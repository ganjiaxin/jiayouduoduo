/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.entity.HykAd;
import com.hyk.code.modules.hyk.dao.HykAdDao;

/**
 * 用户红包管理Service
 * @author 霍中曦
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class HykAdService extends CrudService<HykAdDao, HykAd> {
	@Autowired
	private HykAdDao hykAdDao;

	public HykAd get(String id) {
		return super.get(id);
	}
	
	public List<HykAd> findList(HykAd hykAd) {
		return super.findList(hykAd);
	}
	
	public Page<HykAd> findPage(Page<HykAd> page, HykAd hykAd) {
		return super.findPage(page, hykAd);
	}
	
	@Transactional(readOnly = false)
	public void save(HykAd hykAd) {
		super.save(hykAd);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykAd hykAd) {
		super.delete(hykAd);
	}

	/**
	 * 获取banner中的所有
	 * @return
	 */
	public List All(){
		return hykAdDao.All();
	}
}