/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.modules.hyk.dao.HykCardHisDao;
import com.hyk.code.modules.hyk.dao.HykRechargeCardDao;
import com.hyk.code.modules.hyk.dao.HykRedpackageDao;
import com.hyk.code.modules.hyk.dao.HykUserDao;
import com.hyk.code.modules.hyk.entity.HykCardHis;
import com.hyk.code.modules.hyk.entity.HykRechargeCard;
import com.hyk.code.modules.hyk.entity.HykUser;
import com.hyk.code.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 充值卡管理Service
 *
 * @author 霍中曦
 * @version 2018-12-18
 */
@Service
@Transactional(readOnly = true)
public class HykRechargeCardService extends CrudService<HykRechargeCardDao, HykRechargeCard> {
    @Autowired
    private HykRechargeCardDao hykRechargeCardDao;
    @Autowired
    private HykUserDao hykUserDao;
    @Autowired
    private HykCardHisDao hykCardHisDao;

    public HykRechargeCard get(String id) {
        return super.get(id);
    }

    public List<HykRechargeCard> findList(HykRechargeCard hykRechargeCard) {
        return super.findList(hykRechargeCard);
    }

    public Page<HykRechargeCard> findPage(Page<HykRechargeCard> page, HykRechargeCard hykRechargeCard) {
        return super.findPage(page, hykRechargeCard);
    }

    @Transactional(readOnly = false)
    public void save(HykRechargeCard hykRechargeCard) {
        super.save(hykRechargeCard);
    }

    @Transactional(readOnly = false)
    public void delete(HykRechargeCard hykRechargeCard) {
        super.delete(hykRechargeCard);
    }


    public HykRechargeCard getByTwoType(String caredno, String password) {
        return hykRechargeCardDao.getByTwoType(caredno, password);
    }

    /**
     * 余额充值
     *
     * @return 1 充值卡状态更新为已兑换saleStatus 改为1
     * 2 更新user表中剩余金额 总充值金额
     * 3 添加一条数据到充值记录表中...
     */
    @Transactional(readOnly = false)
    public boolean balanceRechargeFun(HykRechargeCard hykRechargeCard, HykUser hykUser) throws Exception {
        boolean b = false;
        Integer zzz = hykRechargeCardDao.updateSaleStatusById(hykRechargeCard.getId());//1
        Integer xxx = hykUserDao.updateBalanceById(hykRechargeCard.getMoney(), hykUser.getId());//2

        Integer total=hykCardHisDao.getListByUserId(hykUser.getId()).size()+1;



        HykCardHis hykCardHis = new HykCardHis();
        hykCardHis.setId(ZxingHandler.getUUID());
        hykCardHis.setUserId(hykUser.getId());
        hykCardHis.setCardId(hykRechargeCard.getId());
        hykCardHis.setCardno(hykRechargeCard.getCaredno());
        hykCardHis.setMoney(new BigDecimal(hykRechargeCard.getMoney()));
        hykCardHis.setAccountBalance((hykUser.getAccountBalance().add(new BigDecimal(hykRechargeCard.getMoney()))).toString());
        hykCardHis.setAccumulativeRechargeAmount(hykUser.getAccumulativeRechargeAmount().add(new BigDecimal(hykRechargeCard.getMoney())).toString());
        hykCardHis.setTotal(total.toString());
        hykCardHis.setUseDate(new Date());
        Integer ccc = hykCardHisDao.insert(hykCardHis);//3
        b=true;
        return b;
    }
}