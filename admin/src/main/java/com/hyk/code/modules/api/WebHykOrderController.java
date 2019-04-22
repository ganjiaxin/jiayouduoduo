package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.utils.excel.ExportExcel;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykInviter;
import com.hyk.code.modules.hyk.entity.HykOrder;
import com.hyk.code.modules.hyk.entity.ReportIndex;
import com.hyk.code.modules.hyk.entity.vo.CompanyMarVo;
import com.hyk.code.modules.hyk.entity.vo.HykOrderDetailVo;
import com.hyk.code.modules.hyk.entity.vo.ReportVo;
import com.hyk.code.modules.hyk.service.HykMonthReportService;
import com.hyk.code.modules.hyk.service.HykOrderService;
import com.hyk.code.modules.hyk.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单管理Controller
 * @author 霍中曦
 * @version 2018-11-09
 */
@Api(description = "订单管理")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykOrderController extends BaseController {

    @Autowired
    private HykOrderService hykOrderService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private HykMonthReportService hykMonthReportService;

    @ModelAttribute
    public HykOrder get(@RequestParam(required = false) String id) {
        HykOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hykOrderService.get(id);
        }
        if (entity == null) {
            entity = new HykOrder();
        }
        return entity;
    }

    @RequestMapping(value ={ "hykOrder","userHykOrder"})
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "订单管理查询")
    public String list(@RequestParam("callback") String callback,HykOrder hykOrder, HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","订单页面请求成功");
        map.put("permission",request.getAttribute("permission"));
        try {
            Page<HykOrder> page = hykOrderService.findPage(new Page<HykOrder>(request, response), hykOrder);
            for (int i = 0; i < page.getList().size(); i++) {
                HykOrder old = page.getList().get(i);
                old.setDelFlag(null);
                old.setCardId(null);
                old.setPayDate(null);
                old.setCreateDate(null);
                old.setCardNo(StringUtils.isBlank(old.getCardNo())?"未绑定加油卡":old.getCardNo());
                old.setBalancePayment(old.getBalancePayment()!=null?old.getBalancePayment():BigDecimal.ZERO);
            }
            map.put("page", page);

            ReportIndex report = new ReportIndex();
            report.setStartTime(hykOrder.getStartTime());
            report.setEndTime(hykOrder.getEndTime());
            ReportIndex reportObj = reportService.getYesterDayFindList(report);

            ReportIndex webreport = new ReportIndex();
            if (reportObj != null) {
                webreport.setOrderNum(reportObj.getOrderNum() == null ? 0 : reportObj.getOrderNum());
                webreport.setTotalAmt(reportObj.getTotalAmt() == null ? BigDecimal.ZERO : reportObj.getTotalAmt());
            } else {
                webreport.setOrderNum(0);
                webreport.setTotalAmt(BigDecimal.ZERO);
            }
            map.put("report", webreport);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code","400");
            map.put("msg","订单页面请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykOrder/save")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "订单管理修改新增")
    public String save(@RequestParam("callback") String callback,HykOrder hykOrder) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "订单保存请求成功");
            hykOrderService.save(hykOrder);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "订单保存请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value = "hykOrder/delete")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "订单管理删除")
    public String delete(@RequestParam("callback") String callback,HykOrder hykOrder) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "删除订单请求成功");
            hykOrderService.delete(hykOrder);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "删除订单请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }
