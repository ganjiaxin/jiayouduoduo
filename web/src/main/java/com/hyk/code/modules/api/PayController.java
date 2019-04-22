package com.hyk.code.modules.api;

import com.alibaba.fastjson.JSON;
import com.hyk.code.common.Constant;
import com.hyk.code.common.alipay.AlipayUtils;
import com.hyk.code.common.alipay.OrderModel;
import com.hyk.code.common.lianlianpay.PayUtils;
import com.hyk.code.common.lianlianpay.utils.LLPayUtil;
import com.hyk.code.common.lianlianpay.utils.PayConfig;
import com.hyk.code.common.lianlianpay.vo.OrderInfo;
import com.hyk.code.common.lianlianpay.vo.PayDataBean;
import com.hyk.code.common.lianlianpay.vo.RetBean;
import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.common.utils.SmsUtil;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.wxpay.PayUtil;
import com.hyk.code.common.wxpay.WxModel;
import com.hyk.code.modules.hyk.entity.*;
import com.hyk.code.modules.hyk.service.*;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Auther: 霍中曦
 * @Date: 2018/12/5 10:57
 * @Description:
 */
@Controller
@RequestMapping(value = "${frontPath}/web/pay/")
public class PayController {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private HykOrderService hykOrderService;
    @Autowired
    private HykOilManagerService hykOilManagerService;
    @Autowired
    private HykMallOrderService hykMallOrderService;
    @Autowired
    private HykPayinfoService hykPayinfoService;

    @Autowired
    private HykUserBankService hykUserBankService;
    @Autowired
    private HykUserService hykUserService;
    @Autowired
    private HykRedpackageService hykRedpackageService;

