/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.entity.HykRedpackage;
import com.hyk.code.modules.hyk.dao.HykRedpackageDao;

/**
 * 红包管理Service
 * @author 霍中曦
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class HykRedpackageService extends CrudService<HykRedpackageDao, HykRedpackage> {
	@Autowired
	private HykRedpackageDao hykRedpackageDao;

	public HykRedpackage get(String id) {
		return super.get(id);
	}
	
	public List<HykRedpackage> findList(HykRedpackage hykRedpackage) {
		return super.findList(hykRedpackage);
	}
	
	public Page<HykRedpackage> findPage(Page<HykRedpackage> page, HykRedpackage hykRedpackage) {
		return super.findPage(page, hykRedpackage);
	}
	
	@Transactional(readOnly = false)
	public void save(HykRedpackage hykRedpackage) {
		super.save(hykRedpackage);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykRedpackage hykRedpackage) {
		super.delete(hykRedpackage);
	}

	/**
	 * 查询个人所有红包信息
	 * @param userId
	 * @return
	 */
	public List<HykRedpackage> queryAll(String userId,Integer currPage, Integer pageSize){
		//查询全部数据
		List<HykRedpackage> list = hykRedpackageDao.queryAll(userId);
		if(list.size()<(currPage - 1) * pageSize){
			return new ArrayList<>();
		}
		//从第几条数据开始
		int firstIndex = (currPage - 1) * pageSize;
		//到第几条数据结束
		int lastIndex = currPage * pageSize;
		if(lastIndex>list.size()){
			lastIndex=list.size();
		}
		return list.subList(firstIndex, lastIndex); //直接在list中截取
	};
	public Integer queryAllCount(String userId){
		return hykRedpackageDao.queryAllCount(userId);
	};

	/**
	 * 查询个人所有status=0红包信息
	 * @param userId
	 * @return
	 */
	public List<HykRedpackage> queryAllCan(String userId,String redType){
		return hykRedpackageDao.queryAllCan(userId,redType);
	};

	/**
	 * 查询个人所有status=0红包个数
	 * @param userId
	 * @return
	 */
	public Integer queryAllCanCount(String userId,String redType){
		return hykRedpackageDao.queryAllCanCount(userId,redType);
	};


	/**
	 * 更新红包状态
	 * @param status
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public Integer updateStatusById(String status,  String id){
		return hykRedpackageDao.updateStatusById(status,id);
	};
}