/**
 * 功能描述:商户管理
 * @auther: 霍中曦
 * @param:
 * @return:
 * @date: 2019/1/19 15:53
 */
    @RequestMapping(value = "hykOrder/companyBackMoneyMgr")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "商户管理")
    public String companyBackMoneyMgr(@RequestParam("callback") String callback,String other, HttpServletRequest request, HttpServletResponse response) {

        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();

        map.put("code","200");
        map.put("msg","商户管理请求成功");
        map.put("permission",request.getAttribute("permission"));

        CompanyMarVo companyMarVo=new CompanyMarVo();
        companyMarVo.setOther(other);

        //上月充值金额
        BigDecimal lastMonthRechargeAmount = reportService.countRechargeAmountByLastMonth(companyMarVo);
        map.put("lastMonthRechargeAmount",lastMonthRechargeAmount!=null?lastMonthRechargeAmount:BigDecimal.ZERO);

        //上月返现金额
        BigDecimal lastMonthBackMoney = reportService.countBackMoneyByLastMonth(companyMarVo);
        map.put("lastMonthBackMoney",lastMonthBackMoney!=null?lastMonthBackMoney:BigDecimal.ZERO);

        //本月充值金额
        BigDecimal currentMonthRechargeAmount = reportService.countRechargeAmountByCurrentMonth(companyMarVo);
        map.put("currentMonthRechargeAmount",currentMonthRechargeAmount!=null?currentMonthRechargeAmount:BigDecimal.ZERO);

        //本月返现金额
        BigDecimal currentMonthBackMoney = reportService.countBackMoneyByCurrentMonth(companyMarVo);
        map.put("currentMonthBackMoney",currentMonthBackMoney!=null?currentMonthBackMoney:BigDecimal.ZERO);


        //所有公司当月充值金额
        List<ReportVo> currentMonthOrderAmtList = hykMonthReportService.countCurrentMonthOrderAmt();
        //所有公司当月返现金额
        List<ReportVo> currentMonthBackMoneyList = hykMonthReportService.countCurrentMonthBackMoney();
        //所有公司累计邀请人数
        List<ReportVo> currentMonthInviterNumList = hykMonthReportService.countCurrentMonthInviterNum();

        Page<CompanyMarVo> page = hykOrderService.findcompanyBackMoneyPage(new Page<CompanyMarVo>(request, response), companyMarVo);

        for (CompanyMarVo obj:page.getList()){

            obj.setOrderNum(StringUtils.isBlank(obj.getOrderNum())?"0":obj.getOrderNum());
            obj.setOrderAmt(StringUtils.isBlank(obj.getOrderAmt())?"0":obj.getOrderAmt());
            obj.setTotalBackMoney(StringUtils.isBlank(obj.getTotalBackMoney())?"0":obj.getTotalBackMoney());
            obj.setLastMonthOrderAmt(StringUtils.isBlank(obj.getLastMonthOrderAmt())?"0":obj.getLastMonthOrderAmt());
            obj.setLastMonthBackMoney(StringUtils.isBlank(obj.getLastMonthBackMoney())?"0":obj.getLastMonthBackMoney());

            obj.setCurrentMonthOrderAmt(StringUtils.isBlank(obj.getCurrentMonthOrderAmt())?"0":obj.getCurrentMonthOrderAmt());
            obj.setCurrentMonthBackMoney(StringUtils.isBlank(obj.getCurrentMonthBackMoney())?"0":obj.getCurrentMonthBackMoney());
            obj.setTotalInviterNum(StringUtils.isBlank(obj.getTotalInviterNum())?"0":obj.getTotalInviterNum());

            for(ReportVo obj1:currentMonthOrderAmtList){
                if(obj.getCompanyId().equals(obj1.getCompanyId())){
                    obj.setCurrentMonthOrderAmt(obj1.getMoney().toString());
                }
            }
            for(ReportVo obj1:currentMonthBackMoneyList){
                if(obj.getCompanyId().equals(obj1.getCompanyId())){
                    obj.setCurrentMonthBackMoney(obj1.getBackMoney().toString());
                }
            }
            for(ReportVo obj1:currentMonthInviterNumList){
                if(obj.getCompanyId().equals(obj1.getCompanyId())){
                    obj.setTotalInviterNum(obj1.getInviterNum());
                }
            }
        }
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }


    /**
     * 功能描述:员工管理
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/12/11 15:52
     */
    @RequestMapping(value = "hykOrder/employeeMgr")
    @ResponseBody
    @ApiOperation(httpMethod = "  ", value = "员工管理")
    public String employeeMgr(@RequestParam("callback") String callback,String other, HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","员工管理请求成功");
        map.put("permission",request.getAttribute("permission"));

        CompanyMarVo companyMarVo=new CompanyMarVo();
        companyMarVo.setOther(other);

        //上月充值金额
        BigDecimal lastMonthRechargeAmount = reportService.countRechargeAmountByLastMonth(companyMarVo);
        map.put("lastMonthRechargeAmount",lastMonthRechargeAmount!=null?lastMonthRechargeAmount:BigDecimal.ZERO);

        //上月返现金额
        BigDecimal lastMonthBackMoney = reportService.countBackMoneyByLastMonth(companyMarVo);
        map.put("lastMonthBackMoney",lastMonthBackMoney!=null?lastMonthBackMoney:BigDecimal.ZERO);

        //本月充值金额
        BigDecimal currentMonthRechargeAmount = reportService.countRechargeAmountByCurrentMonth(companyMarVo);
        map.put("currentMonthRechargeAmount",currentMonthRechargeAmount!=null?currentMonthRechargeAmount:BigDecimal.ZERO);

        //本月返现金额
        BigDecimal currentMonthBackMoney = reportService.countBackMoneyByCurrentMonth(companyMarVo);
        map.put("currentMonthBackMoney",currentMonthBackMoney!=null?currentMonthBackMoney:BigDecimal.ZERO);


        //所有员工当月充值金额
        List<ReportVo> currentMonthOrderAmtList = hykMonthReportService.countCurrentMonthOrderAmtEmployee();
        //所有员工当月返现金额
        List<ReportVo> currentMonthBackMoneyList = hykMonthReportService.countCurrentMonthBackMoneyEmployee();
        //所有员工累计邀请人数
        List<ReportVo> currentMonthInviterNumList = hykMonthReportService.countCurrentMonthInviterNumEmployee();

        Page<CompanyMarVo> page = hykOrderService.findMonthReportPage(new Page<CompanyMarVo>(request, response), companyMarVo);

        for (CompanyMarVo obj:page.getList()){

            obj.setOrderNum(StringUtils.isBlank(obj.getOrderNum())?"0":obj.getOrderNum());
            obj.setOrderAmt(StringUtils.isBlank(obj.getOrderAmt())?"0":obj.getOrderAmt());
            obj.setTotalBackMoney(StringUtils.isBlank(obj.getTotalBackMoney())?"0":obj.getTotalBackMoney());
            obj.setLastMonthOrderAmt(StringUtils.isBlank(obj.getLastMonthOrderAmt())?"0":obj.getLastMonthOrderAmt());
            obj.setLastMonthBackMoney(StringUtils.isBlank(obj.getLastMonthBackMoney())?"0":obj.getLastMonthBackMoney());

            obj.setCurrentMonthOrderAmt(StringUtils.isBlank(obj.getCurrentMonthOrderAmt())?"0":obj.getCurrentMonthOrderAmt());
            obj.setCurrentMonthBackMoney(StringUtils.isBlank(obj.getCurrentMonthBackMoney())?"0":obj.getCurrentMonthBackMoney());
            obj.setTotalInviterNum(StringUtils.isBlank(obj.getTotalInviterNum())?"0":obj.getTotalInviterNum());

            for(ReportVo obj1:currentMonthOrderAmtList){
                if(obj.getInviterCode().equals(obj1.getEmployeeId())){
                    obj.setCurrentMonthOrderAmt(obj1.getMoney().toString());
                }
            }
            for(ReportVo obj1:currentMonthBackMoneyList){
                if(obj.getInviterCode().equals(obj1.getEmployeeId())){
                    obj.setCurrentMonthBackMoney(obj1.getBackMoney().toString());
                }
            }
            for(ReportVo obj1:currentMonthInviterNumList){
                if(obj.getInviterCode().equals(obj1.getEmployeeId())){
                    obj.setTotalInviterNum(obj1.getInviterNum());
                }
            }
        }
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }

    /**
     * 功能描述:奖励明细
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/12/11 15:52
     */
    @RequestMapping(value = "hykOrder/employeeBackMoneyMgr")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "奖励明细")
    public String employeeBackMoneyMgr(@RequestParam("callback") String callback,String other, HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","奖励明细请求成功");
        map.put("permission",request.getAttribute("permission"));

        CompanyMarVo companyMarVo=new CompanyMarVo();
        companyMarVo.setOther(other);

        //邀请用户截至上月累计充值金额
        BigDecimal totalMonthRechargeAmount = reportService.countRechargeAmountByLastMonthAll(companyMarVo);
        map.put("totalMonthRechargeAmount",totalMonthRechargeAmount!=null?totalMonthRechargeAmount:BigDecimal.ZERO);

        //邀请用户上月充值金额
        BigDecimal lastMonthRechargeAmount = reportService.countRechargeAmountByLastMonth(companyMarVo);
        map.put("lastMonthRechargeAmount",lastMonthRechargeAmount!=null?lastMonthRechargeAmount:BigDecimal.ZERO);

        //邀请用户本月充值金额
        BigDecimal currentMonthRechargeAmount = reportService.countRechargeAmountByCurrentMonth(companyMarVo);
        map.put("currentMonthRechargeAmount",currentMonthRechargeAmount!=null?currentMonthRechargeAmount:BigDecimal.ZERO);


        //邀请用户截止上月累计交易返现金额
        BigDecimal totalMonthBackMoney = reportService.countBackMoneyByLastMonthAll(companyMarVo);
        map.put("totalMonthBackMoney",totalMonthBackMoney!=null?totalMonthBackMoney:BigDecimal.ZERO);

        //邀请用户上月返现金额
        BigDecimal lastMonthBackMoney = reportService.countBackMoneyByLastMonth(companyMarVo);
        map.put("lastMonthBackMoney",lastMonthBackMoney!=null?lastMonthBackMoney:BigDecimal.ZERO);

        //邀请用户本月返现金额
        BigDecimal currentMonthBackMoney = reportService.countBackMoneyByCurrentMonth(companyMarVo);
        map.put("currentMonthBackMoney",currentMonthBackMoney!=null?currentMonthBackMoney:BigDecimal.ZERO);

        HykOrderDetailVo hykOrderDetailVo=new HykOrderDetailVo();
        hykOrderDetailVo.setOther(other);

        Page<HykOrderDetailVo> page = hykOrderService.findOrderBackMoneyDetailPage(new Page<HykOrderDetailVo>(request, response), hykOrderDetailVo);
        for (HykOrderDetailVo od:page.getList()){
            String backMoney=od.getBackMoney();
            BigDecimal money=new BigDecimal(backMoney);
            BigDecimal amt = money.setScale(2,BigDecimal.ROUND_HALF_UP);
            od.setBackMoney(amt.toString());
        }
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }


    /*  导出工具*/
    @RequestMapping(value = "hykOrder/reportExcel")
    @ApiOperation(httpMethod = "GET", value = "订单管理数据")
    public String exportFile(HykOrder hykOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            List<HykOrder>  list = hykOrderService.findList( hykOrder);
            String fileName = DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            for (HykOrder obj:list){
                    if(obj.getPayableMoney()!=null){
                        obj.setPayableMoney(obj.getPayableMoney().add(obj.getBalancePayment()));
                    }else{
                        obj.setPayableMoney(obj.getBalancePayment());
                    }

            }
            new ExportExcel("订单管理数据", HykOrder.class).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出工作任务失败！失败信息："+e.getMessage());
        }
        return null;
    }

    /*  导出工具*/
    @RequestMapping(value = "hykOrder/hykInviterExcel",method = RequestMethod.GET)
    public String hykInviter(HykOrder hykOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            List<HykInviter> list = hykOrderService.findInviterExcelList(hykOrder);
            for(int i=0;i<list.size();i++){
                HykInviter old=list.get(i);
                if("套餐充值".equals(old.getOrderType())){
                    BigDecimal backMoney=BigDecimal.ZERO;
                    if(old.getRedpackageAmt()==null){
                        old.setRedpackageAmt(BigDecimal.ZERO);
                    }
                    if("3".equals(old.getCycle())){
                        backMoney=(old.getPayableMoney().add(old.getRedpackageAmt())).multiply(new BigDecimal("2"));
                    }else if("6".equals(old.getCycle())){
                        backMoney=(old.getPayableMoney().add(old.getRedpackageAmt())).multiply(new BigDecimal("3"));
                    }else if("12".equals(old.getCycle())){
                        backMoney=(old.getPayableMoney().add(old.getRedpackageAmt())).multiply(new BigDecimal("4"));
                    }else if("24".equals(old.getCycle())){
                        backMoney=(old.getPayableMoney().add(old.getRedpackageAmt())).multiply(new BigDecimal("10"));
                    }
                    old.setBackMoney(backMoney.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
                }else{
                    old.setBackMoney(BigDecimal.ZERO);
                }
                old.setDelFlag(null);
                old.setCardId(null);
                old.setPayDate(null);
            }

            String fileName = DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel("订单管理数据", HykInviter.class).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出工作任务失败！失败信息："+e.getMessage());
        }
        return null;
    }


    /**
     * 功能描述: 订单的暂停操作
     * @auther: 霍中曦
     * @date: 2019/3/11 9:20
     */
    @RequestMapping(value = "hykOrder/stop")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "订单管理暂停")
    public synchronized String stop(@RequestParam("callback") String callback,@RequestParam("id")String id) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "订单管理暂停成功");
            HykOrder hykOrder = hykOrderService.get(id);

            if(hykOrder==null){
                map.put("code", "400");
                map.put("msg", "订单不存在");
                return  callback+"("+gson.toJson(map)+")";
            }

            if(!"2".equals(hykOrder.getOrderStatus())){
                map.put("code", "400");
                map.put("msg", "订单状态不匹配");
                return  callback+"("+gson.toJson(map)+")";
            }
            hykOrder.setId(id);
            hykOrder.setOrderStatus("5");
            hykOrderService.updateStop(hykOrder);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "订单管理暂停失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }


    /**
     * 功能描述: 订单的恢复操作
     * @auther: 霍中曦
     * @date: 2019/3/11 9:20
     */
    @RequestMapping(value = "hykOrder/start")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "订单管理恢复")
    public synchronized String start(@RequestParam("callback") String callback,@RequestParam("id")String id) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "订单管理恢复成功");

            HykOrder hykOrder = hykOrderService.get(id);

            if(hykOrder==null){
                map.put("code", "400");
                map.put("msg", "订单不存在");
                return  callback+"("+gson.toJson(map)+")";
            }

            if(!"5".equals(hykOrder.getOrderStatus())){
                map.put("code", "400");
                map.put("msg", "订单状态不匹配");
                return  callback+"("+gson.toJson(map)+")";
            }

            hykOrder.setId(id);
            hykOrder.setOrderStatus("2");
            hykOrderService.updateStart(hykOrder);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "订单管理恢复失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }

    /**
     * 功能描述: 订单的退款操作
     * @auther: 霍中曦
     * @date: 2019/3/11 9:20
     */
    @RequestMapping(value = "hykOrder/refund")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "订单的退款操作")
    public synchronized String refund(@RequestParam("callback") String callback,@RequestParam("id")String id) {
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try {
            map.put("code", "200");
            map.put("msg", "订单的退款操作成功");

            HykOrder hykOrder = hykOrderService.get(id);

            if(hykOrder==null){
                map.put("code", "400");
                map.put("msg", "订单不存在");
                return  callback+"("+gson.toJson(map)+")";
            }

            if(!"2".equals(hykOrder.getOrderStatus())){
                map.put("code", "400");
                map.put("msg", "订单状态不匹配");
                return  callback+"("+gson.toJson(map)+")";
            }

            hykOrder.setId(id);
            hykOrder.setOrderStatus("6");
            hykOrderService.updateRefund(hykOrder);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "订单的退款操作失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }
}