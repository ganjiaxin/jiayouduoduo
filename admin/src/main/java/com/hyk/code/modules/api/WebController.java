package com.hyk.code.modules.api;


import com.google.gson.Gson;
import com.hyk.code.common.UserUtils;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.common.utils.CacheUtils;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykYzm;
import com.hyk.code.modules.hyk.entity.ReportIndex;
import com.hyk.code.modules.hyk.service.ReportService;
import com.hyk.code.modules.sys.entity.Dict;
import com.hyk.code.modules.sys.entity.Menu;
import com.hyk.code.modules.sys.entity.User;
import com.hyk.code.modules.sys.service.DictService;
import com.hyk.code.modules.sys.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述: 公共接口
 * @auther: 霍中曦
 * @param:
 * @return:
 * @date: 2018/11/7 14:37
 */
@Api(description = "登录及公用接口")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebController extends BaseController{

    @Autowired
    private DictService dictService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private SystemService systemService;

    @ModelAttribute
    public User get(@RequestParam(required=false) String userId) {
        User entity = null;
        if (StringUtils.isNotBlank(userId)){
           // entity = systemService.getUser(userId);
        }
        if (entity == null){
            entity = new User();
        }
        return entity;
    }


    /**
     * 功能描述: swagger 接口文档
     * @auther: 霍中曦
     * @date: 2018/11/26 13:37
     */
    @RequestMapping(value = "inter",method = RequestMethod.GET)
    public String inter(HykYzm hykYzm, HttpServletRequest request) {
        return "/swagger/index";
    }

    /**
     * 功能描述:html5
     * @auther: 霍中曦
     * @date: 2018/11/26 15:09
     */
    @RequestMapping(value = "mobileIndex",method = RequestMethod.GET)
    public String mobileIndex(HykYzm hykYzm, HttpServletRequest request) {
        return "/hyk/index";
    }


        /**
         * 功能描述: pc登录功能
         * @auther: 霍中曦
         * @param: [hykUser, request, response, model]
         * @return: java.lang.String
         * @date: 2018/11/9 13:52
         */
    @RequestMapping(value = "login")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "登录接口")
    public String login(@RequestParam("callback") String callback,@RequestParam("loginName") String loginName,@RequestParam("password") String password ,HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try{
            User user =new User();
            user.setLoginName(loginName);
            user.setPassword(password);
            if(user.getLoginName()!=null){
                User old = systemService.getUserByLoginName(user.getLoginName());
                if(old!=null){
                    String password2=user.getPassword();
                    String entryptPassword=old.getPassword();
                    if(SystemService.validatePassword(password2,entryptPassword)){
                        map.put("code","200");
                        map.put("msg","登录成功！");
                        map.put("name",old.getName());
                        map.put("userId",old.getId());
                        map.put("token", TokenUtil.getToken(old.getLoginName(),old.getPassword(),old.getId()));
                        CacheUtils.remove(old.getId());
                        request.getSession().setAttribute("userId",old.getId());
                    }else{
                        map.put("msg","密码不正确！");
                        map.put("code","401");
                    }
                }else{
                    map.put("msg","用户不存在！");
                    map.put("code","402");
                }
            }else{
                map.put("code","400");
                map.put("msg","登录账号为空！");
            }
        }catch(Exception e){
            e.printStackTrace();
            map.put("code","500");
            map.put("msg","系统异常！");
        }
        return  callback+"("+gson.toJson(map)+")";
    }


    @RequestMapping(value = "menuList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "菜单接口")
    public String menuList(@RequestParam("callback") String callback,String token,HttpServletRequest request) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","菜单请求成功");
        List<Menu> resultList=new ArrayList<Menu>();
        User user=new User();
        user.setId(TokenUtil.getWebCurrentUser(token).getId());
        List<Menu> menuList= UserUtils.getMenuList(user);
        for(int i=0;i<menuList.size();i++){
            Menu menu=new Menu();
            menu.setId(menuList.get(i).getId());
            menu.setName(menuList.get(i).getName());
            menu.setHref(menuList.get(i).getHref());
            menu.setPermission(menuList.get(i).getPermission());
            menu.setChildMenu(menuList.get(i).getChildMenu());
            resultList.add(menu);
        }
        map.put("menuList",resultList);
        return  callback+"("+gson.toJson(map)+")";
    }

    /**
     * 功能描述: 获取下拉框内容
     * @auther: 霍中曦
     * @param: type 下拉框类型
     * @date: 2018/11/12 11:49
     */
    @RequestMapping(value = "dicList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "字典接口")
    public String dicList(@RequestParam("callback") String callback, Dict dict, HttpServletRequest request) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","下拉框请求成功");
        List<Dict> resultList=new ArrayList<Dict>();
        List<Dict> dictList=dictService.findList(dict);
        for(int i=0;i<dictList.size();i++){
            Dict dic=new Dict();
            dic.setId(dictList.get(i).getId());
            dic.setLabel(dictList.get(i).getLabel());
            dic.setValue(dictList.get(i).getValue());
            dic.setDelFlag(null);
            resultList.add(dic);
        }
        map.put("list",resultList);
        return  callback+"("+gson.toJson(map)+")";
    }



    //首页报表
    @RequestMapping(value = "reportIndex")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "首页报表")
    public String reportIndex(@RequestParam("callback") String callback,HttpServletRequest request){
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            map.put("code", "200");
            map.put("msg", "请求成功");
            ReportIndex report=reportService.getYesterDayInifo();
            if(report!=null){
                report.setPeopleNum(report.getPeopleNum()==null?0:report.getPeopleNum());
                report.setTotalAmt(report.getTotalAmt()==null?BigDecimal.ZERO:report.getTotalAmt());
                report.setOrderNum(report.getOrderNum()==null?0:report.getOrderNum());
                report.setOilAmt(report.getOilAmt()==null?BigDecimal.ZERO:report.getOilAmt());
                map.put("report",report);
            }else {
                report.setPeopleNum(0);
                report.setTotalAmt(BigDecimal.ZERO);
                report.setOrderNum(0);
                report.setOilAmt(BigDecimal.ZERO);
                map.put("report",report);
            }


        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    //首页选时报表
    @RequestMapping(value = "reportIndexTime")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "条件查询报表接口")
    public String reportIndexTime(@RequestParam("callback") String callback,ReportIndex reportObj,HttpServletRequest request){
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        try {

            map.put("code", "200");
            map.put("msg", "请求成功");
            ReportIndex report=reportService.getYesterDayFindList(reportObj);
            if(report!=null){
                report.setPeopleNum(report.getPeopleNum()==null?0:report.getPeopleNum());
                report.setTotalAmt(report.getTotalAmt()==null?BigDecimal.ZERO:report.getTotalAmt());
                report.setOrderNum(report.getOrderNum()==null?0:report.getOrderNum());
                report.setOilAmt(report.getOilAmt()==null?BigDecimal.ZERO:report.getOilAmt());
                map.put("report",report);
            }else {
                report.setPeopleNum(0);
                report.setTotalAmt(BigDecimal.ZERO);
                report.setOrderNum(0);
                report.setOilAmt(BigDecimal.ZERO);
                map.put("report",report);
            }


        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }




}
