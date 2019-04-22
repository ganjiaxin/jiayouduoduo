package com.hyk.code.modules.api;


import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykMallGoods;
import com.hyk.code.modules.hyk.service.HykMallGoodsService;
import com.hyk.code.modules.sys.entity.Dict;
import com.hyk.code.modules.sys.service.DictService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商城商品管理Controller
 * @author wyy
 * @version 2018-12-21
 */
@Api(description = "商城商品管理")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykMallGoodsController extends BaseController {

    @Autowired
    private HykMallGoodsService hykMallGoodsService;
    @Autowired
    private DictService dictService;

    @ModelAttribute
    public HykMallGoods get(@RequestParam(required=false) String id) {
        HykMallGoods entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = hykMallGoodsService.get(id);
        }
        if (entity == null){
            entity = new HykMallGoods();
        }
        return entity;
    }

    @RequestMapping(value ="hykMallGoods")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "商城商品管理")
    public String list(@RequestParam("token") String token,@RequestParam("callback") String callback,HykMallGoods hykMallGoods, HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","商城商品管理请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykMallGoods> page = hykMallGoodsService.findPage(new Page<HykMallGoods>(request, response), hykMallGoods);
        for(HykMallGoods obj:page.getList()){
            obj.setIshot(StringUtils.isBlank(obj.getIshot())?"":obj.getIshot());
            obj.setViewpic(StringUtils.isBlank(obj.getViewpic())?"":obj.getViewpic());
        }
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }


    @RequestMapping(value = "hykMallGoods/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "商城商品管理修改新增")
    public String save(@RequestParam("token") String token,@RequestParam("callback") String callback,HykMallGoods hykMallGoods) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "保存商城商品请求成功");
            hykMallGoods.setStatus("0");
            //优化 自定添加库存字段
            hykMallGoodsService.save(hykMallGoods);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "保存商城商品请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykMallGoods/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "商城商品管理删除")
    public String delete(@RequestParam("token") String token,@RequestParam("callback") String callback,@RequestParam("id") String id) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除商城商品请求成功");
            HykMallGoods hykMallGoods=hykMallGoodsService.get(id);
            hykMallGoodsService.delete(hykMallGoods);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除商城商品请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykMallGoods/getId")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "单条记录查询接口")
    public String getId(@RequestParam("token") String token,@RequestParam("callback") String callback,@RequestParam("id") String id) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            map.put("code", "200");
            map.put("msg", "商品信息请求成功");
            HykMallGoods obj=hykMallGoodsService.get(id);
            obj.setIshot(StringUtils.isBlank(obj.getIshot())?"":obj.getIshot());
            obj.setViewpic(StringUtils.isBlank(obj.getViewpic())?"":obj.getViewpic());
            map.put("hykMallGoods",obj);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "商品信息请求成功");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

/**
 * 功能描述: 新增模块
 * @auther: 霍中曦
 * @date: 2018/12/23 15:49
 */
    @RequestMapping(value = "hykMallGoods/addCategroy")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "商品模块新增")
    public String addCategroy(@RequestParam("token") String token,@RequestParam("callback") String callback,String name) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "商品模块新增请求成功");

            Dict obj=new Dict();
            obj.setType("mall_category");
            List<Dict> list=dictService.findList(obj);

            String valueStr="0";
            if(list.size()>0){
                valueStr=list.get(list.size()-1).getValue();
            }

            int value = Integer.parseInt(valueStr)+1;

            Dict dict=new Dict();
            dict.setValue(value+"");
            dict.setType("mall_category");
            dict.setLabel(name);
            dict.setSort(value);
            dictService.save(dict);

        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "商品模块新增请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

}
