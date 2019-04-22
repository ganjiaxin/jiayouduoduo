/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.entity.HykNotice;
import com.hyk.code.modules.hyk.dao.HykNoticeDao;

/**
 * 公告管理Service
 * @author 霍中曦
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class HykNoticeService extends CrudService<HykNoticeDao, HykNotice> {
	@Autowired
	private HykNoticeDao hykNoticeDao;

	public HykNotice get(String id) {
		return super.get(id);
	}
	
	public List<HykNotice> findList(HykNotice hykNotice) {
		return super.findList(hykNotice);
	}
	
	public Page<HykNotice> findPage(Page<HykNotice> page, HykNotice hykNotice) {
		return super.findPage(page, hykNotice);
	}
	
	@Transactional(readOnly = false)
	public void save(HykNotice hykNotice) {
		super.save(hykNotice);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykNotice hykNotice) {
		super.delete(hykNotice);
	}

	/**
	 * 所有公告
	 * @return
	 */
	public List<HykNotice> findAll(){
		return hykNoticeDao.findAll();
	};
}