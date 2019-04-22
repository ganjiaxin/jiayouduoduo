package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykInvoice;
import com.hyk.code.modules.hyk.service.HykInvoiceService;
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
 * 发票管理Controller
 * @author 霍中曦
 * @version 2018-11-12
 */
@Api(description = "充发票管理")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykInvoiceController extends BaseController {

    @Autowired
    private HykInvoiceService hykInvoiceService;

    @ModelAttribute
    public HykInvoice get(@RequestParam(required=false) String id) {
        HykInvoice entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = hykInvoiceService.get(id);
        }
        if (entity == null){
            entity = new HykInvoice();
        }
        return entity;
    }

    @RequestMapping(value ="hykInvoice")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "充发票管理")
    public String list(@RequestParam("callback") String callback,HykInvoice hykInvoice, HttpServletRequest request, HttpServletResponse response, Model model) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","发票管理请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykInvoice> page = hykInvoiceService.findPage(new Page<HykInvoice>(request, response), hykInvoice);
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }


    @RequestMapping(value = "hykInvoice/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "充发票管理修改新增")
    public String save(@RequestParam("callback") String callback,HykInvoice hykInvoice, Model model) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "保存发票请求成功");
            hykInvoiceService.save(hykInvoice);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "保存发票请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykInvoice/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "充发票管理删除")
    public String delete(@RequestParam("callback") String callback,HykInvoice hykInvoice) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除发票管理请求成功");
            hykInvoiceService.delete(hykInvoice);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除发票管理请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }


}