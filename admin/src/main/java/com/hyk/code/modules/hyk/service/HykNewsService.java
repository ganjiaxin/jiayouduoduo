/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykNewsDao;
import com.hyk.code.modules.hyk.entity.HykNews;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 新闻管理Service
 * @author 霍中曦
 * @version 2019-03-07
 */
@Service
@Transactional(readOnly = true)
public class HykNewsService extends CrudService<HykNewsDao, HykNews> {

	public HykNews get(String id) {
		return super.get(id);
	}
	
	public List<HykNews> findList(HykNews hykNews) {
		return super.findList(hykNews);
	}
	
	public Page<HykNews> findPage(Page<HykNews> page, HykNews hykNews) {
		return super.findPage(page, hykNews);
	}
	
	@Transactional(readOnly = false)
	public void save(HykNews hykNews) {
		super.save(hykNews);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykNews hykNews) {
		super.delete(hykNews);
	}
	
}