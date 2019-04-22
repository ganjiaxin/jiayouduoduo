package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.utils.excel.ExportExcel;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykOilManager;
import com.hyk.code.modules.hyk.entity.ReportIndex;
import com.hyk.code.modules.hyk.service.*;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加油管理Controller
 *
 * @author 霍中曦
 * @version 2018-11-09
 */
@Api(description = "加油管理")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykOilManagerController extends BaseController {

    @Autowired
    private HykOilManagerService hykOilManagerService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private HykGoodsService hykGoodsService;
    @Autowired
    private HykOilCardService hykOilCardService;
    @Autowired
    private HykUserService hykUserService;

    @ModelAttribute
    public HykOilManager get(@RequestParam(required = false) String id) {
        HykOilManager entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hykOilManagerService.get(id);
        }
        if (entity == null) {
            entity = new HykOilManager();
        }
        return entity;
    }

    @RequestMapping(value = "hykOilManager")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "加油管理")
    public String list(@RequestParam("callback") String callback, HykOilManager hykOilManager,
                       HttpServletRequest request, HttpServletResponse response, Model model) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "200");
        map.put("msg", "加油管理页面请求成功");
        map.put("permission", request.getAttribute("permission"));
        Page<HykOilManager> page = hykOilManagerService.findPage(new Page<HykOilManager>(request, response),
                hykOilManager);
        for (int i = 0; i < page.getList().size(); i++) {
            HykOilManager old = page.getList().get(i);
            old.setDelFlag(null);
            old.setOilDate(null);
            old.setPlanOilDate(null);
            old.setCreateDate(null);
            old.getHykOrder().setCardNo(StringUtils.isBlank(old.getHykOrder().getCardNo()) ? "未绑定加油卡" :
                    old.getHykOrder().getCardNo());
            old.setOilDateStr(old.getOilDateStr() == null ? "" : old.getOilDateStr());
        }
        map.put("page", page);
        ReportIndex report = new ReportIndex();
        report.setStartTime(hykOilManager.getStartTime());
        report.setEndTime(hykOilManager.getEndTime());
        ReportIndex reportObj = reportService.getYesterDayFindList(report);

        ReportIndex webreport = new ReportIndex();
        if (reportObj != null) {
            webreport.setOilAmt(reportObj.getOilAmt() == null ? BigDecimal.ZERO : reportObj.getOilAmt());
        } else {
            webreport.setOilAmt(BigDecimal.ZERO);
        }
        map.put("report", webreport);
        return callback + "(" + gson.toJson(map) + ")";
    }

    @RequestMapping(value = "hykOilManager/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "加油管理修改新增")
    public String save(@RequestParam("callback") String callback, HttpServletRequest request,
                       HykOilManager hykOilManager) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("code", "200");
            map.put("msg", "加油管理保存请求成功");
            if ("1".equals(hykOilManager.getStatus())) {
                hykOilManager.setOilDate(new Date());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            Date plan = hykOilManager.getPlanOilDate();
            String planStr = sdf.format(plan);
            String nowStr = sdf.format(new Date());
            if (Integer.parseInt(nowStr) >= Integer.parseInt(planStr)) {//本月和本月之前的版本
                hykOilManagerService.updateStatus(hykOilManager);//更改加油计划状态
                //同时掉用第三方加油接口


            } else {
                map.put("code", "300");
                map.put("msg", "不是本月加油计划");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "加油管理保存请求失败");
        }
        return callback + "(" + gson.toJson(map) + ")";
    }

    @RequestMapping(value = "hykOilManager/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "加油管理删除")
    public String delete(@RequestParam("callback") String callback, HykOilManager hykOilManager) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除订单请求成功");
            hykOilManagerService.delete(hykOilManager);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除订单请求失败");
        }
        return callback + "(" + gson.toJson(map) + ")";
    }


    @RequestMapping(value = "hykOilManager/getId")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "加油管理ID检索")
    public String getId(@RequestParam("callback") String callback, HykOilManager hykOilManager) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("code", "200");
            map.put("msg", "红包信息请求成功");
            hykOilManager.setDelFlag(null);
            hykOilManager.setDelFlag(null);
            hykOilManager.setOilDate(null);
            hykOilManager.setPlanOilDate(null);
            hykOilManager.setCreateDate(null);
            hykOilManager.setOilDateStr(hykOilManager.getOilDateStr() == null ? "" : hykOilManager.getOilDateStr());
            map.put("hykOilManager", hykOilManager);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "红包信息请求失败");
        }
        return callback + "(" + gson.toJson(map) + ")";
    }


    /*  导出工具*/
    @RequestMapping(value = "hykOilManager/reportExcel")
    @ApiOperation(httpMethod = "GET", value = "加油管理导出")
    public String exportFile(HykOilManager hykOilManager, HttpServletRequest request, HttpServletResponse response,
                             RedirectAttributes redirectAttributes) {
        try {
            List<HykOilManager> list = hykOilManagerService.findList(hykOilManager);
            String fileName = DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            new ExportExcel("加油管理数据", HykOilManager.class).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出工作任务失败！失败信息：" + e.getMessage());
        }
        return null;
    }
}