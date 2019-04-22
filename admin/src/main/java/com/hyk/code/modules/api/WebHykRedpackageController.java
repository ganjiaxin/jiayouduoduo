package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.utils.excel.ExportExcel;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykGoods;
import com.hyk.code.modules.hyk.entity.HykRedpackage;
import com.hyk.code.modules.hyk.entity.HykUser;
import com.hyk.code.modules.hyk.service.HykGoodsService;
import com.hyk.code.modules.hyk.service.HykRedpackageService;
import com.hyk.code.modules.hyk.service.HykUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 红包管理Controller
 *
 * @author 霍中曦
 * @version 2018-11-12
 */
@Api(description = "红包管理")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykRedpackageController extends BaseController {

    @Autowired
    private HykRedpackageService hykRedpackageService;
    @Autowired
    private HykUserService hykUserService;

    @Autowired
    private HykGoodsService hykGoodsService;

    @ModelAttribute
    public HykRedpackage get(@RequestParam(required = false) String id) {
        HykRedpackage entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hykRedpackageService.get(id);
        }
        if (entity == null) {
            entity = new HykRedpackage();
        }
        return entity;
    }

    @RequestMapping(value = "hykRedpackage")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "红包管理")
    public String list(@RequestParam("callback") String callback, HykRedpackage hykRedpackage, HttpServletRequest request, HttpServletResponse response, Model model) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "200");
        map.put("msg", "红包管理请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykRedpackage> page = hykRedpackageService.findPage(new Page<HykRedpackage>(request, response), hykRedpackage);
        for (int i = 0; i < page.getList().size(); i++) {
            HykRedpackage old = page.getList().get(i);
            old.setDelFlag(null);
            old.setSendTime(null);
            old.setOverTime(null);
            old.setUseTime(null);
            old.setGoodsId(StringUtils.isBlank(old.getGoodsId())?"":old.getGoodsId());
            old.setGoodsName(StringUtils.isBlank(old.getGoodsName())?"":old.getGoodsName());
        }

        map.put("page", page);
        return callback + "(" + gson.toJson(map) + ")";
    }


    @RequestMapping(value = "hykRedpackage/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "红包管理修改新增")
    public String save(@RequestParam("callback") String callback, HykRedpackage hykRedpackage) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        try {
            map.put("code", "200");
            map.put("msg", "保存红包请求成功");
            if(StringUtils.isBlank(hykRedpackage.getUserId())){
                map.put("code", "400");
                map.put("msg", "发放账户不能为空");
                return callback + "(" + gson.toJson(map) + ")";
            }

            if(hykRedpackage.getMinAmount()==null){
                hykRedpackage.setMinAmount(BigDecimal.ZERO);
            }

            if (hykRedpackage.getDayNum() != null && hykRedpackage.getDayNum() > 0) {
                hykRedpackage.setSendTime(new Date());
                hykRedpackage.setOverTime(DateUtils.addDay(hykRedpackage.getSendTime(), hykRedpackage.getDayNum()));
            }
            if (hykRedpackage.getStatus() == null) {
                hykRedpackage.setStatus("0");
            }

            if(StringUtils.isBlank(hykRedpackage.getId())){
                //新增
                String userids[]=hykRedpackage.getUserId().split(",");
                HykUser hykUser=new HykUser();
                for(int i=0;i<userids.length;i++){
                    hykUser.setPhone(userids[i]);
                    HykUser hykUser2=hykUserService.getByPhone(hykUser);
                    if(hykUser2!=null){
                        HykRedpackage newObj=new HykRedpackage();
                        newObj.setUserId(hykUser2.getId());
                        newObj.setTitle(hykRedpackage.getTitle());
                        newObj.setMoney(hykRedpackage.getMoney());
                        newObj.setDayNum(hykRedpackage.getDayNum());
                        newObj.setMinAmount(hykRedpackage.getMinAmount());
                        newObj.setRedType(hykRedpackage.getRedType());
                        newObj.setStatus(hykRedpackage.getStatus());
                        newObj.setSendTime(hykRedpackage.getSendTime());
                        newObj.setOverTime(hykRedpackage.getOverTime());
                        newObj.setGoodsId(hykRedpackage.getGoodsId());
                        hykRedpackageService.save(newObj);
                    }
                }
            }else{
                //修改
                    HykUser hykUser=new HykUser();
                    hykUser.setPhone(hykRedpackage.getUserId());
                    HykUser hykUser2=hykUserService.getByPhone(hykUser);
                    if(hykUser2!=null){
                        hykRedpackage.setUserId(hykUser2.getId());
                        hykRedpackageService.save(hykRedpackage);
                    }
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "保存红包请求失败");
        }
        return callback + "(" + gson.toJson(map) + ")";

    }


    @RequestMapping(value = "hykRedpackage/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "红包管理删除")
    public String delete(@RequestParam("callback") String callback, HykRedpackage hykRedpackage) {

        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除红包管理请求成功");
            hykRedpackageService.delete(hykRedpackage);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除红包管理请求失败");
        }
        return callback + "(" + gson.toJson(map) + ")";
    }

    @RequestMapping(value = "hykRedpackage/getId")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "红包管理ID检索")
    public String getId(@RequestParam("callback") String callback, HykRedpackage hykRedpackage) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("code", "200");
            map.put("msg", "红包信息请求成功");
            hykRedpackage.setDelFlag(null);
            hykRedpackage.setSendTime(null);
            hykRedpackage.setOverTime(null);
            hykRedpackage.setUseTime(null);
            hykRedpackage.setGoodsId(StringUtils.isBlank(hykRedpackage.getGoodsId())?"":hykRedpackage.getGoodsId());
            hykRedpackage.setGoodsName(StringUtils.isBlank(hykRedpackage.getGoodsName())?"":hykRedpackage.getGoodsName());
            map.put("hykRedpackage", hykRedpackage);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "红包信息请求失败");
        }
        return callback + "(" + gson.toJson(map) + ")";
    }


    @RequestMapping(value = "hykRedpackage/goodsList")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "所有商品信息")
    public String goodsList(@RequestParam("callback") String callback) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("code", "200");
            map.put("msg", "所以商品信息请求成功");
            List<HykGoods> list=hykGoodsService.findList(new HykGoods());
            List<HykGoods> page=new ArrayList<HykGoods>();
            HykGoods first=new HykGoods();
            first.setId("");
            first.setGoodsName("无限制");
            page.add(first);
            for(HykGoods obj:list){
                HykGoods news=new HykGoods();
                news.setId(obj.getId());
                news.setGoodsName(obj.getGoodsName());
                page.add(news);
            }
            map.put("goodsList", page);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "所以商品信息请求失败");
        }
        return callback + "(" + gson.toJson(map) + ")";
    }

    /*  导出工具*/
    @RequestMapping(value = "hykRedpackage/reportExcel")
    @ApiOperation(httpMethod = "GET", value = "订单管理数据")
    public String exportFile(HykRedpackage hykRedpackage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            List<HykRedpackage> list = hykRedpackageService.findList(hykRedpackage);
            String fileName = DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel("红包管理数据", HykRedpackage.class).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出工作任务失败！失败信息："+e.getMessage());
        }
        return null;
    }


}