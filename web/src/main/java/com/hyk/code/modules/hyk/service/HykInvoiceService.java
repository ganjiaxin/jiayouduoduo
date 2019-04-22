/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import java.util.ArrayList;
import java.util.List;

import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.modules.hyk.entity.HykOilCard;
import com.hyk.code.modules.hyk.entity.HykOrder;
import com.hyk.code.modules.hyk.entity.HykUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.entity.HykInvoice;
import com.hyk.code.modules.hyk.dao.HykInvoiceDao;

/**
 * 发票管理Service
 * @author 霍中曦
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class HykInvoiceService extends CrudService<HykInvoiceDao, HykInvoice> {
	@Autowired
	private HykInvoiceDao hykInvoiceDao;
	@Autowired
	private HykOrderService hykOrderService;

	public HykInvoice get(String id) {
		return super.get(id);
	}
	
	public List<HykInvoice> findList(HykInvoice hykInvoice) {
		return super.findList(hykInvoice);
	}
	
	public Page<HykInvoice> findPage(Page<HykInvoice> page, HykInvoice hykInvoice) {
		return super.findPage(page, hykInvoice);
	}
	
	@Transactional(readOnly = false)
	public void save(HykInvoice hykInvoice) {
		super.save(hykInvoice);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykInvoice hykInvoice) {
		super.delete(hykInvoice);
	}

	@Transactional(readOnly = false)
	public Integer insertInvoiceList(String orderIdStr, HykUser hykUser, HykInvoice hykInvoice){
		List<HykInvoice> list=new ArrayList<>();
		String[] stringArray=orderIdStr.split(",");


		hykInvoice.setUserId(hykUser.getId());
		hykInvoice.setUserPhone(hykUser.getPhone());
		hykInvoice.setStatus("0");

		for(int i=0;i<stringArray.length;i++){
			hykInvoice.setId(ZxingHandler.getUUID());
			HykOrder hykOrder=hykOrderService.get(stringArray[i]);
			hykInvoice.setMoney(hykOrder.getPayableMoney().longValue());

			list.add(hykInvoice);
		}
		return hykInvoiceDao.insertInvoiceList(list);
	};
	
}