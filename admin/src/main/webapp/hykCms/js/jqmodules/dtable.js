layui.define(['jquery', 'laytpl', 'layer', 'modal', 'ajax', 'laypage', 'form'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        tpl = layui.laytpl,
        modal = layui.modal,
        ajax = layui.ajax,
        form = layui.form(),
        laypage = layui.laypage,
        dtable = function () {
            this.options = {
                method: "POST",
                timeout: 50000,
                complete: '',
                url: '',
                data: {
                    token: token
                },
                tplid: "list-tpl",
                listid: "list",
                pageid: "", //如果留空则不分页
                curr: 1,
                cache: "false",
                loading: true,
                async: true,
                pages: 0 //分页的总页数，通过服务端获取
            };
        };

    /**
     *@todo 初始化数据
     */
    dtable.prototype.init = function (tplid) {
        this.setOption(tplid);
        this.ajax();
        this.setFormUrl();
        // var curr_page = parseInt(location.hash.replace('#!page=', ''));
        // if (curr_page > 1) {
        //
        // }
        // this.setUrlPage(curr_page);

        modal.init();
    }

    /**
     *@设置请求参数
     *@param string id 渲染模板的ID
     *备注：模板需配置data-params属性，值需严格的json格式，如：'{"content": "add-article.html", "title": "{{item.title}}"}'
     */
    dtable.prototype.setOption = function (id) {
        if (undefined != id || null != id) {
            this.options.tplid = id;
        }
        var params = $("#" + this.options.tplid).attr('data-params');
        if (typeof params === 'string') {
            params = JSON.parse(params)
        }
        this.options = $.extend({}, this.options, params);
        if (undefined == this.options.url || null == this.options.url) {
            this.options.url = window.location.pathname;
        }

        if (this.options.url.indexOf("_=") != -1) {
            this.options.url = this.options.url.replace(/_=\d+/, "_=" + new Date().getTime());
        } else {
            if (this.options.url.indexOf("?") != -1) {
                this.options.url = this.options.url + "&_=" + new Date().getTime();
            } else {

                this.options.url = this.options.url + "?_=" + new Date().getTime();
            }
        }
    }

    /**
     *@todo 设置url中page参数为当前点击的页数
     *@param int curr 需要跳转到的页数（即点击分页按钮后的页数）
     */
    dtable.prototype.setUrlPage = function (curr) {
        if (this.options.url.indexOf("pageNo=") != -1) {
            this.options.url = this.options.url.replace(/pageNo=\d+/, "pageNo=" + curr);
        } else {
            if (this.options.url.indexOf("?") != -1) {
                this.options.url = this.options.url + "&pageNo=" + curr;
            } else {
                this.options.url = this.options.url + "?pageNo=" + curr;
            }
        }
        $('#' + this.options.listid).attr('pagenum', curr)
        this.ajax(curr);
    }

    /**
     *@todo 把表单参数拼成url
     */
    dtable.prototype.setFormUrl = function () {
        var _this = this;
        form.on('submit(search)', function (data) {
            var params = $.param(data.field);
            if (params == "" || params == undefined) return false;
            var url = $(data.form).attr('action');
            if (url.indexOf("?") != -1) {
                _this.options.url = url + "&" + params;
            } else {
                _this.options.url = url + "?" + params;
            }
            _this.ajax();
            return false;
        })
    }
    dtable.prototype.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) {
            return unescape(r[2]);
        }
    }
    /**
     *@todo 向服务请求数据
     */
    dtable.prototype.ajax = function (curr) {
        var _this = this;

        var pageid = _this.options.pageid;
        var loading = _this.options.loading;
        var tplid = _this.options.tplid;
        var listid = _this.options.listid;
        if (loading == true) {
            l = layer.load(0, {shade: [0.5, '#fff']});
        }
        console.log(_this.options.method)
        $.ajax({
            type: _this.options.method,
            url: rootUrl + _this.options.url,
            data: _this.options.data,
            dataType: "jsonp",
            jsonp: 'callback',
            async: _this.options.async,
            cache: _this.options.cache,
            error: function (XMLHttpRequest, status, thrownError) {
                if (loading == true) {
                    layer.close(l);
                }
                layer.msg('网络繁忙，请稍后重试...');
            },
            success: function (ret) {
                if (loading == true) {
                    layer.close(l);
                }
                if (ret.code === '500') {
                    top.location.href = 'login.html';
                    return false;
                }
                if (ret.code !== '200') {
                    layer.msg(ret.msg)
                    return false;
                }
                _this.render(ret,tplid,listid);
                if ("" != pageid && curr == undefined) {
                    _this.options.pages = Math.ceil(ret.page.count / ret.page.pageSize);
                    _this.page();
                }
                if(!_this.callBack(ret, _this.options,_this.options.complete)) return

            }
        });
    }

    /**
     *@todo 数据绑定到模板视图
     *@param json data 后台取得的数据
     */
    dtable.prototype.render = function (data,tplid,listid) {
        var _this = this;
        if ("" != data || undefined != data) {
            var getTpl = $('#' + tplid).html();
            tpl(getTpl).render(data, function (html) {
                $('#' + listid).html(html);
            })
        }
        _this.selectAll();
        modal.init();
        ajax.init();
        form.render();

    }


    dtable.prototype.selectAll = function () {
        var _this = this;
        form.on('checkbox(check)', function (data) {
            var checked = data.elem.checked,
                name = $(data.elem).data('name');
            $('#' + _this.options.listid).find("input[name=" + name + "]").prop("checked", function () {
                return checked;
            });
            form.render();
        });
    }

    dtable.prototype.contains = function (data, needle) {
        if(data===undefined || data===null){
            return false
        }
        if(data.indexOf(needle)>=0){
            return true
        }
        return false;
       // return true
    }
    /**
     *@todo 成功后执行方法
     *@param ret object 服务端返回的信息 ret={status:200
     * ,data:data,url:baidu.com}
     *@param options object 拼装好的参数对象
     **/
    dtable.prototype.callBack = function (ret, options, complete) {
        if ((undefined == ret) || (null == ret))
            return false;
        if (ret.code==='200') {
            eval(complete + "(ret,options)");
            return false;
        }
        return true;
    };

    dtable.prototype.page = function () {
        var _this = this;
        laypage({
            cont: $(_this.options.pageid)
            , pages: _this.options.pages
            , first: 1
            , last: _this.options.pages
            , skip: true
            , curr: location.hash.replace('#!page=', '')
            , hash: 'page'
            , jump: function (obj, first) {
                if (!first) {
                    _this.setUrlPage(obj.curr);
                }
            }, skin: '#FFB800'
        });
    }

    exports('dtable', dtable);
});
