package com.hyk.code.common.lianlianpay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyk.code.common.lianlianpay.conn.HttpRequestSimple;
import com.hyk.code.common.lianlianpay.utils.LLPayUtil;
import com.hyk.code.common.lianlianpay.utils.PayConfig;
import com.hyk.code.common.lianlianpay.vo.OrderInfo;
import com.hyk.code.common.lianlianpay.vo.PayModel;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: 霍中曦
 * @Date: 2018/12/5 09:33
 * @Description:
 */
public class PayUtils {

    /**
     * 普通支付处理
     *
     * @param req
     * @throws Exception
     */
    public static void plainPay(HttpServletRequest req, OrderInfo orders) throws Exception
    {
        // 构造支付请求对象
        PayModel payModel = new PayModel();
        payModel.setApi_version(PayConfig.VERSION);
        payModel.setOid_partner(PayConfig.OID_PARTNER);
        payModel.setSign_type(PayConfig.SIGN_TYPE);
        payModel.setBusi_partner(PayConfig.BUSI_PARTNER);
        payModel.setDt_order(LLPayUtil.getCurrentDateTimeStr());

        String port=req.getServerPort()+"";
        System.out.println("============"+port);
        if("8082".equals(port)){//预发布环境
            if(orders.getNo_order().substring(0,1).equals("s")){
                payModel.setNotify_url(PayConfig.NOTIFY_SHOPURL_TEST);
            }else {
                payModel.setNotify_url(PayConfig.NOTIFY_URL_TEST);
            }
            payModel.setUrl_return(PayConfig.URL_RETURN_TEST+"?orderNo="+orders.getNo_order());
        }else{
            if(orders.getNo_order().substring(0,1).equals("s")){
                payModel.setNotify_url(PayConfig.NOTIFY_SHOPURL);
            }else {
                payModel.setNotify_url(PayConfig.NOTIFY_URL);
            }
            payModel.setUrl_return(PayConfig.URL_RETURN+"?orderNo="+orders.getNo_order());
        }


        payModel.setValid_order("30");// 单位分钟，可以为空，默认30分钟
        payModel.setTime_stamp(LLPayUtil.getCurrentDateTimeStr());
        payModel.setRisk_item(LLPayUtil.createRiskItem(orders));
        payModel.setFlag_pay_product("0");//0 快捷、1 认证、2 网银、5新认证支付
        //动态字段
        payModel.setNo_order(orders.getNo_order());
        payModel.setName_goods(orders.getName_goods());
        payModel.setInfo_order(orders.getInfo_order());
        payModel.setMoney_order(orders.getMoney_order());
        payModel.setFlag_chnl(orders.getFlag_chnl());// //0、App-Android。1、App-iOS。2、Web。3、H5。
        payModel.setUser_id(orders.getUser_id());//用户id

        //payModel.setCard_no("6217856300011580282");
        //payModel.setAcct_name("王耀威");
        //payModel.setId_type("0");//0身份证类型
        //payModel.setId_no("341227199107049033");//身份证号

        //payModel.setBind_mob(req.getParameter("bind_mob"));
        //payModel.setBank_code(req.getParameter("bank_code"));//银行编码
        //payModel.setCard_type(req.getParameter("card_type"));//卡类型


        // 加签名
        String sign = LLPayUtil.addSign(JSON.parseObject(JSON.toJSONString(payModel)), PayConfig.TRADER_PRI_KEY,"");
        payModel.setSign(sign);

        String resJSON = HttpRequestSimple.getInstance().postSendHttp(PayConfig.BILL_CREATE_URL, JSON.toJSONString(payModel));
        System.out.println("创单请求响应报文[" + resJSON + "]");

        JSONObject payDataBean = JSON.parseObject(resJSON);
        if(!"0000".equals(payDataBean.getString("ret_code"))){
            System.out.println("创单失败");
            throw new Exception("创单失败");
        }

        if (!LLPayUtil.checkSign(resJSON, PayConfig.YT_PUB_KEY, "")) {
            System.out.println("签名验证失败");
            throw new Exception("创单失败/创单签名验证失败");
        }
        req.setAttribute("gateway_url", payDataBean.getString("gateway_url"));
        req.setAttribute("reqmsg", payDataBean.getString("ret_code")+":"+resJSON);
    }

}
