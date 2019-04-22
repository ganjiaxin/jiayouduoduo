

layui.define(['jquery', 'layer', 'form'], function(exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form(),
        modal = function() {
            this.options = {
                type: 1,
                title: false,
                full: false,
                shadeClose: true,
                shade: false,
                content: "",
                area: 'auto',
                anim: 5
            };
        };

    /**
     *@todo 初始化方法
     */
    modal.prototype.init = function() {

        var _this = this;
        //绑定模态iframe
        $(".modal-iframe:not([modal-bind])").each(function() {
            _this.setBind($(this));
            $(this).bind('click', _this.iframe);
        });

        //绑定模态捕获页
        $(".modal-catch:not([modal-bind])").each(function() {
            _this.setBind($(this));
            $(this).bind('click', _this.catch);
        });
        //绑定相册
        $(".modal-photo:not([modal-bind])").each(function() {
            _this.setBind($(this));
            _this.photos;
        });
    }


    /**
     *@todo 为已绑定事件元素设置绑定属性，防止再次初始化时重复绑定
     *@param obj 当前对象
     */
    modal.prototype.setBind = function(obj) {
        obj.attr('modal-bind', 1);
    }

    /**
     *@todo 合并参数
     *@param obj 当前对象
     */
    modal.prototype.setOptions = function(obj) {

        var params = obj.data('params');
        if (undefined == params || null == params) {
            layer.msg('请为当前元素配置data-garams属性');
            return;
        }
        if (typeof params === 'string') {
            params = JSON.parse(params)
        }
        var options = $.extend({}, this.options, params);
        return options;
    }



    /**
     *@todo 打开全屏窗口
     */
  modal.prototype.iframe = function(event) {

        //取得参数并设置
        var options = modal.setOptions($(this));
        if (undefined == options.content || null == options.content) {
            layer.msg('请为当前元素配置的data-garams属性配置content值');
            return false;
        };
        var _area = "auto";
        if (options.area != "auto") {
            _area = options.area.split(',');
        }

        var l = layer.open({
            type: 2,
            title: options.title,
            shade:[0.6,'#23262E'],
            maxmin: true,
            anim: 2,
            area: _area,
            content: options.content
        });
        if (options.full) {
            layer.full(l);
        }
    }







    /*@todo 相册窗口
    */
    modal.prototype.photos = function() {
        var options = modal.setOptions($(this));
        if (undefined == options.content || null == options.content) {
            layer.msg('请为当前元素配置的data-garams属性配置content值');
            return;
        }
        layer.photos({
            photos: options.content,
            anim: options.anim
        });
    }

	var modal = new modal();
    modal.init();
    exports('modal', modal);
});
