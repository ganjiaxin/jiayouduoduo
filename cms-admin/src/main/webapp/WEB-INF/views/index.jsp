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
<script src="<%=basePath%>/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<body>
<div style="width: 40%;float: left;">
    <h2>接口列表</h2>
    <div class="btn">1、<button  onclick="register()">注册</button></div>
    <div class="btn">2、<button  onclick="login()">登录</button></div>
    <div class="btn">3、<button  onclick="index()">菜单</button></div>
</div>
<div style="width: 40%;float: left;">
    <h2>接口内容</h2>
    <div id="writePlace" ></div>
</div>

<script>
    var token;

    function register(){
        $.ajax({
            url : "http://localhost:8181/manager/f/app/register",
            data:{phone:"13588356007",password:"123456"},
            type: "post",
            success :function(data){
                token=data.token;
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }
    function login(){

        $.ajax({
            url : "http://localhost:8181/manager/f/app/login",
            data:{phone:"13588356007",password:"123456"},
            type: "post",
            dataType:"json",
            success :function(data){
                token=data.token;
                var resultJson = formatJson(data);
                document.getElementById("writePlace").innerHTML = '<pre>' +resultJson + '<pre/>';
            }
        });
    }


    function index(){

        $.ajax({
            url : "http://localhost:8181/manager/f/app/index",
            data:{'token':token},
            type: "post",
            dataType:"json",
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
