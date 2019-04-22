package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.utils.excel.ExportExcel;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykOilCard;
import com.hyk.code.modules.hyk.service.HykOilCardService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加油卡管理Controller
 * @author 霍中曦
 * @version 2018-11-12
 */
@Api(description = "加油卡管理")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykOilCardController extends BaseController {

    @Autowired
    private HykOilCardService hykOilCardService;

    @ModelAttribute
    public HykOilCard get(@RequestParam(required=false) String id) {
        HykOilCard entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = hykOilCardService.get(id);
        }
        if (entity == null){
            entity = new HykOilCard();
        }
        return entity;
    }

    @RequestMapping(value ="hykOilCard")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "加油卡管理")
    public String list(@RequestParam("callback") String callback,HykOilCard hykOilCard, HttpServletRequest request, HttpServletResponse response, Model model) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","加油卡管理请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykOilCard> page = hykOilCardService.findPage(new Page<HykOilCard>(request, response), hykOilCard);
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }


    @RequestMapping(value = "hykOilCard/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "加油卡管理修改新增")
    public String save(@RequestParam("callback") String callback,HykOilCard hykOilCard, Model model) {

        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "保存加油卡请求成功");
            hykOilCardService.save(hykOilCard);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "保存加油卡请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykOilCard/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "加油卡管理删除")
    public String delete(@RequestParam("callback") String callback,HykOilCard hykOilCard, RedirectAttributes redirectAttributes) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除加油卡管理请求成功");
            hykOilCardService.delete(hykOilCard);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除加油卡管理请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }


    /*  导出工具*/
    @RequestMapping(value = "hykOilCard/reportExcel")
    @ApiOperation(httpMethod = "GET", value = "加油卡管理导出")
    public String exportFile(HykOilCard hykOilCard, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            List<HykOilCard> list = hykOilCardService.findList(hykOilCard);
            String fileName = DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel("加油卡管理数据", HykOilCard.class).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出工作任务失败！失败信息："+e.getMessage());
        }
        return null;
    }

}
