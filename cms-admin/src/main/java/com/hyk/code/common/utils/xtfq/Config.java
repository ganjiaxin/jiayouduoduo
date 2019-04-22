package com.hyk.code.common.utils.xtfq;



public class Config {
//http://www.yuanbaojr.com
	
	//final static String app_key="testhn";//通联商务提供
	final static String app_key="zylb";//正式通联商务提供
	final static String v="1.0";//版本号，目前默认值：1.0  通联商务提供
	final static String sign_v="1";//签名版本号，目前默认值：1   通联商务提供
	final static String format="json";//可选，响应数据格式：xml或json，默认json
	//final static String mer_id="999290053990002";//商户号：商户在通联商务注册的认证号码
	final static String mer_id="999361073920001";//正式商户号：商户在通联商务注册的认证号码
	final static String channel="1";//支付渠道：0：pc   1：wap；wap需要使用手机端浏览器
	final static String pdno="0200";//产品编号:固定值 0200
	final static String return_url="http://www.yuanbaojr.com/f/api/fqfa";//支付成功或失败后返回商户的页面，只是跳转，不带参数
	final static String notify_url="http://www.yuanbaojr.com/f/interfaceApi/notify";//支付成功或失败后异步通知的商户URL地址，只发送参数，详情见7.4
	
}
