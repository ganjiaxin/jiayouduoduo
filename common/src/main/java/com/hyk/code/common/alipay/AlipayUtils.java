package com.hyk.code.common.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 */
public class AlipayUtils {
    /**
     * 日志对象
     */
    protected static Logger logger = LoggerFactory.getLogger(AlipayUtils.class);

    /*沙箱地址 正式环境切换正式地址*/
    public static final String URL = "https://openapi.alipay.com/gateway.do";
    /*appid*/
    public static final String APP_ID = "2019041763934258";//正式
    /*RAS私钥*/
    public static final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0xtxQiSQboxdUw1ICswx9+vC86GOKCLTu1nVO7fQkv7XZgNhl9pVNBY4IVNgGiMSARixPBBDVg/OTLAKEwqBjWl3195qUvRH2HYP0N+8K0b3tWGjoO/vC0CrDNAnQnH+UHMik99/ASIcm50LqTZ5RYyyANgKa7o4h9eyvU2iPkGC4aAb0GV9ebN4fHwf/xhJeKyB+yUeUh/9n+opUfVG9WDGTInFg2Bz9IBBSHKs9HjXFJU40Imc9fp3xAnYFz5l+z+URROxXCitLfZa5vn6RbUID7TvfWxLdH2XN3tD9FlEM/DD7TNkQuqzA1tfsmRFR3pZpXOzFt3Mp22KyxSzZAgMBAAECggEBAINHuuz9euxbFvFmWqM9NcAzQSpetRKj2glsUop8QYWCd2Q5rmz1klz5wLiKLMsp/VGOJFVJhZQZHATvu/B/iow8LuoV4vtLBsUtkAsFp6G6yWuYfa7RCLbkB3n0y3/kdr2v3YH3xFTWFtgIr86JiYRfOgztrBypSyycdEKqls9R9Q/IvbM9VV8nQcGiRcGUFU1+2k3o/uQMbLkzzw2O4o6z8Onnyg3sb/k5SJgw+qZ1a2x5TyjnrgL/fompGgxnU/rybcpZalddIniH2xnMA2Bu6VF4KKp9xEX4XO59uoP7oWnDeeIhk8FzuLLojvgkvb3jfUo5yUvMezMN+yW6JEUCgYEA+aZbYYjXmsEAeRUibfQqtPOdQDyVpKah4Zl4dH0+XOhLGiTWFi3sQWjwvIS8BSeKxN7+7d9XOspJloh1OSW7lru/KBXgEEEfR0HPCMkajouERWibLHjKkK0YfPPb8eQCRUDfvWfc6/aG6fKV/2SrLSSrrMNoMRIEsKVwQPCEJUsCgYEAuWAGCylpwHWzsWurDKvg8/K8yA38mESbEhaEwHxbt4qZOG0Vj6lJpUDbuTK5d3qQsWjEqLWI58+i29JuyKZYDWZpMOIeAovWaJ1J/eIOtWrHpN5mFdHRegd3Yj/u6QHtMn3F13Wpi8fvH/XPBG4cpUx4hqfe1+7QZdcwTSF7M+sCgYBPM/AZ6Yg9mK3FvGbH64cBEaj6ED6JljQVltMYYgO/BhONn9IsBzXFKr31kUb1e64S94x5ezn5dOQT1gcaslXpZHcpCIeloIOD3+UHLIY4Yxr7ykwwt8HMVJ5IRjWpcGUoA4T4WlNEqVn0LvpXpGCKWe/9R6FusAC86j/K2oOSMQKBgGMAlpm1d2YzNlHmF8c/sHd4xzvsxF+qHEuPvTxz7iPbILOTBrXgCpX4SqDhJ05ZJhepxYX41fI/4VGXVFVTCam2bS2PRcnEVzEt98tgxMw9B/zUTh8PHNoYTLkrPfQxdDXIGDZW/8LId7xQ8kwCZsgdTC1WUFxwjdomEATRgdQbAoGBAId7WBdeUFSDbp4ksNKtDtUdYIsj+OGz5lq2yrMXnSMtwAfFCGE6c6mu7qe3M8BMR5C3M5c5w7UcjXEYzlLbIXFdYz3wWYTuNfAhK+e6RtktbimWXjLeTZwnb6xZkt5pBciooX0LP3eMrZUiIyEZA9VCt7Vlp7huRZZujXoACaL9";
    /*参数返回格式，只支持json*/
    public static final String FORMAT = "json";
    /*请求和签名使用的字符编码格式，支持GBK和UTF-8*/
    public static final String CHARSET = "UTF-8";
    /*支付宝公钥，由支付宝生成*/
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy4K2Wyqn3pvC/7CZ8nVmiwksYNI6av5Jpu8f2CnCOzdpVe9StYS931urQeldsf8+FUjv7oTDfbNjSUTzHRR2Awr117I8LtDOn4wy60WRsOxwFiSaJiNmSgzIR8lK85Y6VX+Rk/t2nluTgPmqQmqz5XGxm4zLiYHqQAnWjlbqttO2dxAURu1dzMLyaMLkq6NQwHuBxUIvgrIPuOAPOXrB7a3Ph8VwPpyEPQEzlBGn47HRfLMANlqD/Jovv6TN3RpdXpm38RZfTG8NJpwKaoM8lzVmhAXFw3cUsDOlDWV61Yy7BSqbB/IoyciSN68n2Q1R2MzhxcN/BtuAt8xSQRnQaQIDAQAB";
    /*商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2*/
    public static final String SIGN_TYPE = "RSA2";

