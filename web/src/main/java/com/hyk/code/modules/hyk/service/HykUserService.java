/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.entity.HykUser;
import com.hyk.code.modules.hyk.dao.HykUserDao;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.xml.ws.soap.Addressing;

/**
 * 用户管理Service
 *
 * @author 霍中曦
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class HykUserService extends CrudService<HykUserDao, HykUser> {
    @Autowired
    private HykUserDao hykUserDao;

    public HykUser get(String id) {
        return super.get(id);
    }

    public List<HykUser> findList(HykUser hykUser) {
        return super.findList(hykUser);
    }

    public Page<HykUser> findPage(Page<HykUser> page, HykUser hykUser) {
        return super.findPage(page, hykUser);
    }

    @Transactional(readOnly = false)
    public void save(HykUser hykUser) {
        if (hykUser.getIsNewRecord()) {
            dao.insert(hykUser);
        } else {
            dao.update(hykUser);
        }
    }

    @Transactional(readOnly = false)
    public void delete(HykUser hykUser) {
        super.delete(hykUser);
    }


    public HykUser getByPhone(HykUser hykUser) {
        return dao.getByPhone(hykUser);
    }

    public Integer getByPhoneNew(String phone) {
        return hykUserDao.getByPhoneNew(phone);
    }
    @Transactional(readOnly = false)//ios更改生日
    public Integer updateBirthday(String str,  String id){
        return hykUserDao.updateBirthday(str,id);
    };

    /**
     * 更新用户登录错误次数
     *
     * @param phone
     * @return
     */
    @Transactional(readOnly = false)
    public Integer updateAccount(String phone) {
        return hykUserDao.updateAccount(phone);
    }

    ;


    /**
     * 更新用户验证码错误次数
     *
     * @param
     * @return
     */
    @Transactional(readOnly = false)
    public Integer updateCode(String phone) {
        return hykUserDao.updateCode(phone);
    }

    ;


    /**
     * 更新user表油卡数量
     *
     * @param num 1  /  -1
     * @param id  用户id
     * @return
     */
    @Transactional(readOnly = false)
    public Integer updateOilCardNum(Integer num, String id) {
        return hykUserDao.updateOilCardNum(num, id);
    }

    ;

    /**
     * 查询个人中心显示的信息
     *
     * @param id
     * @return
     */
    public HykUser queryInfo(String id) {
        return hykUserDao.queryInfo(id);
    }

    ;


    /**
     * 修改密码
     *
     * @param hykUser
     * @return
     */
    @Transactional(readOnly = false)
    public Integer updatePwd(HykUser hykUser) {
        return hykUserDao.updatePwd(hykUser);
    }

    ;

    /**
     * 查询该用户邀请的所有用户的手机号和注册日期
     *
     * @param userId
     * @param phone
     * @return
     */
    public List<HykUser> selectInviterUser(String userId, String phone) {
        return hykUserDao.selectInviterUser(userId, phone);
    }

    ;


    /**
     * 查询该用户邀请的总人数
     *
     * @param phone 邀请人手机号
     * @param id    邀请人id
     * @return
     */
    public Integer getCountInvite(String phone, String id) {
        return hykUserDao.getCountInvite(phone, id);
    }

    ;


    /**
     * 定时任务
     *
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public Integer updateAllOrder() {
        try {
            return hykUserDao.updateAllOrder();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }

    public Integer updateAllOrderByUserId(String id) {
        return hykUserDao.updateAllOrderByUserId(id);
    }

}