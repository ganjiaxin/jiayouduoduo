layui.define(['form', 'layer'], function (exports) {
    "use strict";
    var $ = layui.jquery,
        device = layui.device(),
        forms = layui.form(),
        layer = layui.layer,
        MOD_NAME = 'jqform',
        ELEM = '.layui-form',
        THIS = 'layui-this',
        Jqform = function () {
            this.config = {
                verify: {
                    required: [
                        /[\S]+/, '必填项不能为空'
                    ],
                    phone: [
                        /^1\d{10}$/, '请输入正确的手机号'
                    ],
                    email: [
                        /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/, '邮箱格式不正确'
                    ],
                    url: [
                        /(^#)|(^http(s*):\/\/[^\s]+\.[^\s]+)/, '链接格式不正确'
                    ],
                    number: [
                        /^\d+$/, '只能填写数字'
                    ],
                    money: [
                        /^[0-9]+\.?[0-9]*/, '请输入金额'
                    ],
                    date: [
                        /^(\d{4})[-\/](\d{1}|0\d{1}|1[0-2])([-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/, '日期格式不正确'
                    ],
                    identity: [
                        /(^\d{15}$)|(^\d{17}(x|X|\d)$)/, '请输入正确的身份证号'
                    ],
                    content: function (value) {
                        if (value.length > 3000) {
                            return '字数过多，最多不能超过30000';
                        }
                        if (value.length <= 0) {
                            return '必填项不能为空';
                        }

                    }
                },
                blur: true,
                form: "form",
                ajax: true,
                method: 'POST',
                data: '',
                timeout: 50000,
                cache: false,
                loading: false //是否显示loading图片
            };
        };

    /**
     *@todo 设置参数
     */
    Jqform.prototype.set = function (options) {
        var that = this;
        $.extend(true, that.config, options);
        return that;
    };


    /**
     *@todo 合并配雷超参数
     */
    Jqform.prototype.verify = function (settings) {

        var that = this;
        $.extend(true, that.config.verify, settings);
        return that;
    };

    Jqform.prototype.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) {
            return unescape(r[2]);
        }
    }

    Jqform.prototype.fillForm = function (DOM, url) {
        //判断是否有表单存在，如果有就重置表单
        if ($(DOM).length > 0) {
            $(DOM)[0].reset();
        }

        //判断是否需要为表单填充数据
        if (undefined != url || null != url) {
            var inputHtml = "";
            ;
            var data = url.split("&");
            for (var i = 0; i < data.length; i++) {
                var val = data[i].split("=");
                if (val[0] == "link") {
                    $(DOM).attr("action", val[1]);
                    continue;
                }
                if (val[0] == "id") {
                    $('body').attr("lay-id", val[1]);
                    continue;
                }
                var obj = $(DOM).find('input[name=' + val[0] + ']'),
                    dom = $('input#' + val[0] + ''),
                    img = $('img#' + val[0] + ''),
                    p = $('p#' + val[0] + '');
                if (p.length > 0) {
                    p.html(decodeURIComponent(val[1]))
                }
                if (dom.length > 0) {
                    dom.val(decodeURIComponent(val[1]))
                }
                if (img.length > 0) {
                    img.attr('src', decodeURIComponent(val[1]))
                }
                if (obj.length > 0) {
                    if (obj.prop('type') == "text" || obj.prop('type') == "hidden") {
                        obj.val(decodeURIComponent(val[1]));
                    } else if (obj.prop('type') == "checkbox" || obj.prop('type') == "radio") {
                        obj.each(function (i, n) {
                            var result = val[1].split(",");
                            for (var t = 0; t < result.length; t++) {
                                if ($(n).val() == decodeURIComponent(result[t])) {
                                    $(n).prop("checked", true);
                                }
                            }
                        })

                    }
                } else if ($(DOM).find('textarea[name=' + val[0] + ']').length > 0) {
                    $(DOM).find('textarea[name=' + val[0] + ']').val(decodeURIComponent(val[1]));
                } else if ($(DOM).find('select[name=' + val[0] + ']').length > 0) {
                    $(DOM).find('select[name=' + val[0] + ']').val(decodeURIComponent(val[1]));
                } else {
                    inputHtml += '<input type="hidden" name="' + val[0] + '" value="' + decodeURIComponent(val[1]) + '" />';
                }
            }

            $(DOM).append(inputHtml);
            forms.render();
        }
    }


    /**
     *@todo 根据规则验证数据
     *@param object obj 当前点击的提交按钮对象
     */
    Jqform.prototype.check = function (obj, isTab) {
        var ver = obj.attr('jq-verify').split('|'),
            err = obj.attr('jq-error'),
            errorId = obj.attr('error-id'),
            tips = '',
            stop = false,
            DANGER = 'has-warning',
            SUCCESS = 'has-success',
            verify = this.config.verify,
            value = obj.val(),
            itemOuterHeight = 0,
            top = 0;
        obj.parent('div').removeClass(DANGER);

        if (ver.indexOf("required") == -1 && "" == value && ver.indexOf("content") == -1) {
            return;
        }

        if (err) {
            err = err.split('|');

        }

        $.each(ver, function (index, thisVer) {
            var isFn = typeof verify[thisVer] === 'function';
            if (verify[thisVer] && (isFn ? tips = verify[thisVer](value, obj) : !verify[thisVer][0].test(value))) {

                var errHtml = "",
                    errMsg = (tips || err || verify[thisVer][1]);
                if (errorId == undefined) {
                    if (parseInt(obj.width()) > 200 || $('textarea').css('display') == 'none') {
                        errHtml = '<p class="jq-error">' + errMsg + '</p>';
                        if (obj.next('.jq-error').length <= 0) {
                            obj.after(errHtml);
                        } else {
                            obj.next('.jq-error').html(errMsg);
                        }

                    } else {
                        errHtml = '<div class="error"><p>' + errMsg + '</p></div>';
                        if (obj.parent('div').next('.error').length <= 0) {
                            obj.parent('div').after(errHtml);
                        } else {
                            obj.parent('div').next('.error').html(errMsg);
                        }

                    }
                    obj.parent('div').addClass(DANGER);
                    top = obj.parent('div').offset();
                    itemOuterHeight = obj.outerHeight();

                } else {
                    $('#' + errorId).html(errMsg);
                    top = $('#' + errorId).offset();
                    itemOuterHeight = $('#' + errorId).outerHeight();
                }
                var winScrollTop = $(window).scrollTop(),
                    itemOffsetTop = top.top,

                    winHeight = $(window).height();

                if ((winScrollTop > itemOffsetTop + itemOuterHeight) || (winScrollTop < itemOffsetTop - winHeight)) {
                    $('body,html').stop().animate({scrollTop: top.top - 20}, 500);
                }

                stop = true;
                return;

            } else {
                if (errorId == undefined) {
                    obj.parent('div').removeClass(DANGER);
                    obj.next('.jq-error').remove();
                    obj.parent('div').next('.error').remove();
                } else {
                    $('#' + errorId).empty();
                }
            }
        })
        return stop;
    }
    /**
     *@todo ajax 事件  这里callBack特意采用json格式，如果有需要可以在这里修改
     *@param event object 事件对象
     *@param options object 拼装好的参数对象
     **/
    Jqform.prototype.ajax = function (options, form) {
        if (options == undefined || options == null) {
            return;
        }
        //变为大写
        options.method = options.method.toUpperCase();
        if (options.loading == true) {
            var l = layer.load(1, {
                shade: [0.6,'#ffffff'] //0.1透明度的白色背景
            });
        }
        $.ajax({
            type:'POST',
            url: rootUrl + options.url,
            data: options.data,
            dataType: 'jsonp',
            jsonp: 'callback',
            timeout: options.timeout,
            async: true,
            cache: options.cache,
            error: function (XMLHttpRequest, status, thrownError) {
                if (options.loading == true) {
                    layer.close(l);
                }
                layer.msg('网络繁忙，请稍后重试...');
                return false;
            },
            success: function (msg) {
                if (options.loading == true) {
                    layer.close(l);
                }
                if (msg.code === '500') {
                    top.location.href = 'login.html';
                    return false
                } else if (msg.code !== '200') {
                    layer.msg(msg.msg)
                    return false
                }
                if (!form.callBack(msg, options)) return;
            }
        });
    };
    /**
     *@todo ajax成功后执行方法
     *@param ret object 服务端返回的信息 ret={status:200,data:data,url:baidu.com}
     *@param options object 拼装好的参数对象
     **/
    Jqform.prototype.callBack = function (ret, options) {
        if (options.complete) {
            //自定义方法
            eval(options.complete + "(ret,options)");
            return false;
        } else {
            layer.msg('请添加回调方法')
        }
    };


    /**
     *提交表单时较验
     */
    Jqform.prototype.submit = function (event) {
        var button = $(this),
            form = event.data.form,
            stop = null,
            field = {},
            elem = button.parents(ELEM)
            , verifyElem = elem.find('*[jq-verify]') //获取需要校验的元素
            , formElem = button.parents('form')[0] //获取当前所在的form元素，如果存在的话
            , fieldElem = elem.find('input,select,textarea') //获取所有表单域
            , filter = button.attr('lay-filter') //获取过滤器

        //开始校验


        layui.each(verifyElem, function (_, item) {
            stop = form.check($(this));
            return stop;
        });

        if (stop) return false;
        var arr = [];
        layui.each(fieldElem, function (_, item) {
            if (!item.name) return;
            if (/^checkbox$/.test(item.type)) {
                if (item.checked) {
                    arr.push(item.value);
                    var vals = arr.toString();
                    field[item.name] = vals;
                } else {
                    return;
                }
            } else if (/^radio$/.test(item.type)) {
                if (item.checked) {
                    field[item.name] = item.value;
                } else {
                    return;
                }
            }
            // else if (item.name == 'startTime' || item.name == 'endTime' || item.id == "tranfsTime" || item.id == "tranfsTime_end") {
            //     var fomartdate = new Date(item.value);
            //     if (fomartdate != 'Invalid Date') {
            //         field[item.name] = fomartdate.getTime() / 1000;
            //     } else {
            //         field[item.name] = "";
            //     }
            // }
            else {
                field[item.name] = item.value;
            }


        });

        if (form.config.ajax) {

            var options = {
                "data": field
            }

            if (undefined != $(formElem).attr("method")) {
                options.method = $(formElem).attr("method");
            }
            options.url = $(formElem).attr("action");

            if (options.url.indexOf("xwbanker") !== -1) {
                // return layui.event.call(this, 'form', 'submit(' + filter + ')', {
                //     elem: this,
                //     form: formElem,
                //     field: field
                // });

                var str = $.param(field);
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.full(index);
                window.location.href = options.url + '?' + str;

                return false;
            }
            if (undefined != $(formElem).attr("complete")) {
                options.complete = $(formElem).attr("complete");
            }
            options = $.extend({}, form.config, options);
            form.ajax(options, form);
            return false;
        } else {
            //获取字段

            return layui.event.call(this, 'form', 'submit(' + filter + ')', {
                elem: this,
                form: formElem,
                field: field
            });
        }
    };

    //自动完成渲染
    var form = new Jqform(),
        dom = $(document);
    dom.on('click', '*[jq-submit]', {form: form}, form.submit);

    exports(MOD_NAME, form);
});