    public static String OIL_NOTIFY = "http://web.banyuge.com/web/f/web/pay/receiveNotifyAliPay";//加油卡正式回调地址
    public static String SHOP_NOTIFY = "http://web.banyuge.com/web/f/web/payShop/receiveNotifyAliPay";//加油卡正式回调地址

    public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";//	交易创建，等待买家付款
    public static final String TRADE_CLOSED = "TRADE_CLOSED";//	未付款交易超时关闭，或支付完成后全额退款
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";//	交易支付成功
    public static final String TRADE_FINISHED = "TRADE_FINISHED";//	交易结束，不可退款

    //正式
    private static AlipayClient getAlipayClient() {
        AlipayClient client = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET,
                ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        return client;
    }

    public static String createOrder(HttpServletRequest req, OrderModel orderModel,String notifyUrl) {
        //实例化客户端
        String port = req.getServerPort() + "";

        AlipayClient alipayClient = getAlipayClient();

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(orderModel.getBody());
        model.setSubject(orderModel.getSubject());
        model.setOutTradeNo(orderModel.getOut_trade_no());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(orderModel.getTotal_amount());
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setGoodsType(orderModel.getGoods_type());
        request.setBizModel(model);

        request.setNotifyUrl(notifyUrl);//加油回调地址

        String bodystr = "";
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            bodystr = response.getBody();
            req.setAttribute("reqmsg", bodystr);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return bodystr;
    }

    public static void createOrderH5(HttpServletResponse respone, HttpServletRequest request, OrderModel orderModel) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID,
                APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                " \"out_trade_no\":\"" + System.currentTimeMillis() + "" + "\"," +
                " \"total_amount\":\"0.01\"," +
                " \"subject\":\"Iphone6 16G\"," +
                " \"product_code\":\"QUICK_WAP_PAY\"" +
                " }");//填充业务参数
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        respone.setContentType("text/html;charset=" + CHARSET);
        respone.getWriter().write(form);//直接将完整的表单html输出到页面
        respone.getWriter().flush();
        respone.getWriter().close();
    }

    public static void notityInfo(HttpServletRequest request, HttpServletResponse respone) {
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。
//                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            logger.info(requestParams.toString());
            logger.info(params.toString());
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);
            request.setAttribute("status", flag);
            request.setAttribute("out_trade_no", params.get("out_trade_no"));
            if (flag) {
                logger.info("验证签名成功");
                request.setAttribute("trade_status", params.get("trade_status"));
                PrintWriter out = respone.getWriter();
                out.println("success");
                respone.getWriter().flush();
                respone.getWriter().close();
                return;
            } else {
                logger.info("验证签名失败");
                PrintWriter out = respone.getWriter();
                out.println("failure");
                respone.getWriter().flush();
                respone.getWriter().close();
                return;
            }

        } catch (Exception e) {
            logger.info("验证签名失败,直接抛异常");
            try {
                e.printStackTrace();
                PrintWriter out = respone.getWriter();
                out.println("failure");
                respone.getWriter().flush();
                respone.getWriter().close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
