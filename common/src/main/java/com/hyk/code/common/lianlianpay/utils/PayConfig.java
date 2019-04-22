package com.hyk.code.common.lianlianpay.utils;

/**
 * @Auther: 霍中曦
 * @Date: 2018/12/5 09:31
 * @Description:
 */
public interface PayConfig {
    // 银通公钥
    String YT_PUB_KEY="";
    // 商户公钥
    String TRADER_PUB_KEY = "";
    // 商户私钥
    String TRADER_PRI_KEY ="";
    // 接收异步通知地址加油订单
    String NOTIFY_URL     = "/f/web/pay/receiveNotify";
    // 接收异步通知地址商城订单
    String NOTIFY_SHOPURL     = "/f/web/payShop/receiveNotify";
    // 支付结束后返回地址
    String URL_RETURN     = "/mobile/#/payCallback";

    // 接收异步通知地址加油订单
    String NOTIFY_URL_TEST     = "/f/web/pay/receiveNotify";
    // 接收异步通知地址商城订单
    String NOTIFY_SHOPURL_TEST     = "/f/web/payShop/receiveNotify";
    // 支付结束后返回地址
    String URL_RETURN_TEST     = "/mobile/#/payCallback";

    // 商户编号
    String OID_PARTNER = "";
    // 签名方式 RSA或MD5
    String SIGN_TYPE      = "RSA";
    // 接口版本号，固定1.0
    String VERSION        = "1.0";

    // 业务类型，连连支付根据商户业务为商户开设的业务类型； （101001：虚拟商品销售、109001：实物商品销售、108001：外部账户充值）
    String BUSI_PARTNER   = "101001";


    String BILL_CREATE_URL = "https://payserverapi.lianlianpay.com/v1/paycreatebill"; // 支付单创建地址


}
