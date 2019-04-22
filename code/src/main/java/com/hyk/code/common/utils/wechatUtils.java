package com.hyk.code.common.utils;

//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyk.code.common.mapper.JsonMapper;
import com.hyk.code.common.utils.pojo.BaodanMessage;
import com.hyk.code.common.utils.pojo.HebaoMessage;
import com.hyk.code.common.utils.pojo.NewOrderMessage;
import com.hyk.code.common.utils.pojo.ZhifuMessage;
/**
 * Created by shengd on 2017-03-17.
 */
@SuppressWarnings("deprecation")
public class wechatUtils {
    private static Logger logger = LoggerFactory.getLogger(wechatUtils.class);
    private static PropertiesLoader loader = new PropertiesLoader("weixin.properties");
	
	public static final String web_appid=loader.getProperty("web_appid");
	public static final   String web_redirect_uri=loader.getProperty("web_redirect_uri");
	
	public static final   String sessionOutTime=loader.getProperty("sessionOutTime");
	public static final   String appid=loader.getProperty("appid");
	public static final   String appsecret=loader.getProperty("appsecret");
	public static final   String redirectUrl=loader.getProperty("redirectUrl");
	public static final   String scope=loader.getProperty("scope");
	
	public static final   String mch_id=loader.getProperty("mch_id");
	public static final   String payKey=loader.getProperty("payKey");
	public static final   String callbackUrl=loader.getProperty("callbackUrl");
	public static final   String unifiedOrderUrl=loader.getProperty("unifiedOrderUrl");
	
	public static final   String token = loader.getProperty("component_token");
	public static final   String encodingAesKey = loader.getProperty("encodingAesKey");
	public static final   String component_appid =loader.getProperty("component_appid");
	public static final   String component_appsecret=loader.getProperty("component_appsecret");
	public static final   String redirect_uri=loader.getProperty("component_redirect_uri");
	//管理员二维码生成地址
	public static final   String wxSaler_url = loader.getProperty("wxSaler_url");
	//保险公司菜单生成地址配置
	public static final   String wxMenu_url = loader.getProperty("wxMenu_url");
	//开发者模式
	public static final   boolean develeper ="true".equals(loader.getProperty("develeper"))?true:false;
	//信用卡支付 跳转页面
	
    												  
	public static PropertiesLoader loader2 = new PropertiesLoader("application.properties");
	public static final String projectName=loader2.getProperty("projectName");
	
	
	public static  String test_token="";
    
    /** 
    * @Title: getUserInfoAccessToken 
    * @Description: TODO(网页授权) 
    * @param code
    * @return Map<String,String> 返回类型
    * @date 2017年9月19日 上午11:53:22  
    * @author  wangyw
    */ 
    public static Map<String, String> getUserInfoAccessToken(String code) {
    	
        JsonMapper object = new JsonMapper();
        Map<String, String> data = new HashMap();
        try {
        	
            String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", appid, appsecret, code);
            logger.info("request accessToken from url: {}", url);
            String tokens = HttpKit.get(url);
            Map<String, String> JsonMapRet=object.fromJson(tokens, HashMap.class);
    		String access_token=JsonMapRet.get("access_token");
    		String openid=JsonMapRet.get("openid");
            data.put("openid", openid);
            data.put("access_token", access_token);
        } catch (Exception ex) {
            logger.error("fail to request wechat access token. [error={}]", ex);
        }
        return data;
    }

