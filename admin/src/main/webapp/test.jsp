<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<style>
        .btn{
            height: 50px;
            font-size: 16px;
            width: 100%;
            margin-left: 30px;
        }
    button{
        width: 130px;
        height: 30px;
    }
</style>
<html>
<script src="<%=basePath%>hykCms/js/lib/jquery-2.2.0.min.js" type="text/javascript"></script>
<body>
<div style="width: 40%;float: left;">
<h2 style="width: 100%;">接口列表</h2>
<div style="float:left;width: 200px;">
    <div class="btn">1.0、<button  onclick="login()">登录</button></div>
    <div class="btn">1.2、<button  onclick="menuList()">菜单</button></div>
    <div class="btn">1.3、<button  onclick="dicList('oilManagerStatus')">下拉框</button></div>
    <div class="btn">1.4、<button  onclick="uploadImg()">上传图片demo</button></div>
    <div class="btn">1.5、<button  onclick="ckfinder()">富文本demo</button></div>
    <div class="btn">1.6、<button  onclick="reportIndex()">首页报表昨日数据</button></div>
    <div class="btn">1.7、<button  onclick="reportIndexTime()">首页报表选时数据</button></div>
    <hr>
    <div class="btn">22、<button  onclick="hykNews()">新闻管理</button></div>
    <div class="btn">22、<button  onclick="hykNewsSave()">新增修改</button></div>
    <div class="btn">22、<button  onclick="hykNewsGetId()">查看</button></div>
    <div class="btn">22、<button  onclick="hykNewsDelete()">删除</button></div>
    <hr>
    <div class="btn">23、<button  onclick="goodsList()">红包获取所有商品下拉框</button></div>
    <div class="btn">24、<button  onclick="orderStop()">订单暂停</button></div>
    <div class="btn">25、<button  onclick="orderStart()">订单开始</button></div>
    <div class="btn">26、<button  onclick="orderRefund()">订单退款</button></div>
    <div class="btn">27、<button  onclick="hykMallOrderReportExcel()">商城订单导出</button></div>
    <div class="btn">28、<button  onclick="hykMallOrderReportExcelSend()">订单发货地址导出</button></div>

    <hr>
    <div class="btn">21、<button  onclick="companyBackMoneyMgr()">商户管理</button></div>
    <div class="btn">21、<button  onclick="yzm()">验证码管理</button></div>
    <hr>
    <div class="btn">20、<button  onclick="hykMallOrderSend()">发货管理</button></div>
    <hr>
    <div class="btn">19.4、<button  onclick="hykMallGoods()">商城商品查询</button></div>
    <div class="btn">19.4、<button  onclick="hykMallOrder()">商城商品订单查询</button></div>
    <div class="btn">19.4、<button  onclick="hykCardHis()">兑换记录管理</button></div>
    <hr>
    <div class="btn">19.1、<button  onclick="hykRechargeCard()">卡密管理</button></div>
    <div class="btn">19.2、<button  onclick="hykRechargeCardSave()">新增卡密</button></div>
    <div class="btn">19.3、<button  onclick="hykRechargeCardReportExcel()">导出卡密</button></div>
    <div class="btn">19.4、<button  onclick="hykRechargeCardUpdateStatus()">激活卡密</button></div>
    <hr>
    <div class="btn">18.1、<button  onclick="hykMessage()">站内信管理</button></div>
    <div class="btn">18.1、<button  onclick="hykMessageSave()">站内信管理保存</button></div>
    <hr>
    <div class="btn">17.1、<button  onclick="hykCardHis()">充值卡兑换记录</button></div>
    <div class="btn">17.1、<button  onclick="hykCardHisSave()">充值卡兑换保存</button></div>
    <hr>
    <div class="btn">16.1、<button  onclick="hykAppVersion()">版本管理管理列表</button></div>
    <div class="btn">16.1、<button  onclick="hykAppVersionSave()">版本新增保存</button></div>
    <div class="btn">16.1、<button  onclick="hykAppVersionDelete()">版本删除</button></div>
    <div class="btn">16.1、<button  onclick="hykAppVersionGetId()">版本记录查询</button></div>
    <hr>

    <div class="btn">3.0、<button  onclick="hykUser()">用户管理列表</button></div>
    <div class="btn">3.1、<button  onclick="hykUserSave()">用户修改</button></div>
    <div class="btn">3.2、<button  onclick="hykUserGetId()">单条用户记录</button></div>
    <div class="btn">3.1、<button  onclick="excelall()">用户导出</button></div>
    <hr>
    <div class="btn">4.0、<button  onclick="hykOrder()">订单管理列表</button></div>
    <div class="btn">3.1、<button  onclick="hykOrderexcelall()">订单导出</button></div>
    <hr>
    <div class="btn">5.0、<button  onclick="hykOilManager()">加油管理列表</button></div>
    <div class="btn">5.1、<button  onclick="hykOilManagerGetByid()">加油管理列表</button></div>
    <div class="btn">5.2、<button  onclick="hykOilManagerSave()">加油管理列表</button></div>
    <div class="btn">3.1、<button  onclick="hykOilManagerExcelall()">加油管理导出</button></div>
    <hr>
    <div class="btn">6.0、<button  onclick="hykOilCard()">加油卡管理列表</button></div>
    <div class="btn">3.1、<button  onclick="hykOilCardExcelall()">加油卡导出</button></div>
    <hr>
    <div class="btn">7.0、<button  onclick="hykGoods(0)">充值套餐管理列表</button></div>
    <div class="btn">7.1、<button  onclick="hykGoods(1)">即时充值管理列表</button></div>
    <div class="btn">7.2、<button  onclick="hykGoodsSave()">商品修改</button></div>
    <div class="btn">7.3、<button  onclick="hykGoodsDelete()">商品删除</button></div>
    <div class="btn">7.4、<button  onclick="hykGoodsGetId()">根据id查询商品</button></div>
    <hr>
    <div class="btn">8.0、<button  onclick="hykAd()">广告管理列表</button></div>
    <div class="btn">8.1、<button  onclick="hykAdSave()">广告修改</button></div>
    <div class="btn">8.2、<button  onclick="hykAdDelete()">广告删除</button></div>
    <div class="btn">8.3、<button  onclick="hykAdGetId()">根据广告id查询</button></div>
    <hr>
    <div class="btn">9.0、<button  onclick="hykNotice()">公告管理列表</button></div>
    <div class="btn">9.1、<button  onclick="hykNoticeSave()">公告修改</button></div>
    <div class="btn">9.2、<button  onclick="hykNoticeDelete()">公告删除</button></div>
    <div class="btn">9.3、<button  onclick="hykNoticeGetId()">根据公告id查询</button></div>
    <hr>
    <div class="btn">10.0、<button  onclick="hykInvoice()">发票管理列表</button></div>
    <div class="btn">10.1、<button  onclick="hykInvoiceSave()">发票修改</button></div>
    <hr>
