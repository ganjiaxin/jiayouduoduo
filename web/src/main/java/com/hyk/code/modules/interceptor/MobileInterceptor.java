package com.hyk.code.modules.interceptor;

import com.google.gson.Gson;
import com.hyk.code.common.service.BaseService;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.common.utils.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 霍中曦
 * @Date: 2018/11/8 18:35
 * @Description:
 */

/**
 * 手机端视图拦截器
 * @author 霍中曦
 * @version 2014-9-1
 */
public class MobileInterceptor extends BaseService implements HandlerInterceptor {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
    /*if (modelAndView != null){
            // 如果是手机或平板访问的话，则跳转到手机视图页面。
            if(UserAgentUtils.isMobileOrTablet(request) && !StringUtils.startsWithIgnoreCase(modelAndView.getViewName(), "redirect:")){
                modelAndView.setViewName("mobile/" + modelAndView.getViewName());
            }
        }*/
		//System.out.println("Web...后置拦截器");
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
							 Object handler) throws Exception {

		//System.out.println("Web...前置");
		String mHttpUrlName=request.getRequestURI();//项目名称+文件路径
		String mScriptName=request.getServletPath();//文件路径
		String serverName=request.getServerName();//服务地址
		int port=request.getServerPort();//端口

		String mServerUrl="https://"+request.getServerName()+":"+request.getServerPort()+mHttpUrlName.substring(0,mHttpUrlName.lastIndexOf(mScriptName));//取得OfficeServer文件的完整URL

		/*System.out.println(mHttpUrlName);
        System.out.println(mScriptName);
        System.out.println(serverName);
        System.out.println(port);*/
		System.out.println(mServerUrl+mScriptName);

		response.addHeader("Access-Control-Allow-Origin", "*");// '*'表示允许所有域名访问，可以设置为指定域名访问，多个域名中间用','隔开
		// 如果IE浏览器则设置头信息如下
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");

		if(mHttpUrlName.indexOf("login")>0||mHttpUrlName.indexOf("register")>0||mHttpUrlName.indexOf("download/image")>0||mHttpUrlName.indexOf("test")>0||mHttpUrlName.indexOf("checkPhone")>0||mHttpUrlName.indexOf("checkCode")>0||mHttpUrlName.indexOf("getCode")>0||mHttpUrlName.indexOf("updatepwd")>0||mHttpUrlName.indexOf("createQrcode")>0){
			return true;
		}else{
			String token=(String)request.getParameter("token");
			if(StringUtils.isEmpty(token)){
				failInfo(request,response);
				return false;
			}else{
				String params=TokenUtil.isLogin(token);
				System.out.println("验证token:"+params);
				System.out.println("验证token结果:"+"success".equals(params));
				if("success".equals(params)){
					return true;
				}else{
					failInfo(request,response);
					return false;
				}
			}
		}
	}
	/**
	 * 功能描述:未登录返还结果
	 * @auther: 霍中曦
	 * @date: 2018/11/10 10:54
	 */
	public void failInfo(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Gson gson=new Gson();
		response.reset();
		response.setContentType( "application/json");
		response.setCharacterEncoding("utf-8");
		Map<String,String> map=new HashMap<String,String>();
		map.put("code","500");
		map.put("msg","用户未登录");
		response.getWriter().print(gson.toJson(map));
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
								Object handler, Exception ex) throws Exception {
		//System.out.println("Web...最终");

	}

}
