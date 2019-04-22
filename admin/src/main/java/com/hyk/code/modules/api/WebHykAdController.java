package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykAd;
import com.hyk.code.modules.hyk.service.HykAdService;
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
 * 广告管理Controller
 * @author 霍中曦
 * @version 2018-11-12
 */
@Api(description = "广告管理接口")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykAdController extends BaseController {

    @Autowired
    private HykAdService hykAdService;

    @ModelAttribute
    public HykAd get(@RequestParam(required=false) String id) {
        HykAd entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = hykAdService.get(id);
        }
        if (entity == null){
            entity = new HykAd();
        }
        return entity;
    }

    @RequestMapping(value ="hykAd")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "广告管理查询")
    public String list(@RequestParam("callback") String callback,HykAd hykAd, HttpServletRequest request, HttpServletResponse response, Model model) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","广告管理请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykAd> page = hykAdService.findPage(new Page<HykAd>(request, response), hykAd);
        for(int i=0;i<page.getList().size();i++){
            HykAd old=page.getList().get(i);
            old.setDelFlag(null);
            old.setStartTime(null);
            old.setEndTime(null);
            old.setSort(StringUtils.isBlank(old.getSort())?"":old.getSort());
            old.setGoodsImg(StringUtils.isBlank(old.getGoodsImg())?"":old.getGoodsImg());
        }
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykAd/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "广告管理保存修改")
    public String save(@RequestParam("callback") String callback,HykAd hykAd) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "广告保存请求成功");
            if(StringUtils.isBlank(hykAd.getSort())){
                hykAd.setSort("10");
            }
            hykAdService.save(hykAd);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "广告保存请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykAd/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "广告管理删除")
    public String delete(@RequestParam("callback") String callback,HykAd hykAd) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除广告请求成功");
            hykAdService.delete(hykAd);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除广告请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykAd/getId")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "广告记录接口")
    public String getId(@RequestParam("callback") String callback,HykAd hykAd) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            map.put("code", "200");
            map.put("msg", "公告信息请求成功");
            hykAd.setShareTitle(StringUtils.isBlank(hykAd.getShareTitle())?"":hykAd.getShareTitle());
            hykAd.setShareUrl(StringUtils.isBlank(hykAd.getShareUrl())?"":hykAd.getShareUrl());
            hykAd.setShareSonTitle(StringUtils.isBlank(hykAd.getShareSonTitle())?"":hykAd.getShareSonTitle());
            hykAd.setDelFlag(null);
            hykAd.setStartTime(null);
            hykAd.setEndTime(null);
            hykAd.setSort(StringUtils.isBlank(hykAd.getSort())?"":hykAd.getSort());
            hykAd.setGoodsImg(StringUtils.isBlank(hykAd.getGoodsImg())?"":hykAd.getGoodsImg());
            map.put("hykAd",hykAd);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "公告信息请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }


}