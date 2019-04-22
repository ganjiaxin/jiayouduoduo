/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykMallGoodsDao;
import com.hyk.code.modules.hyk.dao.HykMessageDao;
import com.hyk.code.modules.hyk.entity.HykMallGoods;
import com.hyk.code.modules.hyk.entity.HykObject;
import com.hyk.code.modules.hyk.entity.HykOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商城商品管理Service
 *
 * @author wyy
 * @version 2018-12-21
 */
@Service
@Transactional(readOnly = true)
public class HykMallGoodsService extends CrudService<HykMallGoodsDao, HykMallGoods> {
    @Autowired
    private HykMallGoodsDao hykMallGoodsDao;

    public HykMallGoods get(String id) {
        return super.get(id);
    }

    public List<HykMallGoods> findList(HykMallGoods hykMallGoods) {
        return super.findList(hykMallGoods);
    }

    public Page<HykMallGoods> findPage(Page<HykMallGoods> page, HykMallGoods hykMallGoods) {
        return super.findPage(page, hykMallGoods);
    }

    @Transactional(readOnly = false)
    public void save(HykMallGoods hykMallGoods) {
        super.save(hykMallGoods);
    }

    @Transactional(readOnly = false)
    public void delete(HykMallGoods hykMallGoods) {
        super.delete(hykMallGoods);
    }


    /**
     * 组合所有商品集合
     * 先查询都有哪些可展示商品模块 根据这些模块查询可展示商品
     *
     * @return
     */
    public List<Map<String, Object>> getCategoryAndCategoryName() {
        List<HykObject> hykObjects = hykMallGoodsDao.getCategoryAndCategoryName();
        List<Map<String, Object>> list = new ArrayList<>();
        //便利hykObjects   hykObject的str1 商品模块(数字)   str2(数字代表的意义)
        for (HykObject hykObject : hykObjects) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", hykObject.getStr2());
            map.put("value", hykObject.getStr1());
            List<HykMallGoods> list1 = hykMallGoodsDao.getMallGoodsList(hykObject.getStr1());
            for (int i = 0; i < list1.size(); i++) {
                //富文本转译
                String a = HtmlUtils.htmlUnescape(list1.get(i).getIntrocture());
                list1.get(i).setIntrocture(a);
                String b = HtmlUtils.htmlUnescape(list1.get(i).getMess());
                list1.get(i).setMess(b);
            }
            map.put("goodsList", list1);
            //添加非热卖非加油卡集合
            list.add(map);
        }
        Map hotMap = hotMap();
        if (!hotMap.isEmpty()) {
            //添加热卖集合 热卖有自己的逻辑
            list.add(hotMap);
        }
        return exchangeList(list);
    }


    /**
     * 组合所有商品集合 给android
     * 先查询都有哪些可展示商品模块 根据这些模块查询可展示商品
     *
     * @return
     */
    public List<Map<String, Object>> getCategoryAndCategoryNametoAndroid() {
        List<HykObject> hykObjects = hykMallGoodsDao.getCategoryAndCategoryName();
        List<Map<String, Object>> list = new ArrayList<>();
        //便利hykObjects   hykObject的str1 商品模块(数字)   str2(数字代表的意义)
        for (HykObject hykObject : hykObjects) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", hykObject.getStr2());
            map.put("value", hykObject.getStr1());
            List<HykMallGoods> list1 = hykMallGoodsDao.getMallGoodsList(hykObject.getStr1());

            map.put("goodsList", list1);
            list.add(map);
        }
        Map hotMap = hotMaptoAndroid();
        if (!hotMap.isEmpty()) {
            list.add(hotMap);//添加热卖集合 热卖有自己的逻辑
        }
        return exchangeList(list);
    }

    /**
     * 热卖模块内容  如果热卖模块下有商品  把费热卖模块hot标签商品显示到热卖模块一份
     * 没有商品  只显示热卖标签商品
     *
     * @return
     */
    public Map<String, Object> hotMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", hykMallGoodsDao.getValueByValueAndType("9", "mall_category"));
        map.put("value", "9");
        List<HykMallGoods> hotList = hykMallGoodsDao.getMallGoodsList("9");//热卖模块下商品
        for (HykMallGoods hykMallGoods : hotList) {
            hykMallGoods.setIshot("1");
            String a = HtmlUtils.htmlUnescape(hykMallGoods.getIntrocture());//富文本转译
            hykMallGoods.setIntrocture(a);
            String b = HtmlUtils.htmlUnescape(hykMallGoods.getMess());
            hykMallGoods.setMess(b);
        }

        List<HykMallGoods> list2 = hykMallGoodsDao.getHotGoods("9");//非热卖模块下的hot商品
        for (HykMallGoods hykMallGoods : list2) {
            String a = HtmlUtils.htmlUnescape(hykMallGoods.getIntrocture());//富文本转译
            hykMallGoods.setIntrocture(a);
            String b = HtmlUtils.htmlUnescape(hykMallGoods.getMess());
            hykMallGoods.setMess(b);
        }

        if (hotList.size() > 0) {
            hotList.addAll(list2);
            map.put("goodsList", hotList);
            return map;
        }
        if (list2.size() > 0) {
            map.put("goodsList", list2);
            return map;
        }
        return new HashMap<>();
    }

    /**
     * 热卖模块内容  如果热卖模块下有商品  把费热卖模块hot标签商品显示到热卖模块一份
     * 没有商品  只显示热卖标签商品
     *
     * @return
     */
    public Map<String, Object> hotMaptoAndroid() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "热卖");
        map.put("value", "9");
        List<HykMallGoods> hotList = hykMallGoodsDao.getMallGoodsList("9");//热卖模块下商品
        for (HykMallGoods hykMallGoods : hotList) {
            hykMallGoods.setIshot("1");
        }
        List list2 = hykMallGoodsDao.getHotGoods("9");//非热卖模块下的hot商品
        if (hotList.size() > 0) {
            hotList.addAll(list2);
            map.put("goodsList", hotList);
            return map;
        }
        if (list2.size() > 0) {
            map.put("goodsList", list2);
            return map;
        }
        return new HashMap<>();
    }


    public List exchangeList(List<Map<String, Object>> list) {//热卖模块位置排序
        List<Map<String, Object>> returnList = new ArrayList<>();
        Map<String, Object> firstMap = new HashMap<>();//0下标map内容
        Map<String, Object> hotMap = new HashMap<>();//热卖map内容
        int index = -1;//热卖map的下标
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                firstMap = list.get(i);
            }
            String value = list.get(i).get("value").toString();
            if (value.equals("9")) {
                index = i;
                hotMap = list.get(i);
            }
        }
        if (index != -1) {
            list.set(0, hotMap);
            list.set(index, firstMap);
        }
        return list;
    }

}