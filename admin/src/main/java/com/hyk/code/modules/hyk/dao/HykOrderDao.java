/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykBackMoneyVo;
import com.hyk.code.modules.hyk.entity.HykInviter;
import com.hyk.code.modules.hyk.entity.HykOrder;
import com.hyk.code.modules.hyk.entity.vo.CompanyMarVo;
import com.hyk.code.modules.hyk.entity.vo.HykOrderDetailVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 订单管理DAO接口
 * @author 霍中曦
 * @version 2018-11-09
 */
@MyBatisDao
public interface HykOrderDao extends CrudDao<HykOrder> {
    /**
        首页已完成订单显示
     */
    @Select("select REPLACE( a.user_phone, SUBSTR( a.user_phone,4,4), '****')  as userPhone,b.goods_name as goodsId from hyk_order a " +
            "left join hyk_goods b on a.goods_id=b.id  where a.order_status=2 and a.del_flag=0 order by a.pay_date limit 5")
	 List<HykOrder> getSuccessAll();

    /**
     * 根据订单号查询该订单
     * @param orderNo 不知道为什么用对象返回android端就是有一个字段接受不到  没办法改用map
     * @return
     */
//    @Select("SELECT a.order_status as orderStatus,a.card_no as cardNo ,a.amt as amt,a.redpackage_amt as redpackageAmt,a.order_no as orderNo ,a.create_date as createDateStr, " +
//            " a.pay_date as payDateStr ,b.goods_name as goodsName , b.cycle as cycel ,b.discount as discount,b.activity_discount as activityDiscount, " +
//            " b.goods_type as goodsType,b.prices as prices from hyk_order a left join hyk_goods b on a.goods_id=b.id where a.order_no=#{orderNo}")
     Map queryByOrderNo(String orderNo);

    /**
     * 查询该订单详细信息
     * @param orderNo
     * @return
     */
    @Select("select * from hyk_order where order_no=#{orderNo}")
    HykOrder queryByOrder(String orderNo);

    /**
     * App版本相关信息
     * @return
     */
    Map getApp();

    /**
     * 个人所有订单查询
     */
        List<HykOrder> queryListByUserId(String userId);


    /**
     * 添加充值订单
     * @param hykOrder
     * @return
     */
    Integer insertOrder(HykOrder hykOrder);

    List<CompanyMarVo> findcompanyBackMoneyPage(CompanyMarVo companyMarVo);

    HykOrder getUpMonthPayAmt(HykOrder hykOrder);

    HykOrder getUpMonthBackAmt(HykOrder hykOrder);

    HykOrder getCurrentMonthAmt(HykOrder hykOrder);

    HykOrder getCurrentMonthBackAmt(HykOrder hykOrder);

    HykOrder getTotalBackAmt(HykOrder hykOrder);

    HykOrder getTotalPayAmt(HykOrder hykOrder);

    List<HykOrderDetailVo> findOrderBackMoneyDetailPage(HykOrderDetailVo hykOrderDetailVo);

    List<HykBackMoneyVo> getMonthBackAmt(HykBackMoneyVo hykBackMoneyVo);


    List<HykInviter> findInviterExcelList(HykOrder hykOrder);

    List<CompanyMarVo> findMonthReportPage(CompanyMarVo companyMarVo);


    public void updateStop(HykOrder hykOrder);
    public void updateStart(HykOrder hykOrder);
    public void updateRefund(HykOrder hykOrder);

}