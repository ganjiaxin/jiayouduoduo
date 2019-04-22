/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.HykUserImg;
import com.hyk.code.modules.hyk.dao.HykAddressDao;
import com.hyk.code.modules.hyk.dao.HykUserImgDao;
import com.hyk.code.modules.hyk.entity.HykAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户地址管理Service
 * @author 霍中曦
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class HykUserImgService extends CrudService<HykUserImgDao, HykUserImg> {

	@Autowired
	private HykUserImgDao hykUserImgDao;


	public Integer insert(String userId,String AdId){
		return hykUserImgDao.insert(userId,AdId);
	};
	@Transactional(readOnly = false)
	public Integer queryOne(String userId,String AdId){
		return hykUserImgDao.queryOne(userId,AdId);
	};
}