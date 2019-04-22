package com.hyk.code.modules.hyk.service;

import java.util.ArrayList;
import java.util.List;

import com.hyk.code.modules.hyk.entity.HykNotice;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.entity.HykAd;
import com.hyk.code.modules.hyk.dao.HykAdDao;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 用户红包管理Service
 * @author 霍中曦
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class HykAdService extends CrudService<HykAdDao, HykAd> {
	@Autowired
	private HykAdDao hykAdDao;

	public HykAd get(String id) {
		return super.get(id);
	}
	
	public List<HykAd> findList(HykAd hykAd) {
		return super.findList(hykAd);
	}
	
	public Page<HykAd> findPage(Page<HykAd> page, HykAd hykAd) {
		return super.findPage(page, hykAd);
	}
	
	@Transactional(readOnly = false)
	public void save(HykAd hykAd) {
		super.save(hykAd);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykAd hykAd) {
		super.delete(hykAd);
	}

	/**
	 * 获取banner中的所有
	 * @return
	 */
	public List All(){
		return hykAdDao.All();
	}

	/**
	 * 获取所有存在wondeful_img
	 * @return
	 */
	public List<HykAd> wondefulImgAll(Integer currPage,Integer pageSize){
		List<HykAd> list=hykAdDao.wondefulImg();
		List<HykAd> listEnd=hykAdDao.wondefulImgEnd();
		list.addAll(listEnd);
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
	public List<HykAd> wondefulImg(){
		return hykAdDao.wondefulImg();
	};//所有可用精彩活动

	/**
	 * 定时任务
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = {Exception.class})
	public Integer updateStaus(){
		try {
			return hykAdDao.updateStaus();
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
	};

	/**
	 * //app广告弹窗
	 * @return HykAd
	 */
	public HykAd getHykAdAppImg(){
		return hykAdDao.getHykAdAppImg();
	};

	/**
	 * //分享
	 * @return
	 */
	public List<HykAd> getHykAdShareImg(){
		return hykAdDao.getHykAdShareImg();
	};

	/**
	 * //开屏广告
	 * @return
	 */
	public HykAd getHykAdOpenImg(){
		return hykAdDao.getHykAdOpenImg();
	};
	public List<HykAd> goodsImg(){
		return hykAdDao.goodsImg();
	};//商城bannner
}