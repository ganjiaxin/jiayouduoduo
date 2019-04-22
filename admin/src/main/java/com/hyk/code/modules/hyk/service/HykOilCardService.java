/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.entity.HykOilCard;
import com.hyk.code.modules.hyk.dao.HykOilCardDao;

/**
 * 加油卡管理Service
 *
 * @author 霍中曦
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class HykOilCardService extends CrudService<HykOilCardDao, HykOilCard> {
    @Autowired
    private HykOilCardDao hykOilCardDao;

    public HykOilCard get(String id) {
        return super.get(id);
    }

    public List<HykOilCard> findList(HykOilCard hykOilCard) {
        return super.findList(hykOilCard);
    }

    public Page<HykOilCard> findPage(Page<HykOilCard> page, HykOilCard hykOilCard) {
        return super.findPage(page, hykOilCard);
    }

    @Transactional(readOnly = false)
    public void save(HykOilCard hykOilCard) {
        super.save(hykOilCard);
    }

    @Transactional(readOnly = false)
    public void delete(HykOilCard hykOilCard) {
        super.delete(hykOilCard);
    }

    /**
     * 添加加油卡
     *
     * @param hykOilCard
     */
    @Transactional(readOnly = false)
    public Integer addCard(HykOilCard hykOilCard) {
        return hykOilCardDao.addCard(hykOilCard);
    }

    /**
     * 查询该用户的所有加油卡信息
     *
     * @param userId
     * @return
     */
    public List<HykOilCard> findById(String userId) {
        return hykOilCardDao.findById(userId);
    }

    /**
     * 加油计划变更为已加油时，更改card表中money(累计加油金额)
     *
     * @param money
     * @param cardId
     * @return
     */
    @Transactional(readOnly = false)
    public Integer updateMoney(BigDecimal money, String cardId) {
        return hykOilCardDao.updateMoney(money, cardId);
    }

    ;


    /**
     * 删除加油卡
     *
     * @param
     * @return
     */
    @Transactional(readOnly = false)
    public Integer softDeleteById(String id, String userId) {
        return hykOilCardDao.softDeleteById(id, userId);
    }

    ;
}