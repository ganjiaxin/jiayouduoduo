/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.*;
import com.hyk.code.modules.hyk.entity.HykInsurance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 油大大保险活动service
 * @author
 * @version 2019-02-21
 */
@Service
@Transactional(readOnly = true)
public class HykInsuranceService extends CrudService<HykInsuranceDao, HykInsurance> {
    @Autowired
    private HykInsuranceDao hykInsuranceDao;

    public HykInsurance get(String id) {
        return super.get(id);
    }

    @Transactional(readOnly = false)
    public void save(HykInsurance hykInsurance) {
        hykInsuranceDao.save(hykInsurance);
    }

    public List<HykInsurance> getByUserIdAndGoodsId(String userId, String goodsId){
        return hykInsuranceDao.getByUserIdAndGoodsId(userId,goodsId);
    };//根据参数查

}