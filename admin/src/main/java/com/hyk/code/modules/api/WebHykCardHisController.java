package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykCardHis;
import com.hyk.code.modules.hyk.service.HykCardHisService;
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
import java.util.Map;

/**
 * 充值卡兑换记录Controller
 * @author 霍中曦
 * @version 2018-12-18
 */
@Api(description = "充值卡兑换记录接口")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykCardHisController extends BaseController {


    @Autowired
    private HykCardHisService hykCardHisService;

    @ModelAttribute
    public HykCardHis get(@RequestParam(required = false) String id) {
        HykCardHis entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hykCardHisService.get(id);
        }
        if (entity == null) {

            entity = new HykCardHis();
        }
        return entity;
    }

    @RequestMapping(value = "hykCardHis")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "充值卡兑换记录")
    public String list(@RequestParam("callback") String callback,HykCardHis hykCardHis, HttpServletRequest request, HttpServletResponse response) {

        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","充值卡兑换管理请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykCardHis> page = hykCardHisService.findPage(new Page<HykCardHis>(request, response), hykCardHis);
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }


    @RequestMapping(value = "hykCardHis/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "充值卡兑换记录修改保存")
    public String save(@RequestParam("callback") String callback,HykCardHis hykCardHis) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "保存充值卡兑换记录成功");
            hykCardHisService.save(hykCardHis);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "保存充值卡兑换记录失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykCardHis/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "充值卡兑换记录删除")
    public String delete(@RequestParam("callback") String callback,HykCardHis hykCardHis) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除充值卡兑换记录成功");
            hykCardHisService.delete(hykCardHis);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除充值卡兑换记录失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

}