    @ModelAttribute
    public HykOrder get(@RequestParam(required = false) String id) {
        HykOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hykOrderService.get(id);
        }
        if (entity == null) {
            entity = new HykOrder();
        }
        return entity;
    }


    /**
     * 功能描述:
     *
     * @auther: 霍中曦
     * @param: chnl //0、App-Android。1、App-iOS。2、Web。3、H5。 id:订单id
     * @param:type 是否组合支付 0非组合支付() 1组合支付
     * @param:payType 支付类型   1微信支付 2支付保  3快捷支付(连连)  4银联
     * @return:
     * @date: 2018/12/5 14:30
     */
    @RequestMapping(value = "createOrder")
    @ResponseBody
    public Map<String, String> createOrder(HttpServletRequest request, @RequestParam("id") String id, String type,
                                           String payType) {
        Map<String, String> map = new HashMap<String, String>();

        try {
            HykOrder hykOrder = hykOrderService.get(id);
            if (org.springframework.util.StringUtils.isEmpty(hykOrder)) {//判断订单情况 解决网络1k访问
                map.put("code", "601");
                map.put("msg", "当前网络慢,请稍后重试");
                return map;
            }
            if (!org.springframework.util.StringUtils.isEmpty(hykOrder.getRedpackageId())) {
                HykRedpackage hykRedpackage = hykRedpackageService.get(hykOrder.getRedpackageId());
                if (!"0".equals(hykRedpackage.getStatus()) && "1".equals(hykOrder.getDelFlag())) {
                    map.put("code", "301");
                    map.put("msg", "红包已使用");
                    return map;
                }
                //更新红包为锁定状态 生成订单并未付款
                Integer integer1 = hykRedpackageService.updateStatusById("3", hykOrder.getRedpackageId());
            }
            hykOrderService.updateDelFlag(id);
            hykOrder = hykOrderService.get(id);
            if (hykOrder.getBalancePayment().compareTo(BigDecimal.ZERO) == 0) {
                if (!"0".equals(hykOrder.getOrderStatus())) {
                    map.put("code", "400");
                    map.put("msg", "订单已支付");
                    return map;
                }

                BigDecimal balanceMoney;//要用余额支付的金额
                HykUser hykUser = hykUserService.get(hykOrder.getHykUser().getId());
                BigDecimal userMoney = hykUser.getAccountBalance();
                BigDecimal orderMoney = hykOrder.getPayableMoney();

                if ("1".equals(type)) {//选择了组合支付 下方判断选择的支付方
                    //if ("3".equals(payType)) {//连连 要在里面判断用户余额与订单应付金额的关系
                    if (orderMoney.compareTo(userMoney) > 0) {// orderMoney>userMoney  把账户余额全部使用掉 不足的用快捷支付
                        balanceMoney = userMoney;
                        hykOrderService.orderFun2(hykOrder, balanceMoney);//各种更新
                        hykOrder = hykOrderService.get(hykOrder.getId());  //orderFun2 更新了订单应付金额 重新复制下
                    } else {//orderMoney<=userMoney  不走快捷
                        balanceMoney = orderMoney;
                        hykOrderService.orderFun2(hykOrder, balanceMoney);//各种更新
                        hykOilManagerService.updateOrderStatus(hykOrder.getOrderNo(), "6");//直接生成加油计划
                        map.put("code", "201");
                        map.put("msg", "用户余额大于订单应付金额,全余额付款...");
                        map.put("url", PayConfig.URL_RETURN + "?orderNo=" + hykOrder.getOrderNo());
                        return map;
                    }
                    //}

                }
            }
            //-------------------------------------下面是生成给连连的订单
            OrderInfo order = payParam(request, hykOrder);
            // 创建订单
            PayUtils.plainPay(request, order);
            map.put("code", "200");
            //
            map.put("url", request.getAttribute("gateway_url") + "");
            addPayInfo(request, hykOrder);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            SmsUtil.sendErrorToUs("连连支付", request.getServletPath());
        }
        return map;
    }

    /**
     * 功能描述: 商城的订单 支付宝支付
     *
     * @auther: 霍中曦
     * @param: chnl //0、App-Android。1、App-iOS。2、Web。3、H5。 id:订单id
     * @return:
     * @date: 2018/12/5 14:30
     */
    @RequestMapping(value = "createOrderAliPay")
    @ResponseBody
    public Map<String, String> createOrderAliPay(HttpServletRequest request, @RequestParam("id") String id,
                                                 String chnl, String type, String payType) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            HykOrder hykOrder = hykOrderService.get(id);
            //判断订单情况 解决网络1k访问
            if (org.springframework.util.StringUtils.isEmpty(hykOrder)) {
                map.put("code", "601");
                map.put("msg", "当前网络慢,请稍后重试");
                return map;
            }
            if (!org.springframework.util.StringUtils.isEmpty(hykOrder.getRedpackageId())) {
                HykRedpackage hykRedpackage = hykRedpackageService.get(hykOrder.getRedpackageId());
                if (!"0".equals(hykRedpackage.getStatus()) && "1".equals(hykOrder.getDelFlag())) {
                    map.put("code", "301");
                    map.put("msg", "红包已使用");
                    return map;
                }
                Integer integer1 = hykRedpackageService.updateStatusById("3", hykOrder.getRedpackageId());//更新红包为锁定状态
                // 生成订单并未付款
            }
            hykOrderService.updateDelFlag(id);
            hykOrder = hykOrderService.get(id);
            if (hykOrder.getBalancePayment().compareTo(BigDecimal.ZERO) == 0) {
                if (!"0".equals(hykOrder.getOrderStatus())) {
                    map.put("code", "400");
                    map.put("msg", "订单已支付");
                    return map;
                }
                //要用余额支付的金额
                BigDecimal balanceMoney;
                HykUser hykUser = hykUserService.get(hykOrder.getHykUser().getId());
                BigDecimal userMoney = hykUser.getAccountBalance();
                BigDecimal orderMoney = hykOrder.getPayableMoney();
                //选择了组合支付 下方判断选择的支付方
                if ("1".equals(type)) {
                    //if ("2".equals(payType)) {//支付宝 要在里面判断用户余额与订单应付金额的关系
                    if (orderMoney.compareTo(userMoney) > 0) {// orderMoney>userMoney  把账户余额全部使用掉 不足的用快捷支付
                        balanceMoney = userMoney;
                        hykOrderService.orderFun2(hykOrder, balanceMoney);//各种更新
                        hykOrder = hykOrderService.get(hykOrder.getId());  //orderFun2 更新了订单应付金额 重新复制下
                    } else {//orderMoney<=userMoney  不走快捷
                        balanceMoney = orderMoney;
                        hykOrderService.orderFun2(hykOrder, balanceMoney);//各种更新
                        hykOilManagerService.updateOrderStatus(hykOrder.getOrderNo(), "6");//直接生成加油计划
                        map.put("code", "201");
                        map.put("msg", "用户余额大于订单应付金额,全余额付款...");
                        map.put("url", PayConfig.URL_RETURN + "?orderNo=" + hykOrder.getOrderNo());
                        return map;
                    }
                    //}

                }
            }
            //下面是生成给支付宝的订单
            OrderModel order = payParamAliPayOil(request, hykOrder);
            // 创建订单
            String bodystr = AlipayUtils.createOrder(request, order);
            map.put("code", "200");
            map.put("bodyStr", bodystr);
            addPayInfo(request, hykOrder);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            SmsUtil.sendErrorToUs("阿里支付", request.getServletPath());
        }

        return map;
    }


    /**
     * 功能描述: 加油订单 微信支付
     *
     * @auther: 霍中曦
     * @param: chnl //0、App-Android。1、App-iOS。2、Web。3、H5。 id:订单id
     * @return:
     * @date: 2018/12/5 14:30
     */
    @RequestMapping(value = "createOrderWxPay")
    @ResponseBody
    public Map<String, Object> createOrderWxPay(HttpServletRequest request, @RequestParam("id") String id,
                                                String chnl, String type, String payType) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HykOrder hykOrder = hykOrderService.get(id);
            //判断订单情况 解决网络1k访问
            if (org.springframework.util.StringUtils.isEmpty(hykOrder)) {
                map.put("code", "601");
                map.put("msg", "当前网络慢,请稍后重试");
                return map;
            }
            if (!org.springframework.util.StringUtils.isEmpty(hykOrder.getRedpackageId())) {
                HykRedpackage hykRedpackage = hykRedpackageService.get(hykOrder.getRedpackageId());
                if (!"0".equals(hykRedpackage.getStatus()) && "1".equals(hykOrder.getDelFlag())) {
                    map.put("code", "301");
                    map.put("msg", "红包已使用");
                    return map;
                }
                Integer integer1 = hykRedpackageService.updateStatusById("3", hykOrder.getRedpackageId());//更新红包为锁定状态
                // 生成订单并未付款
            }
            hykOrderService.updateDelFlag(id);
            hykOrder = hykOrderService.get(id);
            if (hykOrder.getBalancePayment().compareTo(BigDecimal.ZERO) == 0) {
                if (!"0".equals(hykOrder.getOrderStatus())) {
                    map.put("code", "400");
                    map.put("msg", "订单已支付");
                    return map;
                }
                BigDecimal balanceMoney;//要用余额支付的金额
                HykUser hykUser = hykUserService.get(hykOrder.getHykUser().getId());
                BigDecimal userMoney = hykUser.getAccountBalance();
                BigDecimal orderMoney = hykOrder.getPayableMoney();

                if ("1".equals(type)) {//选择了组合支付 下方判断选择的支付方
                    //if ("1".equals(payType)) {//支付宝 要在里面判断用户余额与订单应付金额的关系
                    if (orderMoney.compareTo(userMoney) > 0) {// orderMoney>userMoney  把账户余额全部使用掉 不足的用快捷支付
                        balanceMoney = userMoney;
                        hykOrderService.orderFun2(hykOrder, balanceMoney);//各种更新
                        hykOrder = hykOrderService.get(hykOrder.getId());  //orderFun2 更新了订单应付金额 重新复制下
                    } else {//orderMoney<=userMoney  不走快捷
                        balanceMoney = orderMoney;
                        hykOrderService.orderFun2(hykOrder, balanceMoney);//各种更新
                        hykOilManagerService.updateOrderStatus(hykOrder.getOrderNo(), "6");//直接生成加油计划
                        map.put("code", "201");
                        map.put("msg", "用户余额大于订单应付金额,全余额付款...");
                        map.put("url", PayConfig.URL_RETURN + "?orderNo=" + hykOrder.getOrderNo());
                        return map;
                    }
                    //}

                }
            }


            WxModel order = payParamWxPay(request, hykOrder);
            // 创建订单
            SortedMap<String, Object> sortMap = PayUtil.wxPay(order, request);
            request.setAttribute("reqmsg", sortMap.toString());
            map.put("code", "200");
            map.put("data", sortMap);
            map.put("packageStr", "Sign=WXPay");
            addPayInfo(request, hykOrder);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            SmsUtil.sendErrorToUs("微信支付", request.getServletPath());
        }

        return map;
    }

    /**
     * 功能描述: 连连回调方法
     *
     * @auther: 霍中曦
     * @date: 2018/12/5 14:11
     */
    @RequestMapping(value = "receiveNotify")
    @ResponseBody
    public void receiveNotify(HttpServletRequest request, HttpServletResponse resp) {
        System.out.println("开始时间:" + System.currentTimeMillis());
        PayDataBean payDataBean = null;
        try {
            System.out.println("进入支付异步通知数据接收处理");
            RetBean retBean = new RetBean();
            String reqStr = LLPayUtil.readReqStr(request);
            if (LLPayUtil.isnull(reqStr)) {
                retBean.setRet_code("9999");
                retBean.setRet_msg("交易失败");
                resp.getWriter().write(JSON.toJSONString(retBean));
                resp.getWriter().flush();
                return;
            }
            System.out.println("接收支付异步通知数据：【" + reqStr + "】");
            try {
                if (!LLPayUtil.checkSign(reqStr, PayConfig.YT_PUB_KEY, "")) {
                    retBean.setRet_code("9999");
                    retBean.setRet_msg("交易失败");
                    resp.getWriter().write(JSON.toJSONString(retBean));
                    resp.getWriter().flush();
                    System.out.println("支付异步通知验签失败");
                    return;
                }
            } catch (Exception e) {
                System.out.println("异步通知报文解析异常：" + e);
                retBean.setRet_code("9999");
                retBean.setRet_msg("交易失败");
                resp.getWriter().write(JSON.toJSONString(retBean));
                resp.getWriter().flush();
                return;
            }
            retBean.setRet_code("0000");
            retBean.setRet_msg("交易成功");
            resp.getWriter().write(JSON.toJSONString(retBean));
            resp.getWriter().flush();
            System.out.println("支付异步通知数据接收处理成功");
            // 解析异步通知对象
            payDataBean = JSON.parseObject(reqStr, PayDataBean.class);
            // TODO:更新订单，发货等后续处理
            try {
                instertLogger(payDataBean);//保存日志
                hykOilManagerService.updateOrderStatus(payDataBean.getNo_order(), "3");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束时间：" + System.currentTimeMillis());
    }

    /**
     * 功能描述: 支付宝回调
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2019/1/4 10:07
     * https://47.110.60.57:8082/hykweb/f/web/pay/receiveNotifyAliPay
     */
    @RequestMapping(value = "receiveNotifyAliPay")
    @ResponseBody
    public void receiveNotifyAliPay(HttpServletRequest request, HttpServletResponse resp) {
        try {
            AlipayUtils.notityInfo(request, resp);
            RetBean retBean = new RetBean();
            boolean status = (boolean) request.getAttribute("status");
            logger.info(request.getAttribute("trade_status") + "回调成功 进行业务处理：" + request.getAttribute(
                    "out_trade_no"));
            String out_trade_no = request.getAttribute("out_trade_no") + "";
            if (status) {
                String trade_status = request.getAttribute("trade_status") + "";
                try {
                    if (AlipayUtils.TRADE_SUCCESS.equals(trade_status)) {
                        logger.info("开始处理成功业务");
                        //保存日志
                        instertLoggerAlipay(out_trade_no, request.getAttribute("params") + "");
                        hykOilManagerService.updateOrderStatus(out_trade_no, Constant.PAY_TYPE_ALIPAY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 功能描述:微信支付回调方法
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2019/1/15 11:28
     */
    @RequestMapping(value = "receiveNotifyWxPay", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String receiveNotifyWxPay(HttpServletRequest request) throws IOException {
        System.out.println("微信支付回调");
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String resultxml = new String(outSteam.toByteArray(), "utf-8");
        Map<String, String> params = null;
        try {
            params = PayUtil.doXMLParse(resultxml);
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        outSteam.close();
        inStream.close();
        if (!PayUtil.isTenpaySign(params)) {
            // 支付失败
            return returnXML("FAIL");
        } else {
            System.out.println("===============付款成功处理业务开始==============");
            try {
                //开始业务处理
                String out_trade_no = params.get("out_trade_no").substring(3);
                instertLoggerAlipay(out_trade_no, params.toString());//保存日志
                hykOilManagerService.updateOrderStatus(out_trade_no, "1");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnXML("SUCCESS");
        }
    }

    private String returnXML(String return_code) {
        return "<xml><return_code><![CDATA["
                + return_code
                + "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    private void addPayInfo(HttpServletRequest request, HykOrder hykOrder) {
        try {
            HykPayinfo hykPayinfo = new HykPayinfo();
            hykPayinfo.setReqMsg(request.getAttribute("reqmsg").toString());
            hykPayinfo.setOrderNo(hykOrder.getOrderNo());
            hykPayinfo.setCode("0000");
            hykPayinfoService.save(hykPayinfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: 保存返还的参数和报文
     *
     * @auther: 霍中曦
     * @date: 2018/12/6 15:03
     */
    private void instertLogger(PayDataBean payDataBean) {
        try {
            HykPayinfo hykPayinfo = new HykPayinfo();
            hykPayinfo.setOrderNo(payDataBean.getNo_order());
            List<HykPayinfo> list = hykPayinfoService.findList(hykPayinfo);
            if (list.size() > 0) {
                hykPayinfo = list.get(0);
                hykPayinfo.setRespMsg(payDataBean.toString());
                hykPayinfoService.save(hykPayinfo);
            }
            HykOrder hykOrder = hykOrderService.findByOrderNo(payDataBean.getNo_order());
            HykUserBank hykUserBank = new HykUserBank();
            hykUserBank.setUserId(hykOrder.getUserId());
            hykUserBank.setNoAgree(payDataBean.getNo_agree());
            hykUserBank.setAcctName(payDataBean.getAcct_name());
            hykUserBank.setIdNo(payDataBean.getId_no());
            hykUserBank.setIdType(payDataBean.getId_type());
            hykUserBank.setCardNo(payDataBean.getCard_no());
            hykUserBankService.save(hykUserBank);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: 保存返还的参数和报文
     *
     * @auther: 霍中曦
     * @date: 2018/12/6 15:03
     */
    private void instertLoggerAlipay(String orderNo, String reqStr) {
        try {
            HykPayinfo hykPayinfo = new HykPayinfo();
            hykPayinfo.setOrderNo(orderNo);
            List<HykPayinfo> list = hykPayinfoService.findList(hykPayinfo);
            if (list.size() > 0) {
                hykPayinfo = list.get(0);
                hykPayinfo.setRespMsg(reqStr);
                hykPayinfoService.save(hykPayinfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 功能描述: 连连订单参数
     *
     * @auther: 霍中曦
     * @date: 2019/1/4 9:30
     */
    private OrderInfo payParam(HttpServletRequest request, HykOrder hykOrder) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setNo_order(hykOrder.getOrderNo());
        orderInfo.setMoney_order(hykOrder.getPayableMoney().toString());
        orderInfo.setName_goods(hykOrder.getGoodsName());
        orderInfo.setInfo_order("用户购买" + hykOrder.getGoodsName());
        orderInfo.setUser_id(hykOrder.getHykUser().getId());
        //0、App-Android。1、App-iOS。2、Web。3、H5。
        orderInfo.setFlag_chnl(request.getParameter("chnl"));
        orderInfo.setRegisterDate(hykOrder.getHykUser().getRegisterDate());
        orderInfo.setPhone(hykOrder.getUserPhone());
        return orderInfo;
    }

    /**
     * 功能描述: 支付宝订单参数
     *
     * @auther: 霍中曦
     * @date: 2019/1/4 9:30
     */
    private OrderModel payParamAliPayOil(HttpServletRequest request, HykOrder hykOrder) {
        //加油计划
        OrderModel order = new OrderModel();
        order.setBody(hykOrder.getGoodsName());
        order.setSubject("购买" + hykOrder.getGoodsName() + "每期" + hykOrder.getEveryAmt() + "元");
        order.setOut_trade_no(hykOrder.getOrderNo());
        order.setTimeout_express("30m");
        order.setTotal_amount(hykOrder.getPayableMoney().toString());
        order.setGoods_type("1");
        return order;
    }

    private WxModel payParamWxPay(HttpServletRequest request, HykOrder hykOrder) {
        //加油计划
        WxModel order = new WxModel();
        order.setBody("加油多多-" + hykOrder.getGoodsName());
        order.setDetail("购买" + hykOrder.getGoodsName() + "1个");
        order.setOut_trade_no((int) (Math.random() * 900 + 100) + hykOrder.getOrderNo());
        Date now = new Date();
        order.setTime_start(DateUtils.getFmtDate(now, "yyyyMMddHHmmss"));
        order.setTime_expire(DateUtils.getTimeAddMin(now, 30, "yyyyMMddHHmmss"));
        order.setTotalAmount(hykOrder.getPayableMoney());
        order.setAttach(hykOrder.getId());
        return order;
    }
}
