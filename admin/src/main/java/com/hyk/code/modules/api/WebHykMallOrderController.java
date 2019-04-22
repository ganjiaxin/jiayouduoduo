package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.utils.excel.ExportExcel;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykMallOrder;
import com.hyk.code.modules.hyk.service.HykMallOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * 商城商品订单Controller
 *
 * @author wyy
 * @version 2018-12-21
 */
@Api(description = "商城商品订单")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykMallOrderController extends BaseController {

    @Autowired
    private HykMallOrderService hykMallOrderService;

    @ModelAttribute
    public HykMallOrder get(@RequestParam(required = false) String id) {
        HykMallOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hykMallOrderService.get(id);
        }
        if (entity == null) {
            entity = new HykMallOrder();
        }
        return entity;
    }

    @RequestMapping(value ="hykMallOrder")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "商城商品订单管理")
    public String list(@RequestParam("token") String token,@RequestParam("callback") String callback, HykMallOrder hykMallOrder, HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","商城商品管理请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykMallOrder> page = hykMallOrderService.findPage(new Page<HykMallOrder>(request, response), hykMallOrder);
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykMallOrder/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "商城商品订单管理修改新增")
    public String save(@RequestParam("token") String token,@RequestParam("callback") String callback,HykMallOrder hykMallOrder) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "保存商城商品订单请求成功");
            hykMallOrderService.save(hykMallOrder);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "保存商城商品订单请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykMallOrder/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "商城商品订单删除")
    public String delete(@RequestParam("token") String token,@RequestParam("callback") String callback,@RequestParam("id") String id) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除商城商品订单请求成功");
            HykMallOrder hykMallOrder=hykMallOrderService.get(id);
            hykMallOrderService.delete(hykMallOrder);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除商城商品订单请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykMallOrder/getId")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "单条订单记录查询接口")
    public String getId(@RequestParam("token") String token,@RequestParam("callback") String callback,@RequestParam("id") String id) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            map.put("code", "200");
            map.put("msg", "商品订单信息请求成功");
            HykMallOrder hykMallOrder=hykMallOrderService.get(id);
            map.put("hykMallOrder",hykMallOrder);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "商品订单信息请求成功");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value ="hykMallOrderSend")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "商城商品订单发货管理")
    public String hykMallOrderSend(@RequestParam("token") String token,@RequestParam("callback") String callback, HykMallOrder hykMallOrder, HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","商城商品管理请求成功");
        map.put("permission",request.getAttribute("permission"));
        hykMallOrder.setGoodsType("1");//必须是实物商品
        Page<HykMallOrder> page = hykMallOrderService.findPage2(new Page<HykMallOrder>(request, response), hykMallOrder);
        for(int i=0;i<page.getList().size();i++){
            HykMallOrder old=page.getList().get(i);
            old.setDelFlag(null);
            old.setWltypeStr(StringUtils.isBlank(old.getWltypeStr())?"":old.getWltypeStr());
            old.setPostid(StringUtils.isBlank(old.getPostid())?"":old.getPostid());
            old.setRemark(StringUtils.isBlank(old.getRemark())?"":old.getRemark());
            old.setSendStatusStr(StringUtils.isBlank(old.getSendStatusStr())?"":old.getSendStatusStr());
        }
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }


/**
 * 功能描述: 修改发货状态
 * @auther: 霍中曦
 * @date: 2018/12/24 9:28
 * cardNo 油卡号
 */

    @RequestMapping(value = "hykMallOrder/updateSendStatus")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "商城商品订单管理修改新增")
    public String updateSendStatus(@RequestParam("token") String token,@RequestParam("callback") String callback,HykMallOrder hykMallOrder,String cardNo) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "保存商城商品订单请求成功");
            if("1".equals(hykMallOrder.getGoodsType())){
                    //实物发货状态修改
                    hykMallOrder.setSendStatus("1");
                    boolean status=hykMallOrderService.updateSendStatus(hykMallOrder,cardNo);
                    if(!status){
                        map.put("msg", "保存商城商品订单请求成功 短信发送失败");
                    }
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "保存商城商品订单请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    /*  导出工具*/
    @RequestMapping(value = "hykMallOrder/reportExcel")
    @ApiOperation(httpMethod = "GET", value = "商城商品订单导出")
    public String exportFile(HykMallOrder hykMallOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            List<HykMallOrder> list = hykMallOrderService.findList(hykMallOrder);

            String fileName = DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel("商城商品订单", HykMallOrder.class).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出工作任务失败！失败信息："+e.getMessage());
        }
        return null;
    }

    /*  导出工具*/
    @RequestMapping(value = "hykMallOrder/reportExcelSend")
    @ApiOperation(httpMethod = "GET", value = "发货信息导出")
    public String exportFileSend(HykMallOrder hykMallOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            hykMallOrder.setGoodsType("1");//必须是实物商品
            List<HykMallOrder> list = hykMallOrderService.findList2(hykMallOrder);
            String fileName = DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel("发货信息导出", HykMallOrder.class).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出工作任务失败！失败信息："+e.getMessage());
        }
        return null;
    }


}
