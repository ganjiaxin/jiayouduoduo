package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykAdvice;
import com.hyk.code.modules.hyk.service.HykAdviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 意见反馈管理Controller
 *
 * @author 霍中曦
 * @version 2018-11-09
 */
@Api(description = "意见反馈管理接口")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykAdviceController extends BaseController {

    @Autowired
    private HykAdviceService hykAdviceService;

    @ModelAttribute
    public HykAdvice get(@RequestParam(required = false) String id) {
        HykAdvice entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hykAdviceService.get(id);
        }
        if (entity == null) {
            entity = new HykAdvice();
        }
        return entity;
    }
    @RequestMapping(value = "hykAdvice")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "意见反馈查询")
    public String list(@ApiParam(required = true, name = "callback", value = "必填字段")String callback,HykAdvice hykAdvice,HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "200");
        map.put("msg", "意见反馈页面请求成功");
        map.put("permission",request.getAttribute("permission"));
        try {
            Page<HykAdvice> page = hykAdviceService.findPage(new Page<HykAdvice>(request, response), hykAdvice);
            map.put("page", page);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "意见反馈页面请求失败");
        }
        return callback + "(" + gson.toJson(map) + ")";
    }


    @RequestMapping(value = "hykAdvice/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "意见反馈修改保存")
    public String save(@RequestParam("callback") String callback,HykAdvice hykAdvice) {
         Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "意见反馈保存请求成功");
            hykAdviceService.save(hykAdvice);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "意见反馈保存请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }
}