<%--    <div class="btn">9.1、<button  onclick="hykOilCardSave()">加油卡管理修改</button></div>
    <div class="btn">9.2、<button  onclick="hykOilCardDelete()">加油卡管理删除</button></div>--%>

    <div class="btn">11.0、<button  onclick="hykRedpackage()">红包管理列表</button></div>
    <div class="btn">11.1、<button  onclick="hykRedpackageSave()">红包管理修改</button></div>
    <div class="btn">11.2、<button  onclick="hykRedpackageDelete()">红包管理删除</button></div>
    <div class="btn">11.2、<button  onclick="hykRedpackageGetId()">根据id查询红包</button></div>
    <div class="btn">3.1、<button  onclick="hykRedpackageExcelall()">红包导出</button></div>
    <hr>
    <div class="btn">14.1、<button  onclick="hykInviter()">邀请人管理列表</button></div>

    <div class="btn">14.2、<button  onclick="hykInviterExcelall()">邀请人管理导出</button></div>
    <div class="btn">14.2、<button  onclick="backMoneyMgr()">返现管理列表</button></div>
    <div class="btn">14.3、<button  onclick="backMoneyMgr2()">返现管理列表2</button></div>
    <hr>
    <div class="btn">15.1、<button  onclick="hykAdvice()">意见反馈管理列表</button></div>
    <div class="btn">15.2、<button  onclick="hykAdviceSave()">意见反馈修改</button></div>
