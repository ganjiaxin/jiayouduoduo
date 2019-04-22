/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykUserDao;
import com.hyk.code.modules.hyk.entity.HykUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理Service
 * @author 霍中曦
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class HykUserService extends CrudService<HykUserDao, HykUser> {
	@Autowired
	private HykUserDao hykUserDao;


	public HykUser get(String id) {
		return super.get(id);
	}
	
	public List<HykUser> findList(HykUser hykUser) {
		return super.findList(hykUser);
	}
	
	public Page<HykUser> findPage(Page<HykUser> page, HykUser hykUser) {
		return super.findPage(page, hykUser);
	}
	

	@Transactional(readOnly = false)
	public void delete(HykUser hykUser) {
		super.delete(hykUser);
	}


	public HykUser getByPhone(HykUser hykUser) {
		return dao.getByPhone(hykUser);
	}



	/**
	 * 更新用户登录错误次数
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public Integer updateAccount(String id){
		return hykUserDao.updateAccount(id);
	};



	/**
	 * 更新用户验证码错误次数
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public Integer updateCode(String id){
		return hykUserDao.updateCode(id);
	};


	/**
	 * 查询个人中心显示的信息
	 * @param id
	 * @return
	 */
	public HykUser queryInfo(String id){
		return hykUserDao.queryInfo(id);
	};


	/**
	 * 修改密码
	 * @param hykUser
	 * @return
	 */
	@Transactional(readOnly = false)
	public Integer updatePwd(HykUser hykUser){
		return hykUserDao.updatePwd(hykUser);
	};
}