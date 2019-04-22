package com.hyk.code.modules.interceptor;

import com.google.gson.Gson;
import com.hyk.code.common.UserUtils;
import com.hyk.code.common.service.BaseService;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.modules.sys.entity.Menu;
import com.hyk.code.modules.sys.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 霍中曦
 * @Date: 2018/11/8 18:35
 * @Description:
 *preHandle：在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
 *postHandle：在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView （这个博主就基本不怎么用了）；
 *afterCompletion：在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面）；
 */
public class WebInterceptor extends BaseService implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String mHttpUrlName=request.getRequestURI();//项目名称+文件路径
        String mScriptName=request.getServletPath();//文件路径
        String serverName=request.getServerName();//服务地址
        int port=request.getServerPort();//端口
        String mServerUrl="http://"+request.getServerName()+":"+request.getServerPort()+mHttpUrlName.substring(0,mHttpUrlName.lastIndexOf(mScriptName));//取得完整URL
        //System.out.println("======================"+mServerUrl);
        //System.out.println("======================"+mHttpUrlName);
        //System.out.println("======================"+mScriptName);

        if(mHttpUrlName.indexOf("login")>0||mHttpUrlName.indexOf("mobileIndex")>0){
            return true;
        }else{
            String token=(String)request.getParameter("token");
            if(StringUtils.isEmpty(token)){
                failInfo(request,response);
                return false;
            }else{
                String params=TokenUtil.isLogin(token);
                if("success".equals(params)){
                    if(rolesMenu(request,token)){
                        return true;
                    }else{
                        failInfo2(request,response);
                        return  false;
                    }
                }else{
                    failInfo(request,response);
                    return false;
                }
            }
        }
    }
    //验证用户权限
    public boolean rolesMenu(HttpServletRequest request,String token){
        String userId=(String)request.getSession().getAttribute("userId");
        String url=request.getServletPath();//文件路径
        List<Menu> menuList;
        User user=new User();
        if(StringUtils.isBlank(userId)){
            user.setId(TokenUtil.getWebCurrentUser(token).getId());
            menuList= UserUtils.getMenuList(user);
        }else{
            user.setId(userId);
            menuList= UserUtils.getMenuList(user);
        }
        List<Menu> menulist2=null;
        List<Menu> menulist3=null;

        for (int i=0;i<menuList.size();i++){
            menulist2=menuList.get(i).getChildMenu();
            if(url.equals(menuList.get(i).getPermission())){
                setPermission(menulist2,request);
               return true;
            }
            if(menulist2!=null&&menulist2.size()>0){
                for (int j=0;j<menulist2.size();j++){
                    menulist3=menulist2.get(j).getChildMenu();
                    if(url.equals(menulist2.get(j).getPermission())){
                        setPermission(menulist3,request);
                        return true;
                    }

                    if(menulist3!=null&&menulist3.size()>0){
                        for (int k=0;k<menulist3.size();k++){
                            if(url.equals(menulist3.get(k).getPermission())){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        if(defaulPass(url)){
            return  true;
        }
        return false;
    }

    public void setPermission(List<Menu> menulist,HttpServletRequest request){
        String permission="";
        if(menulist!=null){
            for(Menu m:menulist){
                permission+=","+m.getHref();
            }
            if(StringUtils.isNotBlank(permission)){
                request.setAttribute("permission",permission.substring(1,permission.length()));
            }
        }
    }

    /**
     * 功能描述: 是否免验证
     * @auther: 霍中曦
     * @date: 2019/1/8 11:35
     */
    public boolean defaulPass(String url){
        List<String> list=new ArrayList<String>();
        list.add("/f/web/menuList");
        list.add("/f/web/reportIndexTime");
        list.add("/f/web/reportIndex");
        list.add("/f/web/dicList");
        for(int i=0;i<list.size();i++){
            if(url.equals(list.get(i))){
                return true;
            }

        }
        return false;
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
        map.put("msg","未登录");
        String callback=request.getParameter("callback");
        if(StringUtils.isBlank(callback)){
            response.getWriter().print(gson.toJson(map));
        }else{
            response.getWriter().print(callback+"("+gson.toJson(map)+")");
        }
    }

    /**
     * 功能描述:未登录返还结果
     * @auther: 霍中曦
     * @date: 2018/11/10 10:54
     */
    public void failInfo2(HttpServletRequest request, HttpServletResponse response)throws Exception {
        Gson gson=new Gson();
        response.reset();
        response.setContentType( "application/json");
        response.setCharacterEncoding("utf-8");
        Map<String,String> map=new HashMap<String,String>();
        map.put("code","404");
        map.put("msg","该用户没有权限！");
        String callback=request.getParameter("callback");
        if(StringUtils.isBlank(callback)){
            response.getWriter().print(gson.toJson(map));
        }else{
            response.getWriter().print(callback+"("+gson.toJson(map)+")");
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object handler, Exception ex) throws Exception {

    }

}
