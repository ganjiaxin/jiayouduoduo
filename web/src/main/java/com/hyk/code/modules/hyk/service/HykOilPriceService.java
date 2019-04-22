/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.common.utils.OilPriceUtil;
import com.hyk.code.modules.hyk.dao.HykAdDao;
import com.hyk.code.modules.hyk.dao.HykOilPriceDao;
import com.hyk.code.modules.hyk.entity.HykAd;
import com.hyk.code.modules.hyk.entity.HykOilPrice;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 今日油价管理Service
 *
 * @author 霍中曦
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class HykOilPriceService extends CrudService<HykOilPriceDao, HykOilPrice> {
    @Autowired
    private HykOilPriceDao hykOilPricedao;

    public void insertOilPriceList() {
        Object object = OilPriceUtil.getRequest1();
        if (!StringUtils.isEmpty(object)) {
            JSONArray jsonArray = JSONArray.fromObject(object);
            if (jsonArray.size() > 0) {

                List list = new ArrayList();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject job = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    System.out.println(job.get("name") + "=");  // 得到 每个对象中的属性值
                    HykOilPrice hykOilPrice = new HykOilPrice();
                    hykOilPrice.setCity(job.get("city").toString());
                    hykOilPrice.setB90(job.get("b90").toString());
                    hykOilPrice.setB93(job.get("b93").toString());
                    hykOilPrice.setB97(job.get("b97").toString());
                    hykOilPrice.setB0(job.get("b0").toString());
                    hykOilPrice.setH92(job.get("92h").toString());
                    hykOilPrice.setH95(job.get("95h").toString());
                    hykOilPrice.setH98(job.get("98h").toString());
                    hykOilPrice.setH0(job.get("0h").toString());
                    list.add(hykOilPrice);
                }
                hykOilPricedao.insertOilPriceList(list);
            }
        }
    }

    ;//保存当日31个省份今日油价

    public HykOilPrice getByCity(String city) {
        return hykOilPricedao.getByCity(city);
    };//根据城市查询最新的油价

    public List<String> getAllCityName(){
        return hykOilPricedao.getAllCityName();
    };//31个省份名
}