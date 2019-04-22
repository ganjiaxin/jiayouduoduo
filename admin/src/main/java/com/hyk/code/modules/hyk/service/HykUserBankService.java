/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykUserBankDao;
import com.hyk.code.modules.hyk.entity.HykUserBank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户银行卡信息Service
 * @author 霍中曦
 * @version 2018-12-06
 */
@Service
@Transactional(readOnly = true)
public class HykUserBankService extends CrudService<HykUserBankDao, HykUserBank> {

	public HykUserBank get(String id) {
		return super.get(id);
	}
	
	public List<HykUserBank> findList(HykUserBank hykUserBank) {
		return super.findList(hykUserBank);
	}
	
	public Page<HykUserBank> findPage(Page<HykUserBank> page, HykUserBank hykUserBank) {
		return super.findPage(page, hykUserBank);
	}
	
	@Transactional(readOnly = false)
	public void save(HykUserBank hykUserBank) {
		super.save(hykUserBank);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykUserBank hykUserBank) {
		super.delete(hykUserBank);
	}
	
}