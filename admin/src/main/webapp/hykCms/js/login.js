// JavaScript Document

layui.use(['jquery', 'jqform'], function() {
       var  form = layui.jqform;
		form.set({
			"blur": true,
			"form": "#loginForm"
		});
		form.verify({
			password: [
				/^[\S]{6,16}$/, '密码必须6到16位'
			]
		});
})
function success(res){
	layui.data('hykUser',{key:'token',value:res.token});
    layui.data('hykUser',{key:'userId',value:res.userId});
    layui.data('hykUser',{key:'userName',value:res.name});
	top.location.href="index.html";
}
