package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.common.utils.SmsUtil;
import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.modules.hyk.entity.HykGoods;
import com.hyk.code.modules.hyk.entity.HykOrder;
import com.hyk.code.modules.hyk.entity.HykRedpackage;
import com.hyk.code.modules.hyk.entity.HykUser;
import com.hyk.code.modules.hyk.service.HykGoodsService;
import com.hyk.code.modules.hyk.service.HykOrderService;
import com.hyk.code.modules.hyk.service.HykRedpackageService;
import com.hyk.code.modules.hyk.service.HykUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:App【订单流程】  ios/android h5通用
 *
 * @auther: 霍中曦
 * @return:
 * @date: 2018/11/20 14:37
 */


@Controller
public class AppOrderController {
    private final static Logger logger = LoggerFactory.getLogger(AppOrderController.class);
    private final static Integer pageSize = 10;

    @Autowired
    private HykGoodsService hykGoodsService;
    @Autowired
    private HykUserService hykUserService;
    @Autowired
    private HykOrderService hykOrderService;
    @Autowired
    private HykRedpackageService hykRedpackageService;


    /**
     * 功能描述: 即时充值生成订单
     *
     * @auther: 霍中曦
     * @param:cardId 加油卡Id
     * @param:cardNo 加油卡号
     * @param:orderType 交易类型
     * @param:amt 订单总额 只有在自定义金额的时候才会有
     * @param:goodsId 商品编号
     * @param:isCustom 是否自定义金额  0否 1是
     * @return:
     * @date: 2018/11/10
     * <p>
     * 即时充值生成订单  ： 先判断商品库存 再生成订单
     */
    @RequestMapping(value = "mine/recharge")
    @ResponseBody
    public String mineRecharge(HttpServletRequest request, @RequestParam(value = "token") String token, @RequestParam(value = "cardId", required = false, defaultValue = "") String cardId,
                               @RequestParam(value = "cardNo", required = false, defaultValue = "") String cardNo, @RequestParam(value = "isCustom") String isCustom,
                               @RequestParam(value = "amt", required = false) BigDecimal amt, @RequestParam(value = "goodsId", defaultValue = "0", required = false) String goodsId) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                HykGoods hykGoods = hykGoodsService.getGoodsById(goodsId);
                if (hykGoods.getStock() >= 1) {
                    HykOrder hykOrder = new HykOrder();
                    String id = ZxingHandler.getUUID();
                    String orderNo = ZxingHandler.getOrderStr(userId);
                    Integer integer=hykOrderService.queryCountByOrderNo(orderNo);
                    if(integer>=1){
                        map.put("code", "602");
                        map.put("msg", "请不要重复下单");
                        return gson.toJson(map);
                    }
                    HykUser hykUser = hykUserService.get(userId);
                    hykOrder.setId(id);
                    hykOrder.setOrderNo(orderNo);
                    hykOrder.setHykUser(hykUser);//取用userId , userPhone
                    hykOrder.setCardId(cardId);
                    hykOrder.setCardNo(cardNo);
                    hykOrder.setOrderType("0"); //0即时充值 1套餐充值
                    hykOrder.setGoodsId(goodsId);
                    BigDecimal orderAmount = BigDecimal.ZERO;
                    if (isCustom.equals("1")) {
                        hykOrder.setEveryAmt(amt);
                        hykOrder.setAmt(amt);
                        orderAmount = amt;
                    } else {
                        BigDecimal amt1 = new BigDecimal(0);
                        if (hykGoods.getDiscountMoney() != null && hykGoods.getDiscountMoney().compareTo(BigDecimal.ZERO) == 1) {
                            amt1 = hykGoods.getDiscountMoney();
                        } else {
                            amt1 = hykGoods.getPrices();
                        }
                        hykOrder.setEveryAmt(hykGoods.getVal());
                        hykOrder.setAmt(amt1);
                        orderAmount = amt1;
                    }

//                if (redpackageId != null && redpackageId.length() > 0) {
//                    hykOrder.setRedpackageId(redpackageId);
//                }
//                if (redpackageAmt != null && redpackageAmt.length() > 0) {
//                    hykOrder.setRedpackageAmt(new BigDecimal(redpackageAmt));
//                }

                    hykOrder.setOrderStatus("0");
                    hykOrder.setCreateDate(new Date());
                    hykOrder.setPayableMoney(orderAmount);
                    hykOrder.setDelFlag("1");


                    hykOrderService.insertOrder(hykOrder);//生成一条代充值订单
                    hykGoodsService.updateStock(goodsId);//更新商品数量

                    map.put("id", id);//订单id
                    map.put("orderAmount", orderAmount);//订单金额
                    map.put("cardNo", cardNo);//充值油卡号
                    map.put("goodsName", hykGoods.getGoodsName());//商品名称
                    map.put("orderNo", orderNo);//订单编号
                    map.put("balance", hykUser.getAccountBalance());//账户余额
                    map.put("code", "200");
                    map.put("msg", "success");
                } else {
                    map.put("code", "201");
                    map.put("msg", "库存不足");
                }
            }

        } catch (Exception e) {
            logger.info("生成订单异常");
            map.put("code", "400");
            map.put("msg", "生成订单异常");
            e.printStackTrace();
            SmsUtil.sendErrorToUs("加油下单",request.getServletPath());
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 套餐充值生成订单
     *
     * @auther: 霍中曦
     * @param:cardNo 加油卡号
     * @param:cardId 油卡Id
     * @param:orderType 交易类型
     * @param:stagesAmt 每期金额
     * @param:goodsId 商品编号
     * @param:redpackageId 红包id
     * @return:
     * @date: 2018/11/10
     * <p>
     * 套餐充值生成订单  ： 先判断商品库存 再生成订单 套餐充值可已使用红包
     */
    @RequestMapping(value = "mine/packageRecharge")
    @ResponseBody
    public String minePackageRecharge(HttpServletRequest request, @RequestParam(value = "token") String token, @RequestParam(value = "cardId", required = false, defaultValue = "") String cardId,
                                      @RequestParam(value = "cardNo", required = false, defaultValue = "") String cardNo, @RequestParam(value = "stagesAmt") BigDecimal stagesAmt,
                                      @RequestParam(value = "goodsId") String goodsId, @RequestParam(value = "redpackageId", required = false) String redpackageId) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                HykGoods hykGoods = hykGoodsService.get(goodsId);
                if (hykGoods.getStock() >= 1) {
                    HykOrder hykOrder = new HykOrder();
                    String id = ZxingHandler.getUUID();
                    String orderNo = ZxingHandler.getOrderStr(userId);
                    Integer integer=hykOrderService.queryCountByOrderNo(orderNo);
                    if(integer>=1){
                        map.put("code", "602");
                        map.put("msg", "请不要重复下单");
                        return gson.toJson(map);
                    }
                    HykUser hykUser = hykUserService.get(userId);

                    hykOrder.setId(id);
                    hykOrder.setOrderNo(orderNo);
                    hykOrder.setHykUser(hykUser);//userId userPhone
                    hykOrder.setCardId(cardId);
                    hykOrder.setCardNo(cardNo);
                    hykOrder.setOrderType("1"); //0即时充值 1套餐充值
                    hykOrder.setGoodsId(goodsId);

                    hykOrder.setEveryAmt(stagesAmt);
                    BigDecimal cycle = new BigDecimal(hykGoods.getCycle());
                    BigDecimal ActivityDiscount = hykGoods.getActivityDiscount();
                    BigDecimal amt = null;
                    BigDecimal returnDiscount = new BigDecimal(0);//给android 返回
                    if (ActivityDiscount != null && ActivityDiscount.compareTo(BigDecimal.ZERO) == 1) {
                        amt = cycle.multiply(ActivityDiscount).multiply(stagesAmt).divide(new BigDecimal(10));
                        returnDiscount = ActivityDiscount;
                    } else {
                        BigDecimal discount = hykGoods.getDiscount();
                        amt = cycle.multiply(discount).multiply(stagesAmt).divide(new BigDecimal(10));
                        returnDiscount = discount;
                    }
                    hykOrder.setAmt(amt);
//                if(isCustom.equals("1")){
//                    hykOrder.setAmt(amt);
//                }else {
//                    BigDecimal amt1=hykGoodsService.getGoodsById(goodsId).getPrices();
//                    hykOrder.setAmt(amt1);
//                }
                    BigDecimal redMoney = new BigDecimal(0);
                    if (redpackageId != null && redpackageId.length() > 0) {
                        HykRedpackage hykRedpackage = hykRedpackageService.get(redpackageId);
                        if (!StringUtils.isEmpty(hykRedpackage)) {
                            if (!"0".equals(hykRedpackage.getStatus())) {
                                map.put("code", "301");
                                map.put("msg", "红包已使用");
                                return gson.toJson(map);
                            }
                            if (!StringUtils.isEmpty(hykRedpackage.getMinAmount())) {
                                if (amt.compareTo(hykRedpackage.getMinAmount()) < 0) {
                                    map.put("code", "302");
                                    map.put("msg", "红包未达到可用金额");
                                    return gson.toJson(map);
                                }
                            }
                            if (!StringUtils.isEmpty(hykRedpackage.getGoodsId())) {
                                if(!hykRedpackage.getGoodsId().equals(goodsId)){
                                    map.put("code", "303");
                                    map.put("msg", "可用商品类型不匹配");
                                    return gson.toJson(map);
                                }
                            }
                        }
                        hykOrder.setRedpackageId(redpackageId);
                        // Integer integer1 = hykRedpackageService.updateStatusById("3", redpackageId);//更新红包为锁定状态 生成订单并未付款
                        redMoney = hykRedpackage.getMoney();
                        hykOrder.setRedpackageAmt(redMoney);
                    }

                    hykOrder.setOrderStatus("0");
                    hykOrder.setCreateDate(new Date());
                    BigDecimal payableMoney = amt.subtract(redMoney).compareTo(BigDecimal.ZERO) == 1 ? amt.subtract(redMoney) : BigDecimal.ZERO;
                    hykOrder.setPayableMoney(payableMoney);
                    hykOrder.setDelFlag("1");


                    hykOrderService.insertOrder(hykOrder);//生成一条代充值订单
                    hykGoodsService.updateStock(goodsId);//更新商品数量

                    map.put("id", id);//订单id
                    map.put("orderAmount", payableMoney);//订单金额
                    map.put("cardNo", cardNo);//充值油卡号
                    map.put("orderNo", orderNo);//订单编号
                    map.put("discount", returnDiscount);//折扣
                    map.put("cycle", cycle);//周期
                    map.put("amt", amt);//订单总额
                    map.put("redMoney", redMoney);//优惠减免
                    map.put("balance", hykUser.getAccountBalance());//账户余额
                    map.put("code", "200");
                    map.put("msg", "success");
                } else {
                    map.put("code", "201");
                    map.put("msg", "库存不足");
                }
            }

        } catch (Exception e) {
            logger.info("生成订单异常");
            map.put("code", "400");
            map.put("msg", "生成订单异常");
            SmsUtil.sendErrorToUs("加油下单",request.getServletPath());
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------下面是各种查看订单-------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    /**
     * 功能描述: 我的->我的订单  该用户所有订单信息查询
     *
     * @auther: 霍中曦
     * @param:currPage 页数
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/allOrder")
    @ResponseBody
    public String mineAllOrder(HttpServletRequest request, @RequestParam(value = "token") String token, String oilStatus,
                               @RequestParam(value = "currPage", required = false, defaultValue = "1") Integer currPage) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                hykUserService.updateAllOrderByUserId(userId);//过期订单余额返还
                hykOrderService.updateStatusByUserId1(userId);//过期订单用红包的
                hykOrderService.updateStatusByUserId2(userId);//过期订单没用红包的
                List list = hykOrderService.queryListByUserId(userId, oilStatus, currPage, pageSize);
                map.put("list", list);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("查询用户所有订单信息异常");
            map.put("code", "400");
            map.put("msg", "查询用户所有订单信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 1.2版本-->筛选出对应的油卡已付款的订单并显示对应的订单状态；显示内容与订单内容显示一致；
     * 已完成订单在下面，待加油或加油计划执行中的订单按付款时间，按时间的倒叙排列
     *
     * @auther: 霍中曦
     * @param:currPage 页数
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/allSuccessOrder")
    @ResponseBody
    public String mineAllSuccessOrder(HttpServletRequest request, String cardId,
                                      @RequestParam(value = "currPage", required = false, defaultValue = "1") Integer currPage) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Map map = new HashMap();
        try {
            List list = hykOrderService.getListByCardId(cardId, currPage, pageSize);
            map.put("list", list);
            map.put("code", "200");
            map.put("msg", "success");

        } catch (Exception e) {
            logger.info("查询成功加油订单异常");
            map.put("code", "400");
            map.put("msg", "查询成功加油订单异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 查看订单详情
     *
     * @auther: 霍中曦
     * @param:orderNo 订单编号
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "order/seeOrder")
    @ResponseBody
    public String orderSeeOrder(HttpServletRequest request, @RequestParam(value = "orderNo") String orderNo, @RequestParam(value = "token", required = false) String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = null;
            BigDecimal balance = BigDecimal.ZERO;
            if (token != null) {
                HykUser hykUser = TokenUtil.getAppCurrentUser(token);
                userId = hykUser.getId();
                balance = hykUserService.get(userId).getAccountBalance();
            }
//            if (userId == null || userId.length() <= 0) {
//                map.put("code", "300");
//                map.put("msg", "登录已失效 重新登录");
//            } else {
            Map map1 = hykOrderService.queryByOrderNo(orderNo);
            map.put("balance", balance);
            map.put("hykOrder", map1);
            map.put("code", "200");
            map.put("msg", "success");
            // }
        } catch (Exception e) {
            logger.info("获取该订单信息异常");
            map.put("code", "400");
            map.put("msg", "获取该订单信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

}
