/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykMallGoods;
import com.hyk.code.modules.hyk.entity.HykObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 商城商品管理DAO接口
 * @author wyy
 * @version 2018-12-21
 */
@MyBatisDao
public interface HykMallGoodsDao extends CrudDao<HykMallGoods> {

    List<HykObject> getCategoryAndCategoryName();//获取所有商品模块及模块名称 不包括加油卡模块5  不包括热卖模块9

    List<HykMallGoods> getMallGoodsList(String category);//该模块下的可展示商品
    List<HykMallGoods> getMallGoodsListToAndroid(String category);//该模块下的可展示商品给安卓

    Integer updateStockById(@Param("num")Integer num ,@Param("id")String id);//更新商品数量

    List<HykMallGoods> getHotGoods(String category);//非热卖模块下的hot商品

    @Select("SELECT `value` from sys_dict where del_flag=0 and label=#{label} and type =#{type}")
    String getValueByLabelAndType(@Param("label")String label ,@Param("type")String type);
    @Select("SELECT label from sys_dict where del_flag=0 and value=#{value} and type=#{type}")
    String getValueByValueAndType(@Param("value")String value ,@Param("type")String type);
}