    /** 
    * @author  霍中曦
    * @Description: TODO(access_token是公众号的全局唯一票据) 
    * @date 2017-5-4 下午7:10:45
    * @param code
    * @return 
    * @return Map<String,String> 
    */ 
    public static Map<String, String> getAccessToken() {
        JsonMapper object = new JsonMapper();
        Map<String, String> data = new HashMap();
        try {
            String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appid, appsecret);
            logger.info("request accessToken from url: {}", url);
            String tokens = HttpKit.get(url);
            Map<String, String> JsonMapRet=object.fromJson(tokens, HashMap.class);
    		String access_token=JsonMapRet.get("access_token");
            data.put("access_token", access_token);
        } catch (Exception ex) {
            logger.error("fail to request wechat access token. [error={}]", ex);
        }
        return data;
    }
    
    /**
     * 获取用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public static Map<String, String> getUserInfo(String accessToken, String openId) {
        Map<String, String> data = new HashMap();
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId +"&lang=zh_CN";
        logger.info("request user info from url: {}", url);
        try {
            String response =HttpKit.get(url);
           // System.out.println("=======response======="+response);
            Map<String, String> userInfo=new JsonMapper().fromJson(response, HashMap.class);
            logger.info("get userinfo success. [result={}]", userInfo);
            data.put("openid", userInfo.get("openid"));//openid用户的唯一标识
            data.put("nickname", userInfo.get("nickname"));
            data.put("city", userInfo.get("city"));
            data.put("province", userInfo.get("province"));
            data.put("country", userInfo.get("country"));
            data.put("headimgurl", userInfo.get("headimgurl"));
        } catch (Exception ex) {
            logger.error("fail to request wechat user info. [error={}]", ex);
        }
        return data;
    }
    

  	/**
  	* ----------见注释知方法 ----------
  	* @Title: sendNotice
  	* @Description: 
  	* TODO(核保信息发送)
  	* @param openid
  	* @return boolean
  	* @date 2017年11月6日 下午11:40:55
  	* @author 霍中曦
  	* @throws
  	* 
  	* {{first.DATA}}
		产品名称：{{name.DATA}}
		核保结果：{{result.DATA}}
		{{remark.DATA}}
		您好，您购买的保险，未能通过公司核保
		
		产品名称：微互助
		核保结果：未通过核保
		若有疑问，请拨打咨询电话4000095522
  	*/ 
  	public static boolean sendHebao(String openid,String access_token,HebaoMessage hebaoMessage){
  		 String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
  		 String tourl=hebaoMessage.getTourl();
  	      String data="{"
							+"  \"touser\":\""+openid+"\","
							+"  \"template_id\":\"gNYdifkwec86Cu5y6tAY3NWaqjPGoY0W_iyus4nbZOw\","
							/*+"  \"url\":\""+tourl+"\","*/
							+"    \"topcolor\":\"#FF0000\","
							+"    \"data\":{"
							+"           \"first\": {"
							+"               \"value\":\""+hebaoMessage.getFirst()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"name\": {"
							+"               \"value\":\""+hebaoMessage.getName()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"result\": {"
							+"               \"value\":\""+hebaoMessage.getResult()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"remark\": {"
							+"               \"value\":\""+hebaoMessage.getRemark()+"\","
							+"               \"color\":\"#173177\""
							+"           }"
							+"   }"
							+"}";
  	      String aa= HttpKit.post(url, data);
  	      Map<String, Object> map2  =new JsonMapper().fromJson(aa, HashMap.class);
  	      System.out.println(map2.toString());
  	  return "0".equals(map2.get("errcode").toString());
  	}
  	
  	/**
  	* ----------见注释知方法 ----------
  	* @Title: sendNewOrder
  	* @Description: 
  	* TODO(新订单消息通知 核保人员)
  	* @param openid
  	* @param access_token
  	* @param hebaoMessage
  	* @return boolean
  	* @date 2017年11月11日 下午10:10:57
  	* @author 霍中曦
  	* @throws
  	*/ 
  	public static boolean sendNewOrder(String openid,String access_token,NewOrderMessage newOrderMessage){
 		 String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
 		 String tourl=newOrderMessage.getTourl();
 	      String data="{"
							+"  \"touser\":\""+openid+"\","
							+"  \"template_id\":\"WmuvyVnbqWewYCr5S8791jZLcX2zPj2C0iFNAeGRK04\","
							/*+"  \"url\":\""+tourl+"\","*/
							+"    \"topcolor\":\"#FF0000\","
							+"    \"data\":{"
							+"           \"first\": {"
							+"               \"value\":\""+newOrderMessage.getFirst()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"keyword1\": {"
							+"               \"value\":\""+newOrderMessage.getKeyword1()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"keyword2\": {"
							+"               \"value\":\""+newOrderMessage.getKeyword2()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"keyword3\": {"
							+"               \"value\":\""+newOrderMessage.getKeyword3()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"keyword4\": {"
							+"               \"value\":\""+newOrderMessage.getKeyword4()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"keyword5\": {"
							+"               \"value\":\""+newOrderMessage.getKeyword5()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"remark\": {"
							+"               \"value\":\""+newOrderMessage.getRemark()+"\","
							+"               \"color\":\"#173177\""
							+"           }"
							+"   }"
							+"}";
 	      String aa= HttpKit.post(url, data);
 	      Map<String, Object> map2  =new JsonMapper().fromJson(aa, HashMap.class);
 	      System.out.println(map2.toString());
 	  return "0".equals(map2.get("errcode").toString());
 	}
  	
  	/**
  	* ----------见注释知方法 ----------
  	* @Title: sendZhifu
  	* @Description: 
  	* TODO(订单支付消息通知)
  	* @param openid
  	* @param content
  	* @return boolean
  	* @date 2017年11月6日 下午11:52:20
  	* @author 霍中曦
  	* @throws
  	* {{first.DATA}}
		客户姓名：{{Custname.DATA}}
		投保产品：{{ProductName.DATA}}
		支付号：{{PayNo.DATA}}
		{{remark.DATA}} 
  	*/ 
  	public static boolean sendZhifu(String openid,String access_token,ZhifuMessage zhifuMessage){
 		 String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
 		 String  tourl=zhifuMessage.getTourl();
 	      String data="{"
							+"  \"touser\":\""+openid+"\","
							+"  \"template_id\":\"SWdoiNiioh4UOzpMPTIb8haxK12EFAXmeqhC0EMK5fE\","
							+"  \"url\":\""+tourl+"\","
							+"    \"topcolor\":\"#FF0000\","
							+"    \"data\":{"
							+"           \"first\": {"
							+"               \"value\":\""+zhifuMessage.getFirst()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"Custname\": {"
							+"               \"value\":\""+zhifuMessage.getCustname()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"ProductName\": {"
							+"               \"value\":\""+zhifuMessage.getProductName()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"PayNo\": {"
							+"               \"value\":\""+zhifuMessage.getPayNo()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"remark\": {"
							+"               \"value\":\""+zhifuMessage.getRemark()+"\","
							+"               \"color\":\"#173177\""
							+"           }"
							+"   }"
							+"}";
 	      String aa= HttpKit.post(url, data);
 	      Map<String, Object> map2  =new JsonMapper().fromJson(aa, HashMap.class);;
 	      System.out.println(map2.toString());
 	      return "0".equals(map2.get("errcode").toString());
 	}
  	
  	/**
  	* ----------见注释知方法 ----------
  	* @Title: sendChudan
  	* @Description: 
  	* TODO(出单通知)
  	* @param openid
  	* @param access_token
  	* @param baodanMessage
  	* @return boolean
  	* @date 2017年11月11日 下午10:23:25
  	* @author 霍中曦
  	* @throws
  	*/ 
  	public static boolean sendChudan(String openid,String access_token,BaodanMessage baodanMessage){
 		 String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
 		 String tourl=baodanMessage.getTourl();
 	      String data="{"
							+"  \"touser\":\""+openid+"\","
							+"  \"template_id\":\"a4Ur96FP-l5ijH7NppFUPgysLtiB88G1hejTE8EOafc\","
							/*+"  \"url\":\""+tourl+"\","*/
							+"    \"topcolor\":\"#FF0000\","
							+"    \"data\":{"
							+"           \"first\": {"
							+"               \"value\":\""+baodanMessage.getFirst()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"keyword1\": {"
							+"               \"value\":\""+baodanMessage.getKeyword1()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"keyword2\": {"
							+"               \"value\":\""+baodanMessage.getKeyword2()+"\","
							+"               \"color\":\"#173177\""
							+"           },"
							+"           \"remark\": {"
							+"               \"value\":\""+baodanMessage.getRemark()+"\","
							+"               \"color\":\"#173177\""
							+"           }"
							+"   }"
							+"}";
 	      String aa= HttpKit.post(url, data);
 	      Map<String, Object> map2  =new JsonMapper().fromJson(aa, HashMap.class);
 	      System.out.println(map2.toString());
 	  return "0".equals(map2.get("errcode").toString());
 	}

  	//获取第三方平台component_access_token
	public static String get_component_access_token(String ticket_value){
		String data="{"+
				"\"component_appid\":\""+component_appid+"\" ,"+
				"\"component_appsecret\": \""+component_appsecret+"\"," +
				"\"component_verify_ticket\": \""+ticket_value+"\" "+
				"}";
				
		String url="https://api.weixin.qq.com/cgi-bin/component/api_component_token";
		   String aa= HttpKit.post(url, data);
	      Map<String, Object> map =new JsonMapper().fromJson(aa, HashMap.class);;
	      System.out.println("========="+map.toString());
		return map.get("component_access_token")+"";
	}
  	
	//获取预授权码pre_auth_code
	public static String get_pre_auth_code(String component_access_token){
		String data="{"+
				"\"component_appid\":\""+component_appid+"\" "+
				"}";
				
		String url="https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token="+component_access_token;
		String aa= HttpKit.post(url, data);
	    Map<String, Object> map  =new JsonMapper().fromJson(aa, HashMap.class);
	    System.out.println("========="+map.toString());
		return map.get("pre_auth_code")+"";
	}
	
	///使用授权码换取公众号或小程序的接口调用凭据和授权信息
	public static Map<String, Object> get_authorizer_access_token(String auth_code_value, String component_access_token){
		String data="{"+
				"\"component_appid\":\""+component_appid+"\", "+
				"\"authorization_code\": \""+auth_code_value+"\" "+
				"}";
				
		  String url="https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token="+component_access_token;
		  String aa= HttpKit.post(url, data);
	      Map<String, Object> map  =new JsonMapper().fromJson(aa, HashMap.class);
	      System.out.println("========="+map.toString());
	      /**========={authorization_info={authorizer_appid=wxec1bf4c30beebeaa, 
	      authorizer_access_token=-s4u91Iru2yeBLwf7Hcbc0tm-daVnGi7XnFTcHPB5gZrA21Os5oG4LOeKFfJ6uYyCKMDE1tqynb-ZUTKPoH-Qglm9MprmminXRHeWXtGn3hFIGqUIK6kmvfI9buQdyOfAFGfAHDEJK, expires_in=7200,
	      authorizer_refresh_token=refreshtoken@@@fGtSWx2_GP1jYAZz5MIrntd2WA67gx6D8qsuuTHDKVM, 
	      func_info=[{funcscope_category={id=4}}, {funcscope_category={id=2}}, {funcscope_category={id=9}}]}}
	     */
	      Map<String, Object>map2=(Map<String, Object>)map.get("authorization_info");
	      System.out.println("=====map2===="+map2.toString());
	      return map2;
	}
	
	public static String get_auth_url(String pre_auth_code){
		
    	String url="https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid="
    	+component_appid+"&pre_auth_code="+pre_auth_code+"&redirect_uri="+redirect_uri;
		return url;
	}
	
	//客户端授权信息
	public static String clientAutoUrl(String appid,String scope) {
		String url="https://open.weixin.qq.com/connect/oauth2/authorize"
				+ "?appid="+appid
				+ "&redirect_uri="+web_redirect_uri
				+ "&response_type=code"
				+ "&scope="+scope
				+ "&state="+appid
				+ "&component_appid="+component_appid
				+ "#wechat_redirect";
				return url;
	}
	
	/**
	* ----------见注释知方法 ----------
	* @Title: getClientAcessToken
	* @Description: 
	* TODO(用户收取通过地址  获取用户accesstoken 信息)
	* @param appid
	* @param code
	* @param component_access_token
	* @return Map<String,Object>
	* @date 2017年11月3日 下午9:19:27
	* @author 霍中曦
	* @throws
	*/ 
	public static Map<String,Object> getClientAcessToken(String appid,String code,String component_access_token ) {
		String url="https://api.weixin.qq.com/sns/oauth2/component/access_token"
				+ "?appid="+appid
				+ "&code="+code
				+ "&grant_type=authorization_code"
				+ "&component_appid="+component_appid
				+ "&component_access_token="+component_access_token;
		 String aa= HttpKit.get(url);
	      Map<String, Object> map =new JsonMapper().fromJson(aa, HashMap.class);
	     System.out.println("===getClientAcessToken=="+map.toString());
		return map;
	}
	
	/**
	* ----------见注释知方法 ----------
	* @Title: getClientInfoByAccessToken
	* @Description: 
	* TODO(通过accesstokan 获取用户信息)
	* @param access_token
	* @param openid
	* @return Map<String,Object>
	* @date 2017年11月3日 下午9:19:58
	* @author 霍中曦
	* @throws
	*/ 
	public static Map<String,String> getClientInfoByAccessToken(String access_token,String openid ) {
	      String celient="https://api.weixin.qq.com/sns/userinfo"
	      						+ "?access_token="+access_token
	      						+ "&openid="+openid
	      						+ "&lang=zh_CN";
	      String bb= HttpKit.get(celient);
	      Map<String, String> map =new JsonMapper().fromJson(bb, HashMap.class);
	      System.out.println("===getClientInfoByAccessToken=="+map.toString());
	      //{country=中国, unionid=oOAvCwZTwQV1SRcG5sP_4tTCc1vY, province=安徽, city=合肥, openid=o0zLTwmYhIt05IEY03CidQhN5e4w, sex=1, nickname=王耀威, headimgurl=http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKuLsA1AAjUSPs595vTZ73R457sBg7qDa3ia5SsqFkTRG83ibc0Fd5HyxbxaxTNW8BN3TRBPnBBpibuA/0, language=zh_CN, privilege=[]}
		return map;
	}

	
	public static String getWxOpenId(String url) {
		  String bb= HttpKit.get(url);
	      Map<String, Object> map2 =new JsonMapper().fromJson(bb, HashMap.class);;
	      System.out.println("=== 用户信息======"+map2.toString());
		return map2.get("openid")+"";
	}
	//获取授权方的token
	public static Map<String, Object> api_authorizer_token(String authorizer_appid,String authorizer_refresh_token,String component_access_token) {
		
/*		component_appid	第三方平台appid
		authorizer_appid	授权方appid
		authorizer_refresh_token	授权方的刷新令牌，刷新令牌主要用于第三方平台获取和刷新已授权用户的access_token，只会在授权时刻提供，请妥善保存。一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌
		*/
		String data="{"+
				"\"component_appid\":\""+component_appid+"\","+
				"\"authorizer_appid\":\""+authorizer_appid+"\","+
				"\"authorizer_refresh_token\":\""+authorizer_refresh_token+"\""+
				"}";
				
		String url="https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token="+component_access_token;
		String aa= HttpKit.post(url, data);
	    Map<String, Object> map  =new JsonMapper().fromJson(aa, HashMap.class);
	    System.out.println("========="+map.toString());
		return map;
	}
	
	//jssdk获取jsapi_ticket
    public static  Map<String, Object>  getJsapiTicket(){
     String access_token=getAccessToken().get("access_token");
      String url1="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
	  String str1=HttpKit.get(url1);
	  Map<String, Object>  map  =new JsonMapper().fromJson(str1, HashMap.class);
	  System.out.println("========="+map.toString());
	  map.put("access_token", access_token);
	  return map;
    }	
	
	//jssdk获取jsapi_ticket
    public static  Map<String, Object>  getJsapiTicket(String authorizerAccessToken){
      String url1="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+authorizerAccessToken+"&type=jsapi";
	  String str1=HttpKit.get(url1);
	  Map<String, Object>  map  =new JsonMapper().fromJson(str1, HashMap.class);
	  System.out.println("========="+map.toString());
	  return map;
    }
    
	//jssdk获取jsapi_ticket
    public static  String  pjnm(){
		String pjnm="";
		if(wechatUtils.develeper) {
			pjnm="/"+wechatUtils.projectName;
		}
	  return pjnm;
    }	
    
    public static void main(String[] args) {
    	String token="pcud_4rWx8aYLCdQuTlfo8l_KqRu2FI0oU-BXNeGyZmUyxoNpjcNtH-MrQ3Y2AWVhbf0_9WbdHLpo__CNcgIvEAOMvHWPO_E3-EXyBK7AWvClcOia2twKOhh7Jom8EnEINRjAIAMRV";
    	Map<String,Object> map=api_authorizer_token("wxe557bd08a5d4944b","refreshtoken@@@dP1BSceqWuUR87qNc7_hSAJGnYR98lEBapERdq1Wjbg",token);
    	
    	 System.out.println(map.get("authorizer_access_token")+"");
    	 System.out.println(map.get("authorizer_refresh_token")+"");
		 
    	//System.out.println(pjnm());
    	//getJsapiTicket("YJ93V_CHOczsdLMudn6KiVypWm9o5PntqOgAYkzQmeDFB04XJAtEf1H2NMeww24RDvzsGHe0oioN7O-v5cbWcQF8HzHzPqcsLJ5upYHUXhSPu6j5zAgP3D04QkDA2qwiFEKjAJDPOU");
	/*	String json="{"+
	    "\"access_token\": \"xou7i-UHz1K_aWdPKoxg2LqBpKv5mrEEOW_aVNTGpM8hh1jczXdmY93hWgC6qLCGV0e7WWBqR5t0vmvfQ_kXShQo2xxkwfPJ9OeAKMLcAQY\","+
	    "\"expires_in\": 7200,"+
	    "\"refresh_token\": \"Ta_c5UexDn6onmLbGSSlWngyEVlyZ45MjK4j8iGKIKZyjWqmUu7c0m5mBe3YSMliFyy_0b-MRyKWLHXI3m0iE8br83UKLXc1zwlwcC5GO3Q\","+
	    "\"openid\": \"oMca8weHUVCkkYhkT-c-vaMRcv1Q\","+
	    "\"scope\": \"snsapi_base\""+
    	"}\"";

        JsonMapper object = new JsonMapper();
        Map<String, String> JsonMapRet=object.fromJson(json, HashMap.class);
		String access_token=JsonMapRet.get("access_token");
		String openid=JsonMapRet.get("openid");
		System.out.println(access_token);
		System.out.println(openid);*/
    	//System.out.println("==============="+wxSaler_url);
	}
}
