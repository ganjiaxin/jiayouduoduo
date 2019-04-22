/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykUser;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 用户管理DAO接口
 * @author 霍中曦
 * @version 2018-11-12
 */
@MyBatisDao
public interface HykUserDao extends CrudDao<HykUser> {

    HykUser getByPhone(HykUser hykUser);

    /**
     * 更新用户登录错误次数
     * @param id
     * @return
     */
    @Update("update hyk_user set account_error=account_error+1 where id=#{id}")
    Integer updateAccount(String id);

    /**
     * 更新用户验证码错误次数
     * @param id
     * @return
     */
    @Update("update hyk_user set send_code_error=send_code_error+1 where id=#{id}")
    Integer updateCode(String id);

    /**
     * 查询个人中心显示的信息
     * @param id
     * @return
     */
    @Select("select headImg as headimg ,sex as sex, UNIX_TIMESTAMP(birthday) as registerDateStr from hyk_user where id =#{id}")
    HykUser queryInfo(String id);


    /**
     * 修改密码
     * @param hykUser
     * @return
     */
    @Update("update hyk_user set `password`=#{password} where phone=#{phone}")
    Integer updatePwd(HykUser hykUser);
}