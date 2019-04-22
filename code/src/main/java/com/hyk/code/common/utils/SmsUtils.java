package com.hyk.code.common.utils;

import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 *
 * @auther: 霍中曦
 * @param:
 * @return:聚合短信接口
 * @date: 2018/11/20 16:39
 */

public class SmsUtils {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0" +
            ".1547.66 Safari/537.36";

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @param code  验证码
     */
    public static void mobileQuery(String phone, String code) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", phone);//接受短信的用户手机号码
        params.put("tpl_id", "151210");//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value", "#code#=" + code);//您设置的模板变量，根据实际情况修改
        params.put("key", "be8afe8b9ca77f3e631c73682251e32a");//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户套餐充值到账
     *
     * @param phone
     * @param cardNo
     * @param cardType
     * @param cycle
     * @param num
     */
    public static void packageMsgQuery(String phone, String cardNo, String cardType, String cycle, String num,
                                       String money) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", phone);//接受短信的用户手机号码
        params.put("tpl_id", "151600");//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value",
                "#cardNo#=" + cardNo + "&#cardType#=" + cardType + "&#cycle#=" + cycle + "&#num#=" + num + "&#money" +
                        "#=" + money);//您设置的模板变量，根据实际情况修改
        params.put("key", "be8afe8b9ca77f3e631c73682251e32a");//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 注册手机号和加油卡手机号不同时 发送到加油卡手机号
     * 套餐充值发送短信
     *
     * @param cardPhone
     * @param userPhone
     * @param name
     * @param cardNo
     * @param cardType
     * @param cycle
     * @param num
     * @param money     【加油多多】亲爱的用户，您好，手机号码尾号为#userPhone#向#name#尾号为#cardNo#的#cardType#加油卡的#cycle#月套餐充值第#num#期#money
     *                  #元已到账，祝您生活愉快。
     */
    public static void packageMsgQuery2(String cardPhone, String userPhone, String name, String cardNo,
                                        String cardType, String cycle, String num, String money) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", cardPhone);//接受短信的用户手机号码
        params.put("tpl_id", "");//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value", "");//您设置的模板变量，根据实际情况修改
        params.put("key", "");//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户即时到账发送短信
     *
     * @param phone
     * @param cardNo
     * @param cardType
     */
    public static void immediatelyMsgQuery(String phone, String cardNo, String cardType, String money) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", phone);//接受短信的用户手机号码
        params.put("tpl_id", "151603");//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value", "#cardNo#=" + cardNo + "&#cardType#=" + cardType + "&#money#=" + money);
        //您设置的模板变量，根据实际情况修改
        params.put("key", "be8afe8b9ca77f3e631c73682251e32a");//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 注册手机号和加油卡手机号不同时 发送到加油卡手机号
     * 即时充值发送短信
     *
     * @param cardPhone
     * @param userPhone
     * @param name
     * @param cardNo
     * @param cardType
     */
    public static void immediatelyMsgQuery2(String cardPhone, String userPhone, String name, String cardNo,
                                            String cardType, String money) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", cardPhone);//接受短信的用户手机号码
        params.put("tpl_id", "");//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value", "");//您设置的模板变量，根据实际情况修改
        params.put("key", "");//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 即时充值支付成功以绑卡发送短信
     *
     * @param phone
     * @param cardNo
     * @param cardType
     * @param money
     * @param month
     * @param day
     */
    public static void packagePayMsg1(String phone, String cardNo, String cardType, String money, String month,
                                      String day) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", phone);//接受短信的用户手机号码
        params.put("tpl_id", "");//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value", "");//您设置的模板变量，根据实际情况修改
        params.put("key", "");//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 即时充值支付成功未绑卡发送短信
     *
     * @param phone
     * @param cardNo
     * @param cardType
     * @param money
     * @param month
     * @param day
     */
    public static void packagePayMsg3(String phone, String cardNo, String cardType, String money, String month,
                                      String day) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", phone);//接受短信的用户手机号码
        params.put("tpl_id", "");//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value", "");//您设置的模板变量，根据实际情况修改
        params.put("key", "");//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 套餐支付成功已绑卡发送短信
     *
     * @param phone
     * @param cardNo
     * @param cardType
     * @param cycle
     * @param money
     * @param month
     * @param day
     */
    public static void packagePayMsg2(String phone, String cardNo, String cardType, String cycle, String money,
                                      String month, String day) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", phone);//接受短信的用户手机号码
        params.put("tpl_id", "");//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value", "");//您设置的模板变量，根据实际情况修改
        params.put("key", "");//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 套餐支付成功未绑卡发送短信
     *
     * @param phone
     * @param cardNo
     * @param cardType
     * @param cycle
     * @param money
     * @param month
     * @param day
     */
    public static void packagePayMsg4(String phone, String cardNo, String cardType, String cycle, String money,
                                      String month, String day) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", phone);//接受短信的用户手机号码
        params.put("tpl_id", "");//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value", "");//您设置的模板变量，根据实际情况修改
        params.put("key", "");//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 实物兑换点击购买成功短信
     *
     * @param phone
     * @param goodsName
     */
    public static void realGoodsBuySuccess(String phone, String goodsName) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", phone);//接受短信的用户手机号码
        params.put("tpl_id", "");//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value", "");//您设置的模板变量，根据实际情况修改
        params.put("key", "");//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 实物发放成功短信
     *
     * @param phone
     * @param month
     * @param day
     * @param goodsName
     */
    public static void realGoodsSend(String phone, String month, String day, String goodsName) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", phone);//接受短信的用户手机号码
        params.put("tpl_id", "");//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value", "");//您设置的模板变量，根据实际情况修改
        params.put("key", "");//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 余额充值卡短信通知
     *
     * @param phone     接收短信手机号
     * @param goodsName 商品名称
     * @param num       购买数量
     * @param list      用来创建短信主题内容
     *                  【加油多多】亲爱的用户您好，您购买的商品 #goodsName#，数量为#num#;#txt#请及时兑换，祝您生活愉快。
     */
    public static void balanceMessage(String phone, String goodsName, Integer num, List<Map<String, Object>> list) {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", phone);//接受短信的用户手机号码
        params.put("tpl_id", "151606");//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value", "#goodsName#="+goodsName+"&#num#="+num+"");//您设置的模板变量，根据实际情况修改
        params.put("key", "be8afe8b9ca77f3e631c73682251e32a");//应用APPKEY(应用详细页查询)
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 功能描述:实物发货通知
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/12/28 13:52
     * 【加油多多】亲爱的惠友，您于#payDate#在油大大APP购买的#goodsName#商品现已发货，请前往油大大APP，点击我的订单查看商品物流信息，祝您使用愉快。
     */
    public static boolean sendGoodsMess(String phone, String payDate, String goodsName) throws Exception {
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("mobile", phone);//接受短信的用户手机号码
        params.put("tpl_id", "151605");//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value", "#payDate#=" + payDate + "&#goodsName#=" + goodsName);//您设置的模板变量，根据实际情况修改
        params.put("key", "be8afe8b9ca77f3e631c73682251e32a");//应用APPKEY(应用详细页查询)
        result = net(url, params, "GET");
        JSONObject object = JSONObject.fromObject(result);
        if (object.getInt("error_code") == 0) {
            System.out.println(object.get("result"));
            return true;
        } else {
            System.out.println(object.get("error_code") + ":" + object.get("reason"));
            return false;
        }
    }

    public static String createTxt(List<Map<String, Object>> list) {
        String str = "";
        for (int i = 1; i <= list.size(); i++) {
            str = str + "卡片" + createNum(i) + "：验证卡号为：" + list.get(i - 1).get("caredno") + "，秘钥为" + list.get(i - 1).get("password") + "；";
        }

        return str;
    }

    public static String createTxt2(List<Map<String, Object>> list) {
        String str = "";
        for (int i = 1; i <= list.size(); i++) {
            str = str + "卡片" + createNum(i) + "：验证卡号为：" + list.get(i - 1).get("caredno") + "，秘钥为" + "******；";
        }

        return str;
    }

    public static String createNum(int num) {
        String str = "";
        switch (num) {
            case 1:
                str = str + "一";
                break;
            case 2:
                str = str + "二";
                break;
            case 3:
                str = str + "三";
                break;
            case 4:
                str = str + "四";
                break;
            case 5:
                str = str + "五";
                break;
        }

        return str;
    }


    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String, String> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }



    public static void main(String[] args) {
        try {
            mobileQuery("18578747704", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}