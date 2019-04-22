package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykAppVersion;
import com.hyk.code.modules.hyk.service.HykAppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 版本管理Controller
 * @author 霍中曦
 * @version 2018-12-18
 */
@Api(description = "版本管理接口")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykAppVersionController extends BaseController {

    @Autowired
    private HykAppVersionService hykAppVersionService;

    @ModelAttribute
    public HykAppVersion get(@RequestParam(required=false) String id) {
        HykAppVersion entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = hykAppVersionService.get(id);
        }
        if (entity == null){
            entity = new HykAppVersion();
        }
        return entity;
    }

    @RequestMapping(value ="hykAppVersion")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "版本管理查询")
    public String list(@RequestParam("callback") String callback,HykAppVersion hykAppVersion, HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","版本管理请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykAppVersion> page = hykAppVersionService.findPage(new Page<HykAppVersion>(request, response), hykAppVersion);
        for(int i=0;i<page.getList().size();i++){
            HykAppVersion old=page.getList().get(i);
            old.setDelFlag(null);
        }
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykAppVersion/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "版本管理修改保存")
    public String save(@RequestParam("callback") String callback,HykAppVersion hykAppVersion) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "版本保存请求成功");
            hykAppVersion.setCreateDate(new Date());
            hykAppVersionService.save(hykAppVersion);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "版本保存请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }


    @RequestMapping(value = "hykAppVersion/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "版本管理删除")
    public String delete(@RequestParam("callback") String callback,HykAppVersion hykAppVersion) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除版本请求成功");
            hykAppVersionService.delete(hykAppVersion);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除版本请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykAppVersion/getId")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "版本管理Id检索")
    public String getId(@RequestParam("callback") String callback,HykAppVersion hykAppVersion) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            map.put("code", "200");
            map.put("msg", "版本请求成功");
            map.put("hykAppVersion",hykAppVersion);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "版本信息请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

}
