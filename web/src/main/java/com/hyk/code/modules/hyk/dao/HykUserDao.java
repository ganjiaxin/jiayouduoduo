/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.access.method.P;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户管理DAO接口
 * @author 霍中曦
 * @version 2018-11-12
 */
@MyBatisDao
public interface HykUserDao extends CrudDao<HykUser> {

    @Select("SELECT COUNT(*) from hyk_user where phone=#{phone}")
    Integer getByPhoneNew(String phone);
    HykUser getByPhone(HykUser hykUser);

    /**
     * 更新用户登录错误次数
     * @param phone
     * @return
     */
    @Update("update hyk_user set account_error=account_error+1 where del_flag=0 and phone=#{phone}")
    Integer updateAccount(String phone);

    /**
     * 更新user表油卡数量
     * @param num 1  /  -1
     * @param id 用户id
     * @return
     */
    @Update("update hyk_user set oil_card_num=IFNULL(oil_card_num,0) + #{num} where id= #{id}")
    Integer updateOilCardNum(@Param("num")Integer num,@Param("id")String id);

    /**
     * 更新用户验证码错误次数
     * @param phone
     * @return
     */
    @Update("update hyk_user set send_code_error=send_code_error+1 where del_flag=0 and phone=#{phone}")
    Integer updateCode(String phone);

    /**
     * 查询个人中心显示的信息
     * @param id
     * @return inviterId  这个字段是最初版本的邀请码 后来改需求了怕版本不兼容 多了一个realInviterId 字段用来存真正的邀请码
     */
    @Select("select phone as phone, id as inviterId,inviter_id as realInviterId,headImg as headimg ,sex as sex, DATE_FORMAT(birthday,'%Y-%m-%d') as registerDateStr2,UNIX_TIMESTAMP(birthday)as registerDateStr  ,qrcode as qrcode ,account_balance as accountBalance from hyk_user where id =#{id}")
    HykUser queryInfo(String id);
    Integer updateBalanceById(@Param("money")long money, @Param("id") String id);//更新累计充值金额，剩余金额
    Integer updateBirthday(@Param("str")String str, @Param("id") String id);//ios更改生日

    /**
     * 修改密码
     * @param hykUser
     * @return
     */
    @Update("update hyk_user set `password`=#{password} where phone=#{phone}")
    Integer updatePwd(HykUser hykUser);

    /**
     * 查询该用户邀请的所有用户的手机号和注册日期
     * @param userId
     * @param phone
     * @return
     */
    @Select("select  insert(phone, 4, 4, '****') as phone ,  FROM_UNIXTIME(UNIX_TIMESTAMP(register_date),'%Y-%m-%d %H:%i') as registerDateStr " +
            " from hyk_user where inviter_id=#{userId} or inviter_id=#{phone}")
    List<HykUser> selectInviterUser(@Param("userId")String userId,@Param("phone")String phone);

    @Update("update hyk_user set wait_amt= IFNULL(wait_amt,0) + #{money} , pay_num=IFNULL(pay_num,0)+1 where id = #{id}")
    Integer updateWaitAmt(@Param("money") BigDecimal money, @Param("id")String id);

    /**
     * 查询该用户邀请的总人数
     * @param phone  邀请人手机号
     * @param id   邀请人id
     * @return
     */
    @Select("SELECT COUNT(*) from hyk_user where inviter_id=#{phone} or inviter_id=#{id}")
    Integer getCountInvite(@Param("phone") String phone, @Param("id")String id) ;


    Integer updateAccountBalance(@Param("money")BigDecimal money, @Param("id") String id);//余额支付后修改剩余金额
    Integer updateAccountBalance2(@Param("money")BigDecimal money, @Param("id") String id);//支付失败后修改剩余金额

    Integer updateAllOrderByUserId(String id);//访问所有订单时更新已过期的 并返还余额
    Integer updateAllOrder();//定时执行

}