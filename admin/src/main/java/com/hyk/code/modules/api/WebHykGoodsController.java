package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykGoods;
import com.hyk.code.modules.hyk.service.HykGoodsService;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品管理Controller
 * @author 霍中曦
 * @version 2018-11-09
 */
@Api(description = "销售商品管理")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykGoodsController extends BaseController {

    @Autowired
    private HykGoodsService hykGoodsService;

    @ModelAttribute
    public HykGoods get(@RequestParam(required=false) String id) {
        HykGoods entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = hykGoodsService.get(id);
        }
        if (entity == null){
            entity = new HykGoods();
        }
        return entity;
    }

    @RequestMapping(value ="hykGoods")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "商品管理查询")
    public String list(@RequestParam("callback") String callback,HykGoods hykGoods, HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","用户页面请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykGoods> page = hykGoodsService.findPage(new Page<HykGoods>(request, response), hykGoods);
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykGoods/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "新增/修改商品接口")
    public String save(@RequestParam("callback") String callback,HykGoods hykGoods) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "商品保存请求成功");
            if(hykGoods.getPrices()!=null&&hykGoods.getDiscount()!=null){
                    if(hykGoods.getDiscount().compareTo(BigDecimal.ZERO)>0){
                        hykGoods.setDiscountMoney(hykGoods.getPrices().multiply(hykGoods.getDiscount()));
                    }
            }
            if(hykGoods.getGoodsType().equals("1")){
                hykGoods.setCycle(1L);
            }
            hykGoods.setStatus("1");

            if(hykGoods.getAdviceMoney2()!=null||hykGoods.getAdviceMoney1()!=null){
                hykGoods.setIstop("1");
            }else{
                hykGoods.setIstop("");
            }
            //默认500 不明白的需求
            if(hykGoods.getAdviceMoney1()==null){
                hykGoods.setAdviceMoney1(new BigDecimal(500));
            }
            if(hykGoods.getAdviceMoney2()==null){
                hykGoods.setAdviceMoney2(new BigDecimal(500));
            }


            hykGoodsService.save(hykGoods);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "商品保存请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykGoods/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "删除商品接口")
    public String delete(@RequestParam("callback") String callback,@RequestParam("id") String id) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除商品请求成功");
            HykGoods hykGoods=new HykGoods();
            hykGoods.setId(id);
            hykGoodsService.delete(hykGoods);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除商品请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykGoods/getId")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "单条记录查询接口")
    public String getId(@RequestParam("callback") String callback,HykGoods hykGoods) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            map.put("code", "200");
            map.put("msg", "商品信息请求成功");
            hykGoods.setAdviceMoney1(hykGoods.getAdviceMoney1()==null?BigDecimal.ZERO:hykGoods.getAdviceMoney1());
            hykGoods.setAdviceMoney2(hykGoods.getAdviceMoney2()==null?BigDecimal.ZERO:hykGoods.getAdviceMoney2());
            map.put("hykGoods",hykGoods);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "商品信息请求成功");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

}
