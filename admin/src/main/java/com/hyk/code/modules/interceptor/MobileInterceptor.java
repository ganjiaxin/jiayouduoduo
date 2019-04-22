package com.hyk.code.modules.interceptor;

import com.google.gson.Gson;
import com.hyk.code.common.service.BaseService;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.utils.UserAgentUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
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
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
							 Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
		if (modelAndView != null){
			// 如果是手机或平板访问的话，则跳转到手机视图页面。
			if(UserAgentUtils.isMobileOrTablet(request) && !StringUtils.startsWithIgnoreCase(modelAndView.getViewName(), "redirect:")){
				modelAndView.setViewName("mobile/" + modelAndView.getViewName());
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
								Object handler, Exception ex) throws Exception {

	}

}