</div>

  <div style="float:left; margin-left: 20px;">
      <div class="btn">1、<button  onclick="hykCompanyBackmoneyHis()">资金流水</button></div>
      <div class="btn">2、<button  onclick="hykCompanyCashWithdrawal()">提现管理</button></div>
      <div class="btn">3、<button  onclick="hykCompanyCashWithdrawalSave()">提现审核</button></div>
      <div class="btn">4、<button  onclick="employeeBackMoneyMgr()">奖励明细</button></div>

      <div class="btn">5、<button  onclick="employeeMgr()">员工管理</button></div>
      <div class="btn">6、<button  onclick="companyBackMoneyMgr()">商户管理</button></div>

  </div>

</div>
<div style="width: 40%;float: left;">
    <h2>接口内容</h2>
    <div id="writePlace" ></div>
</div>

<script>


    setTimeout(function(){
        //window.location.href="http://www.baidu.com";
    }, 3000);

    //var path="http://47.99.115.136:8081/manager";
    var path="<%=basePath%>";
    //var path="http://192.168.0.114:8181/manager";
    var token;
    var userId;
    function login(){

        $.ajax({
            url : path+"/f/web/login",
            data:{loginName:"admin",password:"123456"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                token=data.token;
                userId=data.userId;
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function  hykMallOrderReportExcel(){
        window.location.href=path+"/f/web/hykMallOrder/reportExcel?token="+token;
    }
    function  hykMallOrderReportExcelSend(){
        window.location.href=path+"/f/web/hykMallOrder/reportExcelSend?token="+token;
    }

    function orderStop(){
        $.ajax({
            url : path+"/f/web/hykOrder/stop",
            data:{'token':token,id:"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function orderStart(){
        $.ajax({
            url : path+"/f/web/hykOrder/start",
            data:{'token':token,id:"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function orderRefund(){
        $.ajax({
            url : path+"/f/web/hykOrder/refund",
            data:{'token':token,id:"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function goodsList(){
        $.ajax({
            url : path+"/f/web/hykRedpackage/goodsList",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    var newsId="0";
    function hykNews(){
        $.ajax({
            url : path+"/f/web/hykNews",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                newsId=data.page.list[0].id;
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }
    function hykNewsSave(){
        $.ajax({
            url : path+"/f/web/hykNews/save",
            data:{'token':token,title:"新闻1",content:"123465",ico:"12346",url:"http://www.baidu.com",status:"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }
    function hykNewsGetId(){
        $.ajax({
            url : path+"/f/web/hykNews/getId",
            data:{'token':token,id:newsId},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }

    function hykNewsDelete(){
        $.ajax({
            url : path+"/f/web/hykNews/delete",
            data:{'token':token,id:newsId},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }

    function employeeMgr(){
        $.ajax({
            url : path+"/f/web/hykOrder/employeeMgr",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }

    function companyBackMoneyMgr(){
        $.ajax({
            url : path+"/f/web/hykOrder/companyBackMoneyMgr",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }
    function yzm(){
        $.ajax({
            url : path+"/f/web/hykYzm",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }

    function hykCompanyCashWithdrawalSave(){
        $.ajax({
            url : path+"/f/web/hykCompanyCashWithdrawal/save",
            data:{'token':token,id:"89468a6df72847c9bd9e3fa7795f93dd",status:"2",remarks:"123"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }

    function employeeBackMoneyMgr(){
        $.ajax({
            url : path+"/f/web/hykOrder/employeeBackMoneyMgr",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }

    function hykCompanyCashWithdrawal(){
        $.ajax({
            url : path+"/f/web/hykCompanyCashWithdrawal",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }


    function hykCompanyBackmoneyHis(){
        $.ajax({
            url : path+"/f/web/hykCompanyBackmoneyHis",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }

    function companyBackMoneyMgr(){
        $.ajax({
            url : path+"/f/web/hykOrder/companyBackMoneyMgr",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }

    function hykMallOrderSend(){

        $.ajax({
            url : path+"/f/web/hykMallOrderSend",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }


    function hykCardHis(){
        $.ajax({
            url : path+"/f/web/hykCardHis",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }


    function hykMallOrder(){
        $.ajax({
            url : path+"/f/web/hykMallOrder",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }
    function hykMallGoods(){
        $.ajax({
            url : path+"/f/web/hykMallGoods",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });

    }

    function hykRechargeCard(){

        $.ajax({
            url : path+"/f/web/hykRechargeCard",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykRechargeCardSave(){

        $.ajax({
            url : path+"/f/web/hykRechargeCard/createCard",
            data:{'token':token,code:"1",money:500,useMethod:"0",cardNum:10},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function  hykRechargeCardReportExcel(){
        window.location.href=path+"/f/web/hykRechargeCard/reportExcel?token="+token;
    }

    function hykRechargeCardUpdateStatus(){

        $.ajax({
            url : path+"/f/web/hykRechargeCard/updateStatus",
            data:{'token':token,code:"1",overDate:'2018-12-30'},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykMessage(){

        $.ajax({
            url : path+"/f/web/hykMessage",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }



    function hykMessageSave(){

        $.ajax({
            url : path+"/f/web/hykMessage/save",
            data:{'token':token,title:"123",content:"123",phones:"17730209007"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykCardHis(){

        $.ajax({
            url : path+"/f/web/hykCardHis",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function hykCardHisSave(){
        $.ajax({
            url : path+"/f/web/hykCardHis/save",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykCardHisGetId(){

        $.ajax({
            url : path+"/f/web/hykCardHis/getId",
            data:{'token':token,'id':"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykCardHisDelete(){
        $.ajax({
            url : path+"/f/web/hykCardHis/delete",
            data:{'token':token,'id':"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    //
    function hykAppVersion(){

        $.ajax({
            url : path+"/f/web/hykAppVersion",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function hykAppVersionSave(){
        $.ajax({
            url : path+"/f/web/hykAppVersion/save",
            data:{'token':token,code:"1.0",content:"发布内容"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykAppVersionGetId(){

        $.ajax({
            url : path+"/f/web/hykAppVersion/getId",
            data:{'token':token,'id':"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykAppVersionDelete(){
        $.ajax({
            url : path+"/f/web/hykAppVersion/delete",
            data:{'token':token,'id':"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function ckfinder(){
        window.location.href="./ckeditor/samples/index.html"
    }


    function  uploadImg(){
        window.location.href="./upload/index.html"
    }

    function  excelall(){
        window.location.href=path+"/f/web/hykUser/reportExcel?token="+token;
    }

    function  hykOrderexcelall(){
        window.location.href=path+"/f/web/hykOrder/reportExcel?token="+token;
    }
    function  hykOilManagerExcelall(){
        window.location.href=path+"/f/web/hykOilManager/reportExcel?token="+token;
    }
    function  hykOilCardExcelall(){
        window.location.href=path+"/f/web/hykOilCard/reportExcel?token="+token;
    }
    function  hykRedpackageExcelall(){
        window.location.href=path+"/f/web/hykRedpackage/reportExcel?token="+token;
    }
    function hykInviterExcelall(){
        window.location.href=path+"/f/web/hykOrder/hykInviterExcel?token="+token;
    }


    function menuList(){

        $.ajax({
            url : path+"/f/web/menuList",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function dicList(type){

        $.ajax({
            url : path+"/f/web/dicList",
            data:{'token':token,'type':type},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function reportIndex(){

        $.ajax({
            url : path+"/f/web/reportIndex",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function reportIndexTime(){

        $.ajax({
            url : path+"/f/web/reportIndexTime",
            data:{'token':token,startTime:'2018-11-01',endTime:'2018-11-30'},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }


    function hykUser(){

        $.ajax({
            url : path+"/f/web/hykUser",
            data:{'token':token,channel:"",minPayNum:1,maxPayNum:6,userOther:"","inviterOther":""},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykUserSave(){

        $.ajax({
            url : path+"/f/web/hykUser/save",
            data:{'token':token,'id':"1",'isCompany':"1",'phone':"1875645636",'password':"123456",realName:"王小二"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function hykUserSaveGetId(){

        $.ajax({
            url : path+"/f/web/hykUser/getId",
            data:{'token':token,'id':"1"},
            type: "post",
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykGoods(type){

        $.ajax({
            url : path+"/f/web/hykGoods",
            data:{'token':token,"goodsType":type},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykGoodsSave(){

        $.ajax({
            url : path+"/f/web/hykGoods/save",
            data:{'token':token,
               "goodsType":"1",
                "goodsName":"测试新增功能",
                "discount":"7.7",
                "cycle":"3",
                "stock":"1000",
                "activityDiscount":"7.5",
                "label":"1",
                "id":"ecd03b46e47942a78d09e24bbb5cdd84",
                "prices":"100",
                "val":"90",
                "goodsImg":"http://www.baidu.com"
                },
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykGoodsDelete(){

        $.ajax({
            url : path+"/f/web/hykGoods/delete",
            data:{'token':token,'id':"ecd03b46e47942a78d09e24bbb5cdd84"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function hykGoodsGetId(){

        $.ajax({
            url : path+"/f/web/hykGoods/getId",
            data:{'token':token,'id':"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykOrder(){
//startTime:'2018-11-01',endTime:'2018-11-15',other:"17730209007",orderType:'1',orderStatus:'0',payType:'1'
        $.ajax({
            url : path+"/f/web/hykOrder",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykOrderSave(){

        $.ajax({
            url : path+"/f/web/hykOrder/save",
            data:{'token':token,'id':"dd22a13a0d0a4c2f975c08d2ab1f75ac"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykOrderDelete(){

        $.ajax({
            url : path+"/f/web/hykOrder/delete",
            data:{'token':token,'id':"dd22a13a0d0a4c2f975c08d2ab1f75ac"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykOilManager(){
        //startTime:'2018-11-01',endTime:'2018-11-15','hykOrder.orderType':"1",'status':"1",'other':'17730209007'
        $.ajax({
            url : path+"/f/web/hykOilManager",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykOilManagerSave(){

        $.ajax({
            url : path+"/f/web/hykOilManager/save",
            data:{'token':token,'id':"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykOilManagerDelete(){

        $.ajax({
            url : path+"/f/web/hykOilManager/delete",
            data:{'token':token,'id':"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykOilManagerGetByid(){

        $.ajax({
            url : path+"/f/web/hykOilManager/getId",
            data:{'token':token,'id':"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykOilCard(){

        $.ajax({
            url : path+"/f/web/hykOilCard",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykOilCardSave(){

        $.ajax({
            url : path+"/f/web/hykOilCard/save",
            data:{'token':token,'id':"dd22a13a0d0a4c2f975c08d2ab1f75ac","status":"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykOilCardDelete(){

        $.ajax({
            url : path+"/f/web/hykOilCard/delete",
            data:{'token':token,'id':"dd22a13a0d0a4c2f975c08d2ab1f75ac"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykAd(){

        $.ajax({
            url : path+"/f/web/hykAd",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykAdSave(){

        $.ajax({
            url : path+"/f/web/hykAd/save",
            data:{'token':token,
                'id':"",
                "title":"活动名称",
                "bannerImg":"http://www.baidu.com",
                "openImg":"http://www.baidu.com",
                "wondefulImg":"http://www.baidu.com",
                "appImg":"http://www.baidu.com",
                "shareImg":"http://www.baidu.com",
                "status":"1",
                "startTime":"2018-11-16",
                "endTime":"2018-11-20",
                "url":"",
            },
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykAdDelete(){

        $.ajax({
            url : path+"/f/web/hykAd/delete",
            data:{'token':token,'id':"asd"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykAdGetId(){
        $.ajax({
            url : path+"/f/web/hykAd/getId",
            data:{'token':token,'id':"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykNotice(){

        $.ajax({
            url : path+"/f/web/hykNotice",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykNoticeSave(){

        $.ajax({
            url : path+"/f/web/hykNotice/save",
            data:{'token':token,title:"xxx",content:"yyy",startTime:'2018-11-01',endTime:'2018-11-15',status:"0"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykNoticeDelete(){

        $.ajax({
            url : path+"/f/web/hykNotice/delete",
            data:{'token':token,'id':"dd22a13a0d0a4c2f975c08d2ab1f75ac"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function hykNoticeGetId(){
        $.ajax({
            url : path+"/f/web/hykNotice/getId",
            data:{'token':token,'id':"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function hykInvoice(){

        $.ajax({
            url : path+"/f/web/hykInvoice",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykInvoiceSave(){

        $.ajax({
            url : path+"/f/web/hykInvoice/save",
            data:{'token':token,'id':"1","status":"0"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }




    function hykRedpackage(){

        $.ajax({
            url : path+"/f/web/hykRedpackage",
            data:{'token':token,"other":"17730209007"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykRedpackageSave(){

        $.ajax({
            url : path+"/f/web/hykRedpackage/save",
            data:{'token':token,"title":"测试红包新增功能","money":"10","userId":"1","dayNum":10,"redType":"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykRedpackageDelete(){

        $.ajax({
            url : path+"/f/web/hykRedpackage/delete",
            data:{'token':token,'id':"f445cfb06fa449e5b5e449606cb04b52"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function hykRedpackageGetId(){

        $.ajax({
            url : path+"/f/web/hykRedpackage/getId",
            data:{'token':token,'id':"1"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }



    function hykUserRedpackage(){

        $.ajax({
            url : path+"/f/web/hykUserRedpackage",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykUserRedpackageSave(){

        $.ajax({
            url : path+"/f/web/hykUserRedpackage/save",
            data:{'token':token,'id':"dd22a13a0d0a4c2f975c08d2ab1f75ac"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykUserRedpackageDelete(){

        $.ajax({
            url : path+"/f/web/hykUserRedpackage/delete",
            data:{'token':token,'id':"dd22a13a0d0a4c2f975c08d2ab1f75ac"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykInviter(){

        $.ajax({
            url : path+"/f/web/hykOrder/hykInviter",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function backMoneyMgr(){

        $.ajax({
            url : path+"/f/web/hykOrder/backMoneyMgr",
            data:{'token':token,'other':1},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function backMoneyMgr2(){

        $.ajax({
            url : path+"/f/web/hykOrder/getMonthBackAmt2",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }

    function hykAdvice(){

        $.ajax({
            url : path+"/f/web/hykAdvice",
            data:{'token':token},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function hykAdviceSave(){
        $.ajax({
            url : path+"/f/web/hykAdvice/save",
            data:{'token':token,'id':1,status:"1",answer:"answer"},
            type: "post",
            dataType:"jsonp",  //数据格式设置为jsonp
            jsonp:"callback",  //Jquery生成验证参数的名称
            success :function(data){
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
</script>

<script>
    //格式化代码函数,已经用原生方式写好了不需要改动,直接引用就好
    var formatJson = function (json, options) {
        var reg = null,
                formatted = '',
                pad = 0,
                PADDING = '    ';
        options = options || {};
        options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
        options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;
        if (typeof json !== 'string') {
            json = JSON.stringify(json);
        } else {
            json = JSON.parse(json);
            json = JSON.stringify(json);
        }
        reg = /([\{\}])/g;
        json = json.replace(reg, '\r\n$1\r\n');
        reg = /([\[\]])/g;
        json = json.replace(reg, '\r\n$1\r\n');
        reg = /(\,)/g;
        json = json.replace(reg, '$1\r\n');
        reg = /(\r\n\r\n)/g;
        json = json.replace(reg, '\r\n');
        reg = /\r\n\,/g;
        json = json.replace(reg, ',');
        if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
            reg = /\:\r\n\{/g;
            json = json.replace(reg, ':{');
            reg = /\:\r\n\[/g;
            json = json.replace(reg, ':[');
        }
        if (options.spaceAfterColon) {
            reg = /\:/g;
            json = json.replace(reg, ':');
        }
        (json.split('\r\n')).forEach(function (node, index) {
                    var i = 0,
                            indent = 0,
                            padding = '';

                    if (node.match(/\{$/) || node.match(/\[$/)) {
                        indent = 1;
                    } else if (node.match(/\}/) || node.match(/\]/)) {
                        if (pad !== 0) {
                            pad -= 1;
                        }
                    } else {
                        indent = 0;
                    }

                    for (i = 0; i < pad; i++) {
                        padding += PADDING;
                    }

                    formatted += padding + node + '\r\n';
                    pad += indent;
                }
        );
        return formatted;
    };
    //引用示例部分
    //(1)创建json格式或者从后台拿到对应的json格式
   // var originalJson = {"name": "binginsist", "sex": "男", "age": "25"};
    //(2)调用formatJson函数,将json格式进行格式化
   // var resultJson = formatJson(originalJson);
    //(3)将格式化好后的json写入页面中
   //document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
</script>

</body>
</html>
