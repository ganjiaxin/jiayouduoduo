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
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.wxpay.PayUtil;
import com.hyk.code.common.wxpay.WxModel;
import com.hyk.code.modules.hyk.entity.*;
import com.hyk.code.modules.hyk.service.*;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Auther: 霍中曦
 * @Date: 2018/12/5 10:57
 * @Description:
 */
@Controller
@RequestMapping(value = "${frontPath}/web/payShop/")
public class PayShopController {
    @Autowired
    private HykOrderService hykOrderService;
    @Autowired
    private HykOilManagerService hykOilManagerService;

    @Autowired
    private HykPayinfoService hykPayinfoService;

    @Autowired
    private HykUserBankService hykUserBankService;
    @Autowired
    private HykUserService hykUserService;

    @Autowired
    private HykMallOrderService hykMallOrderService;

    @ModelAttribute
    public HykMallOrder get(@RequestParam(required = false) String id) {
        HykMallOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hykMallOrderService.get(id);
        }
        if (entity == null) {
            entity = new HykMallOrder();
        }
        return entity;
    }


    /**
     * 功能描述: 商城的订单 连连支付
     *
     * @auther: 霍中曦
     * @param: chnl //0、App-Android。1、App-iOS。2、Web。3、H5。 id:订单id
     * @return:
     * @date: 2018/12/5 14:30
     */
    @RequestMapping(value = "createShopOrder")
    @ResponseBody
    public Map<String, String> createShopOrder(HttpServletRequest request, HykMallOrder hykMallOrder) {
        Map<String, String> map = new HashMap<String, String>();
        OrderInfo order = payParam(request, hykMallOrder);
        try {
            // 创建订单
            PayUtils.plainPay(request, order);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
        }
        map.put("code", "200");
        map.put("url", request.getAttribute("gateway_url") + "");
        addPayInfo(request, hykMallOrder);
        return map;
    }


    /**
     * 功能描述: 连连支付回调
     *
     * @auther: 霍中曦
     * @date: 2018/12/5 14:11
     */
    @RequestMapping(value = "receiveNotify")
    @ResponseBody
    public void receiveNotify(HttpServletRequest request, HttpServletResponse resp) {
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
            PayDataBean payDataBean = JSON.parseObject(reqStr, PayDataBean.class);
            // TODO:更新订单，发货等后续处理
            try {
                instertLogger(payDataBean);//保存日志
                hykMallOrderService.updateSuccessStatusByOrder("2", payDataBean.getNo_order(), "3");//成功 //发送虚拟商品
                //hykMallOrderService.updateSendStatus(payDataBean.getNo_order());//发送虚拟商品
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: 商城的订单 支付宝支付
     *
     * @auther: 霍中曦
     * @param: chnl //0、App-Android。1、App-iOS。2、Web。3、H5。 id:订单id
     * @return:
     * @date: 2018/12/5 14:30
     */
    @RequestMapping(value = "createShopOrderAliPay")
    @ResponseBody
    public Map<String, String> createShopOrderAliPay(HttpServletRequest request, String id, String chnl) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            HykMallOrder hykMallOrder = hykMallOrderService.get(id);
            OrderModel order = payParamAliPay(request, hykMallOrder);
            // 创建订单
            String bodystr = AlipayUtils.createOrder(request, order);
            map.put("code", "200");
            map.put("bodyStr", bodystr);
            addPayInfo(request, hykMallOrder);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
        }

        return map;
    }

    /**
     * 功能描述: 商城订单 支付宝H5支付
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2019/1/15 11:25
     */
    @RequestMapping(value = "createShopOrderAliPayH5")
    @ResponseBody
    public void createShopOrderAliPayH5(HttpServletResponse respone, HttpServletRequest request, String id,
                                        String chnl) {
        Map<String, String> map = new HashMap<String, String>();
        HykMallOrder hykMallOrder = hykMallOrderService.get(id);
        try {
            OrderModel order = payParamAliPay(request, hykMallOrder);
            // 创建订单
            AlipayUtils.createOrderH5(respone, request, order);
            map.put("code", "200");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
        }
        //addPayInfo(request, hykMallOrder);
        //return map;
    }

    /**
     * 功能描述: 支付宝回调
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2019/1/4 10:07
     * https://47.110.60.57:8082/hykweb/f/web/payShop/receiveNotifyAliPay
     */
    @RequestMapping(value = "receiveNotifyAliPay")
    @ResponseBody
    public void receiveNotifyAliPay(HttpServletRequest request, HttpServletResponse resp) {
        try {
            RetBean retBean = new RetBean();
            // 创建订单
            AlipayUtils.notityInfo(request, resp);
            boolean status = (boolean) request.getAttribute("status");
            String out_trade_no = request.getAttribute("out_trade_no") + "";
            if (status) {
                System.out.println(request.getAttribute("trade_status") + "回调成功 进行业务处理：" + request.getAttribute(
                        "out_trade_no"));
                String trade_status = request.getAttribute("trade_status") + "";
                try {
                    if (AlipayUtils.TRADE_SUCCESS.equals(trade_status)) {
                        System.out.println("开始处理成功业务");
                        instertLoggerAlipay(out_trade_no, request.getAttribute("params") + "");//保存日志
                        //订单状态 0待支付 1支付中 2已支付 3支付失败 4订单已失效
                        hykMallOrderService.updateSuccessStatusByOrder("2", out_trade_no, Constant.PAY_TYPE_ALIPAY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("支付宝验签失败 不处理业务");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 功能描述: 商城的订单 微信支付
     *
     * @auther: 霍中曦
     * @param: chnl //0、App-Android。1、App-iOS。2、Web。3、H5。 id:订单id
     * @return:
     * @date: 2018/12/5 14:30
     */
    @RequestMapping(value = "createShopOrderWxPay")
    @ResponseBody
    public Map<String, Object> createShopOrderWxPay(HttpServletRequest request, String id, String chnl) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HykMallOrder hykMallOrder = hykMallOrderService.get(id);
            WxModel order = payParamWxPay(request, hykMallOrder);
            // 创建订单
            SortedMap<String, Object> sortMap = PayUtil.wxPay(order, request);
            request.setAttribute("reqmsg", sortMap.toString());
            map.put("code", "200");
            map.put("data", sortMap);
            map.put("packageStr", "Sign=WXPay");
            addPayInfo(request, hykMallOrder);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
        }

        return map;
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
        System.out.println("微信支付回调====" + System.currentTimeMillis());
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
                System.out.println("业务开始====" + System.currentTimeMillis());
                String out_trade_no = params.get("out_trade_no");
                instertLoggerAlipay(out_trade_no, params.toString());//保存日志
                hykMallOrderService.updateSuccessStatusByOrder("2", out_trade_no, "1");//成功//发送虚拟商品
                System.out.println("业务结束====" + System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("微信支付回调====" + System.currentTimeMillis());
            return returnXML("SUCCESS");
        }
    }

    private String returnXML(String return_code) {
        return "<xml><return_code><![CDATA["
                + return_code
                + "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    private void addPayInfo(HttpServletRequest request, HykMallOrder hykMallOrder) {
        try {
            HykPayinfo hykPayinfo = new HykPayinfo();
            hykPayinfo.setReqMsg(request.getAttribute("reqmsg").toString());
            hykPayinfo.setOrderNo(hykMallOrder.getOrderno());
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
     * 功能描述: 保存返还的参数和报文Alipay
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


    private OrderInfo payParam(HttpServletRequest request, HykMallOrder hykMallOrder) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setNo_order(hykMallOrder.getOrderno());
        orderInfo.setMoney_order(hykMallOrder.getMoney().toString());
        orderInfo.setName_goods(hykMallOrder.getGoodsName());
        orderInfo.setInfo_order("用户购买" + hykMallOrder.getGoodsName());
        orderInfo.setUser_id(hykMallOrder.getUserId());
        orderInfo.setFlag_chnl(request.getParameter("chnl"));//0、App-Android。1、App-iOS。2、Web。3、H5。
        HykUser hykUser = hykUserService.get(hykMallOrder.getUserId());
        orderInfo.setRegisterDate(hykUser.getRegisterDate());
        orderInfo.setPhone(hykMallOrder.getPhone());
        return orderInfo;
    }

    private OrderModel payParamAliPay(HttpServletRequest request, HykMallOrder hykMallOrder) {//惠优商城
        OrderModel order = new OrderModel();
        order.setBody(hykMallOrder.getGoodsName());
        order.setSubject("购买" + hykMallOrder.getGoodsName() + hykMallOrder.getNum() + "个");
        order.setOut_trade_no(hykMallOrder.getOrderno());
        order.setTimeout_express("30m");
        order.setTotal_amount(hykMallOrder.getMoney().toString());
        order.setGoods_type("1");
        return order;
    }

    private WxModel payParamWxPay(HttpServletRequest request, HykMallOrder hykMallOrder) {//惠优商城
        WxModel order = new WxModel();
        order.setBody("油大大-" + hykMallOrder.getGoodsName());
        order.setDetail("购买" + hykMallOrder.getGoodsName() + hykMallOrder.getNum() + "个");
        order.setOut_trade_no(hykMallOrder.getOrderno());
        Date now = new Date();
        order.setTime_start(DateUtils.getFmtDate(now, "yyyyMMddHHmmss"));
        order.setTime_expire(DateUtils.getTimeAddMin(now, 30, "yyyyMMddHHmmss"));
        order.setTotalAmount(hykMallOrder.getMoney());
        order.setAttach(hykMallOrder.getId());
        return order;
    }
}
