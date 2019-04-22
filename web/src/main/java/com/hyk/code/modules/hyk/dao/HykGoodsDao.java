/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykGoods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 商品管理DAO接口
 * @author 霍中曦
 * @version 2018-11-09
 */
@MyBatisDao
public interface HykGoodsDao extends CrudDao<HykGoods> {
    /**
     *根据类型查商品
     * @param type 商品类型 1及时充值0加油套餐
     * @return
     */
    @Select("SELECT * from hyk_goods where `status`=1 and goods_type=#{type} and del_flag=0 ORDER BY val desc")
	List<HykGoods> getByType(Integer type);

    /**
     * 首页充值商品显示
     * @param type  商品类型 1及时充值0加油套餐
     * @return
     */
    @Select("SELECT * from hyk_goods where status =1 and goods_type=#{type} and del_flag=0 and LENGTH(label)>0 order by istop desc ,label asc ")
    List<HykGoods> getByTypeIndex(Integer type);

    /**
     * 根据商品id查
     * @param id
     * @return
     */
    @Select("select * from hyk_goods where id=#{id}")
    HykGoods getGoodsById(String id);

    /**
     * 更新库存数量
     * @param goodsId
     * @return
     */
    @Update("update hyk_goods set stock=stock-1 where id=#{goodsId}")
    Integer updateStock(String goodsId);
}