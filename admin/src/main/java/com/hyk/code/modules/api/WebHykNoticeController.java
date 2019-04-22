package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykNotice;
import com.hyk.code.modules.hyk.service.HykNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 公告管理Controller
 * @author 霍中曦
 * @version 2018-11-12
 */
@Api(description = "公告管理")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykNoticeController extends BaseController {

    @Autowired
    private HykNoticeService hykNoticeService;

    @ModelAttribute
    public HykNotice get(@RequestParam(required=false) String id) {
        HykNotice entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = hykNoticeService.get(id);
        }
        if (entity == null){
            entity = new HykNotice();
        }
        return entity;
    }

    @RequestMapping(value ="hykNotice")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公告管理")
    public String list(@RequestParam("callback") String callback,HykNotice hykNotice, HttpServletRequest request, HttpServletResponse response, Model model) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","公告管理页面请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykNotice> page = hykNoticeService.findPage(new Page<HykNotice>(request, response), hykNotice);
        for(int i=0;i<page.getList().size();i++){
            HykNotice old=page.getList().get(i);
            old.setDelFlag(null);
            old.setStartTime(null);
            old.setEndTime(null);
        }
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykNotice/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公告管理修改新增")
    public String save(@RequestParam("callback") String callback,HykNotice hykNotice) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "保存公告请求成功");
            hykNoticeService.save(hykNotice);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "保存公告请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykNotice/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公告管理删除")
    public String delete(@RequestParam("callback") String callback,HykNotice hykNotice) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除公告请求成功");
            hykNoticeService.delete(hykNotice);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除公告请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykNotice/getId")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "公告管理ID检索")
    public String getId(@RequestParam("callback") String callback,HykNotice hykNotice) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            map.put("code", "200");
            map.put("msg", "公告信息请求成功");
            hykNotice.setDelFlag(null);
            hykNotice.setStartTime(null);
            hykNotice.setEndTime(null);
            map.put("hykNotice",hykNotice);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "公告信息请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

}