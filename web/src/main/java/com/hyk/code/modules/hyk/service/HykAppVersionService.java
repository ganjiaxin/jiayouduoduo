package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykAppVersionDao;
import com.hyk.code.modules.hyk.entity.HykAppVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 版本管理Service
 * @author 霍中曦
 * @version 2018-12-18
 */
@Service
@Transactional(readOnly = true)
public class HykAppVersionService extends CrudService<HykAppVersionDao, HykAppVersion> {
	@Autowired
	private HykAppVersionDao hykAppVersionDao;

	public HykAppVersion get(String id) {
		return super.get(id);
	}
	
	public List<HykAppVersion> findList(HykAppVersion hykAppVersion) {
		return super.findList(hykAppVersion);
	}
	
	public Page<HykAppVersion> findPage(Page<HykAppVersion> page, HykAppVersion hykAppVersion) {
		return super.findPage(page, hykAppVersion);
	}
	
	@Transactional(readOnly = false)
	public void save(HykAppVersion hykAppVersion) {
		super.save(hykAppVersion);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykAppVersion hykAppVersion) {
		super.delete(hykAppVersion);
	}

	/**
	 * //获取该操作系统最新版本
	 * @param system
	 * @return
	 */
	public HykAppVersion getBySystem(String system){
		return hykAppVersionDao.getBySystem(system);
	};
}