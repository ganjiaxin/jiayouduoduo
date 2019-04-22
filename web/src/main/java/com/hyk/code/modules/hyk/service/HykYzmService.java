/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.entity.HykYzm;
import com.hyk.code.modules.hyk.dao.HykYzmDao;

/**
 * 验证码管理Service
 * @author 霍中曦
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class HykYzmService extends CrudService<HykYzmDao, HykYzm> {
	@Autowired
	private HykYzmDao hykYzmDao;

	public HykYzm get(String id) {
		return super.get(id);
	}
	
	public List<HykYzm> findList(HykYzm hykYzm) {
		return super.findList(hykYzm);
	}
	
	public Page<HykYzm> findPage(Page<HykYzm> page, HykYzm hykYzm) {
		return super.findPage(page, hykYzm);
	}
	
	@Transactional(readOnly = false)
	public void save(HykYzm hykYzm) {
		super.save(hykYzm);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykYzm hykYzm) {
		super.delete(hykYzm);
	}


	/**
	 * 用过手机号获得最新的验证码对象
	 * @param phone
	 * @return
	 */
	public HykYzm queryByPhone(String phone){
		return hykYzmDao.queryByPhone(phone);
	};


	/**
	 * 保存验证码信息
	 * @param hykYzm
	 * @return
	 */
	@Transactional(readOnly = false)
	public Integer saveYzm(HykYzm hykYzm){
		return hykYzmDao.saveYzm(hykYzm);
	};
	public Integer queryCountByIp(String ip){
		return hykYzmDao.queryCountByIp(ip);
	};//同一ip当日请求次数
}