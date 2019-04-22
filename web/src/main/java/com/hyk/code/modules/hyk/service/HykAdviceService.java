package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykAdviceDao;
import com.hyk.code.modules.hyk.entity.HykAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品管理Service
 * @author 霍中曦
 * @version 2018-11-09
 */
@Service
@Transactional(readOnly = true)
public class HykAdviceService extends CrudService<HykAdviceDao, HykAdvice> {
	@Autowired
	private HykAdviceDao hykAdviceDao;
	/**
	 * 保存意见反馈信息
	 */
	@Transactional(readOnly = false)
	public Integer saveOpinion(String id,String content, String imgs,String phone){
		return hykAdviceDao.saveOpinion(id,content,imgs,phone);
	};
	
}