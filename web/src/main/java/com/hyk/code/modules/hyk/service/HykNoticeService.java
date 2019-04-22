/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import java.util.ArrayList;
import java.util.List;

import com.hyk.code.modules.hyk.entity.HykOrder;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.entity.HykNotice;
import com.hyk.code.modules.hyk.dao.HykNoticeDao;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
	public List findAll(Integer currPage, Integer pageSize){
		List<HykNotice> list=hykNoticeDao.findAll();
		if (list.size() < (currPage - 1) * pageSize) {
			return new ArrayList<>();
		}
		int firstIndex = (currPage - 1) * pageSize;//从第几条数据开始
		int lastIndex = currPage * pageSize;//到第几条数据结束
		if (lastIndex > list.size()) {
			lastIndex = list.size();
		}
		return list.subList(firstIndex, lastIndex); //直接在list中截取
	};

	/**
	 * 返回最新的标题
	 * @return
	 */
	public HykNotice findNewestTitle(){
		return hykNoticeDao.findNewestTitle();
	};

	/**
	 * 定时任务
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = {Exception.class})
	public Integer  updateStatus(){
		try {
			return hykNoticeDao.updateStatus();
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
	};

}