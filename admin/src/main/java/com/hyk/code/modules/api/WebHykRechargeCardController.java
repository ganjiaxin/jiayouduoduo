package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.CreateCardUtils;
import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.utils.excel.ExportExcel;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykRechargeCard;
import com.hyk.code.modules.hyk.service.HykRechargeCardService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值卡管理Controller
 * @author 霍中曦
 * @version 2018-12-18
 */
@Api(description = "充值卡管理")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykRechargeCardController extends BaseController {

    @Autowired
    private HykRechargeCardService hykRechargeCardService;
    @Autowired
    private DictService dictService;

    @ModelAttribute
    public HykRechargeCard get(@RequestParam(required = false) String id) {
        HykRechargeCard entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hykRechargeCardService.get(id);
        }
        if (entity == null) {
            entity = new HykRechargeCard();
        }
        return entity;
    }

    @RequestMapping(value ="hykRechargeCard")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "卡密管理查询")
    public String list(@RequestParam(value = "token") String token,@RequestParam(value = "callback")String callback,HykRechargeCard hykRechargeCard, HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","卡密管理请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykRechargeCard> page = hykRechargeCardService.findPage(new Page<HykRechargeCard>(request, response), hykRechargeCard);
        for(int i=0;i<page.getList().size();i++){
            HykRechargeCard old=page.getList().get(i);
            if(StringUtils.isBlank(old.getCreateDateStr())){
                old.setCreateDateStr("");
                old.setOverDateStr("");
            }

            if(StringUtils.isBlank(old.getUserName())){
                old.setUseDateStr("");
                old.setUserName("");
            }
            old.setOverDateStr(StringUtils.isBlank(old.getOverDateStr())?"":old.getOverDateStr());

        }
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }


    @RequestMapping(value = "hykRechargeCard/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "根据id修改卡密状态")
    public String save(@RequestParam(value = "token") String token,@RequestParam("callback") String callback,@RequestParam("id") String id,@RequestParam("status") String status) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "保存充值卡管理成功");
            HykRechargeCard hykRechargeCard=hykRechargeCardService.get(id);
            hykRechargeCard.setStatus(status);
            hykRechargeCardService.save(hykRechargeCard);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "保存充值卡管理失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykRechargeCard/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "删除卡密")
    public String delete(@RequestParam(value = "token") String token,@RequestParam("callback")String callback,@RequestParam("id")String id) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除充值卡管理成功");
            HykRechargeCard hykRechargeCard=hykRechargeCardService.get(id);
            hykRechargeCardService.delete(hykRechargeCard);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除充值卡管理失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }


    /**
     * 功能描述: ；批量生成卡密记录
     * @auther: 霍中曦
     * @date: 2018/12/19 13:42
     */
    @RequestMapping(value = "hykRechargeCard/createCard")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "批量新增卡密")
    public String createCard(@RequestParam(value = "token") String token,@RequestParam("callback") String callback,@RequestParam("money") Long money,
                             @RequestParam("useMethod") String useMethod,@RequestParam("cardNum") int cardNum) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "卡密生成成功");

            Dict dict=new Dict();
            dict.setType("card_code");
            List<Dict> dicList=dictService.findList(dict);
            String code=dicList.get(0).getValue();
            code=(Integer.parseInt(code)+1)+"";

            HykRechargeCard hykRechargeCard=new HykRechargeCard();
            if("0".equals(useMethod)){
                hykRechargeCard.setSaleStatus("0");
            }else{
                hykRechargeCard.setSaleStatus("3");
            }
            hykRechargeCard.setStatus("0");
            hykRechargeCard.setCode(code);
            hykRechargeCard.setMoney(money);
            hykRechargeCard.setUseMethod(useMethod);
            hykRechargeCard.setCardNum(cardNum);
            List<HykRechargeCard> list= CreateCardUtils.createCard(hykRechargeCard);
            hykRechargeCardService.saveBatch(list);

            map.put("card_code",code);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "卡密生成失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }


    /**
     * 功能描述: 激活指定批次的卡密
     * @auther: 霍中曦
     * code 批次号必填 overDate 过期时间必填
     * @date: 2018/12/19 16:53
     */
    @RequestMapping(value = "hykRechargeCard/updateStatus")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "激活卡密")
    public String updateStatus(@RequestParam(value = "token") String token,@RequestParam("callback") String callback,@RequestParam(value = "code") String code,String overDate,Integer days) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "卡密修改成功");

            HykRechargeCard hykRechargeCard=new HykRechargeCard();
            hykRechargeCard.setCreateDate(new Date());
            hykRechargeCard.setStatus("1");
            hykRechargeCard.setCode(code);
            if(StringUtils.isNotBlank(overDate)){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                hykRechargeCard.setOverDate(sdf.parse(overDate+" 23:59:59"));
            }
            hykRechargeCard.setDays(days);
            hykRechargeCardService.updateBatch(hykRechargeCard);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "卡密修改失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }


    /*  导出工具*/
    @RequestMapping(value = "hykRechargeCard/reportExcel")
    @ApiOperation(httpMethod = "GET", value = "卡密管理数据")
    public String hykInviter(
            @RequestParam(value = "token") String token, Date startDate,Date endDate,String status,String useMethod,String other,String code,HttpServletRequest request, HttpServletResponse response) {
        try {
            HykRechargeCard hykRechargeCard=new HykRechargeCard();
            hykRechargeCard.setStartDate(startDate);
            hykRechargeCard.setEndDate(endDate);
            hykRechargeCard.setStatus(status);
            hykRechargeCard.setUseMethod(useMethod);
            hykRechargeCard.setOther(other);
            hykRechargeCard.setCode(code);
            List<HykRechargeCard> list = hykRechargeCardService.findList(hykRechargeCard);
            String fileName = DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            new ExportExcel("卡密管理数据", HykRechargeCard.class).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
