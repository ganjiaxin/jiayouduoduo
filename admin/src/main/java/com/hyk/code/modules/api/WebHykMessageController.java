package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykMessage;
import com.hyk.code.modules.hyk.service.HykMessageService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 站内信管理Controller
 * @author 霍中曦
 * @version 2018-12-18
 */
@Api(description = "站内信管理")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykMessageController extends BaseController {
    @Autowired
    private HykMessageService hykMessageService;

    @ModelAttribute
    public HykMessage get(@RequestParam(required=false) String id) {
        HykMessage entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = hykMessageService.get(id);
        }
        if (entity == null){
            entity = new HykMessage();
        }
        return entity;
    }

    @RequestMapping(value ="hykMessage")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "站内信管理")
    public String list(@RequestParam("callback") String callback, HykMessage hykMessage, HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","站内信管理请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykMessage> page = hykMessageService.findPage(new Page<HykMessage>(request, response), hykMessage);
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }


    @RequestMapping(value = "hykMessage/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "保存站内信")
    public String save(@RequestParam("callback") String callback,HykMessage hykMessage) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "保存站内信管理成功");
            if(StringUtils.isNotBlank(hykMessage.getId())){

                hykMessageService.save(hykMessage);
            }
            if(StringUtils.isBlank(hykMessage.getId())){
                if(StringUtils.isNotBlank(hykMessage.getPhones())){
                    String phone[]=hykMessage.getPhones().split(",");
                    for(int i=0;i<phone.length;i++){
                        HykMessage newObj=new HykMessage();
                        newObj.setPhones(phone[i]);
                        newObj.setMessStatus("0");
                        newObj.setCreateDate(new Date());
                        newObj.setType("1");//手动操作类型
                        newObj.setTitle(hykMessage.getTitle());
                        newObj.setContent(hykMessage.getContent());
                        hykMessageService.save(newObj);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "保存站内信管理失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykMessage/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "删除站内信")
    public String delete(@RequestParam("callback") String callback,@RequestParam("id") String id) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除站内信管理成功");
            HykMessage hykMessage=new HykMessage();
            hykMessage.setId(id);
            hykMessageService.delete(hykMessage);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除站内信管理失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykMessage/getId")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "单条记录请求")
    public String getId(@RequestParam("callback") String callback,@RequestParam("id") String id) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            map.put("code", "200");
            map.put("msg", "站内信请求成功");
            HykMessage hykMessage = hykMessageService.get(id);
            map.put("hykAppVersion",hykMessage);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "站内信信息请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }
}
