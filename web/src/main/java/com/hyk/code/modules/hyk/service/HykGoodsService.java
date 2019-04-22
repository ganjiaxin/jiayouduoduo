package com.hyk.code.modules.hyk.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.entity.HykGoods;
import com.hyk.code.modules.hyk.dao.HykGoodsDao;

/**
 * 商品管理Service
 * @author 霍中曦
 * @version 2018-11-09
 */
@Service
@Transactional(readOnly = true)
public class HykGoodsService extends CrudService<HykGoodsDao, HykGoods> {

	@Autowired
	private HykGoodsDao hykGoodsDao;

	public HykGoods get(String id) {
		return super.get(id);
	}
	
	public List<HykGoods> findList(HykGoods hykGoods) {
		return super.findList(hykGoods);
	}
	
	public Page<HykGoods> findPage(Page<HykGoods> page, HykGoods hykGoods) {
		return super.findPage(page, hykGoods);
	}
	
	@Transactional(readOnly = false)
	public void save(HykGoods hykGoods) {

		super.save(hykGoods);
	}
	
	@Transactional(readOnly = false)
	public void delete(HykGoods hykGoods) {
		super.delete(hykGoods);
	}
	/**
	 *根据类型查商品
	 * @param type 商品类型 1及时充值0加油套餐
	 * @return
	 */
	public List getByType(Integer type) {
		return hykGoodsDao.getByType(type);
	}


	/**
	 * 首页充值商品显示
	 * @param type  商品类型 1及时充值0加油套餐
	 * @return
	 */
	public List<HykGoods> getByTypeIndex(Integer type){
		return hykGoodsDao.getByTypeIndex(type);
	};

	/**
	 * 根据商品id查
	 * @param id
	 * @return
	 */
	public HykGoods getGoodsById(String id){
		return hykGoodsDao.getGoodsById(id);
	};

	/**
	 * 更新库存数量
	 * @param goodsId
	 * @return
	 */
	@Transactional(readOnly = false)
	public Integer updateStock(String  goodsId){
		return hykGoodsDao.updateStock(goodsId);
	};
}