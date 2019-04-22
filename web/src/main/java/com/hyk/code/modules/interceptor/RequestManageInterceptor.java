package com.hyk.code.modules.interceptor;

import com.google.gson.Gson;
import com.hyk.code.common.service.BaseService;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.modules.hyk.entity.RequestIp;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 短信拦截
 *
 * @author 霍中曦
 * @version 2014-9-1
 */
public class RequestManageInterceptor extends BaseService implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        Gson gson = new Gson();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = null;//返回给页面显示
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //取用户的真实IP
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader(" Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader(" WL-Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //取session中的IP对象
        RequestIp re = (RequestIp) request.getSession().getAttribute(ip);
        //第一次请求
        if (null == re) {
            //放入到session中
            RequestIp reIp = new RequestIp();
            reIp.setCreateTime(System.currentTimeMillis());
            reIp.setReCount(1);
            request.getSession().setAttribute(ip, reIp);
            return true;
        } else {
            Long createTime = re.getCreateTime();
            if (null == createTime) {
                //时间请求为空
                resultMap.put("code", 503);
                resultMap.put("msg", "短信请求频繁，请稍后再试！");
                out = response.getWriter();
                JSONObject responseJSONObject = JSONObject.fromObject(resultMap);
                out.append(responseJSONObject.toString());
                return false;
            } else {
                if (((System.currentTimeMillis() - createTime) / 1000) > 30) {
                    System.out.println("通过请求！" + ((System.currentTimeMillis() - createTime) / 1000));
                    //当前时间离上一次请求时间大于30秒，可以直接通过,保存这次的请求
                    RequestIp reIp = new RequestIp();
                    reIp.setCreateTime(System.currentTimeMillis());
                    reIp.setReCount(1);
                    request.getSession().setAttribute(ip, reIp);
                    return true;
                } else {
                    //小于30秒，并且30秒之内请求了3次，返回提示
                    //if(re.getReCount() > 3){
                    resultMap.put("code", 503);
                    resultMap.put("msg", "短信请求频繁，请稍后再试！");
                    out = response.getWriter();
                    JSONObject responseJSONObject = JSONObject.fromObject(resultMap);
                    out.append(responseJSONObject.toString());//以json形式返回给页面，也可以直接返回提示信息
                    return false;
//					}else{
//						//小于10秒，但请求数小于3次，给对象添加
//						re.setCreateTime(System.currentTimeMillis());
//						re.setReCount(re.getReCount()+1);
//						request.getSession().setAttribute(ip,re);
//						return false;
//					}
                }
            }
        }
    }

    /**
     * 功能描述:未登录返还结果
     *
     * @auther: 霍中曦
     * @date: 2018/11/10 10:54
     */
    public void failInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        //System.out.println("Web...最终");

    }

}
