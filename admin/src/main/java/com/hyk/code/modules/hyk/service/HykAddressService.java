/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykAddressDao;
import com.hyk.code.modules.hyk.entity.HykAddress;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
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
public class HykAddressService extends CrudService<HykAddressDao, HykAddress> {
	@Autowired
	private HykAddressDao hykAddressDao;

	public HykAddress get(String id) {
		return super.get(id);
	}

	public List<HykAddress> findList(HykAddress hykAddress) {
		return super.findList(hykAddress);
	}

	public Page<HykAddress> findPage(Page<HykAddress> page, HykAddress hykAddress) {
		return super.findPage(page, hykAddress);
	}

	@Transactional(readOnly = false)
	public void save(HykAddress hykAddress) {
		super.save(hykAddress);
	}

	@Transactional(readOnly = false)
	public void delete(HykAddress hykAddress) {
		super.delete(hykAddress);
	}

	/**
	 * 获取该用户的所有地址
	 * @param userId
	 * @return
	 */
	public List<HykAddress> getListByUserId(String userId){
		return hykAddressDao.getListByUserId(userId);
	};

	/**
	 * 根据地址id  查询地址信息
	 * @param AddressId
	 * @return
	 */
	public HykAddress getByAddressId(String AddressId){
		return hykAddressDao.getByAddressId(AddressId);
	};

	/**
	 * 更新地址信息
	 * @param
	 * @return
	 */
	@Transactional(readOnly = false)
	public Integer updateAddress(HykAddress hykAddress ){
		return hykAddressDao.updateAddress(hykAddress);
	};

	/**
	 * 更新该用户的所有地址为非默认
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	public Integer updateNotDefault(String userId){
		return hykAddressDao.updateNotDefault(userId);
	};

	/**
	 * 删除地址
	 */
	@Transactional(readOnly = false)
	public Integer softDeleteAddress(String addressId){
		return hykAddressDao.softDeleteAddress(addressId);
	};


	/**
	 * 添加地址
	 */
	@Transactional(readOnly = false)
	public Integer addAddress(HykAddress hykAddress){
		return hykAddressDao.addAddress(hykAddress);
	};

}