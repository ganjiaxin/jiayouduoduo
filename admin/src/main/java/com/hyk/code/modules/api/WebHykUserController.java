package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.common.utils.HttpKit;
import com.hyk.code.common.utils.PropertiesLoader;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.utils.excel.ExportExcel;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykUser;
import com.hyk.code.modules.hyk.service.HykUserService;
import com.hyk.code.modules.sys.entity.Dict;
import com.hyk.code.modules.sys.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 霍中曦
 * @Date: 2018/11/10 14:44
 * @Description:
 */
@Api(description = "用户管理")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykUserController extends BaseController {

    private static PropertiesLoader loader = new PropertiesLoader("application.properties");
    public static final String hykweb=loader.getProperty("hyk-web");

    @Autowired
    private HykUserService hykUserService;
    @Autowired
    private DictService dictService;

    @ModelAttribute
    public HykUser get(@RequestParam(required=false) String id) {
        HykUser entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = hykUserService.get(id);
        }
        if (entity == null){
            entity = new HykUser();
        }
        return entity;
    }

    @RequestMapping(value = "hykUser")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "用户管理")
    //@SystemLog(module=SystemLog.Module.USER,operateName=SystemLog.Operate.QUERY)
    public String hykUser(@RequestParam("callback") String callback, HykUser hykUser, HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","用户页面请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykUser> page = hykUserService.findPage(new Page<HykUser>(request, response), hykUser);
        for(int i=0;i<page.getList().size();i++){
            HykUser old=page.getList().get(i);
            old.setInviterId(old.getInviterId()!=null?old.getInviterId():"");
            old.setOilCardNum(old.getOilCardNum()!=null?old.getOilCardNum():0);
            old.setPayNum(old.getPayNum()!=null?old.getPayNum():0);
            old.setWaitAmt(old.getWaitAmt()!=null?old.getWaitAmt(): BigDecimal.ZERO);
            old.setChannel(old.getChannel()!=null?old.getChannel():"");
            old.setRemark(old.getRemark()!=null?old.getRemark():"");

            old.setCompanyName(old.getCompanyName()!=null?old.getCompanyName():"");
            old.setIsCompanyStr(old.getIsCompanyStr()!=null?old.getIsCompanyStr():"");
            old.setIsBossStr(old.getIsBossStr()!=null?old.getIsBossStr():"");

            old.setDelFlag(null);
            old.setPassword(null);
            old.setAccumulativeRechargeAmount(old.getAccumulativeRechargeAmount()!=null?old.getAccumulativeRechargeAmount():BigDecimal.ZERO);
            old.setAccountBalance(old.getAccountBalance()!=null?old.getAccountBalance():BigDecimal.ZERO);
        }
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }



    @RequestMapping(value = "hykUser/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "用户管理删除")
    public String delete(@RequestParam("callback") String callback,HykUser hykUser) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "用户删除请求成功");
            hykUserService.delete(hykUser);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "用户删除请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }


    /*  导出工具*/
    @RequestMapping(value = "hykUser/reportExcel")
    @ApiOperation(httpMethod = "GET", value = "用户管理数据")
    public String exportFile(HykUser hykUser, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            List<HykUser>  list = hykUserService.findList( hykUser);
            String fileName = DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel("用户管理数据", HykUser.class).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出工作任务失败！失败信息："+e.getMessage());
        }
        return null;
    }



    @RequestMapping(value = "hykUser/getId")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "用户管理ID检索")
    public String getId(@RequestParam("callback") String callback,HykUser hykUser) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            map.put("code", "200");
            map.put("msg", "用户信息请求成功");
            map.put("hykUser",hykUser);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "用户信息请求成功");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    /**
     * 功能描述: 商户新增接口
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2019/1/5 14:14
     */
    @RequestMapping(value = "hykUser/addCompany")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "商户新增")
    public String addCompany(@RequestParam("token") String token,@RequestParam("callback") String callback,@RequestParam("name")String name) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "商户新增请求成功");

            Dict obj=new Dict();
            obj.setType("companyId");
            List<Dict> list=dictService.findList(obj);

            String valueStr="0";
            if(list.size()>0){
                valueStr=list.get(list.size()-1).getValue();
            }

            int value=Integer.parseInt(valueStr)+1;
            Dict dict=new Dict();
            dict.setValue(value+"");
            dict.setType("companyId");
            dict.setLabel(name);
            dict.setSort(value);
            dictService.save(dict);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "商户新增请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }


    @RequestMapping(value = "hykUser/createQrcode")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "手动生成邀请码")
    public String createQrcode(@RequestParam("callback") String callback,HykUser hykUser) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        try {

            String respon= HttpKit.httpsRequest(hykweb+"/f/app/createQrcode?id="+hykUser.getId(),"GET","");
            JSONObject jsonObject= JSONObject.fromObject(respon);
            if(!"200".equals(jsonObject.get("code"))) {
                map.put("code", "406");
                map.put("msg", "操作成功！邀请码生成失败 ");
                return callback + "(" + gson.toJson(map) + ")";
            }

            map.put("code", "200");
            map.put("msg", "操作成功！");
            return  callback+"("+gson.toJson(map)+")";

        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "操作失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

}
