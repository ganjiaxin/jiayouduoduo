// JavaScript Document

layui.config({
    version: true
    , debug: false
    , base: 'js/'
}).extend({
    index:'index',
    ajax: 'jqmodules/ajax',
    dtable: 'jqmodules/dtable',
    jqform: 'jqmodules/jqform',
    jqdate: 'jqmodules/jqdate',
    modal: 'jqmodules/modal'
})

var token =	layui.data('hykUser').token;
if(token===undefined || token.typeOf === 'undefined'){
    token=''
}

var url =window.location.href;
var rootUrl =url.substring(0,url.indexOf("hykCms")-1); //通用
var imgUrl ='http://47.99.115.136:8082/hykweb/f/app/download/image?filePath=' //测试库
// var imgUrl ='http://47.110.60.57:8082/hykweb/f/app/download/image?filePath=' //预发布正式库
//var imgUrl ='https://www.huiucard.com/hykweb/f/app/download/image?filePath=' //正式库
//var rootUrl = 'http://47.99.115.136:8081/hyk-admin' //本地测试



