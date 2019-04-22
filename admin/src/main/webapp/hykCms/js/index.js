layui.define(['jquery', 'elem', 'tabmenu', 'laytpl', 'layer'], function (exports) {
    var $ = layui.jquery,
        element = layui.elem(),
        jqtpl = layui.laytpl,
        layer = layui.layer,
        tabmenu = layui.tabmenu(),
        data = "",
        jqIndex = function () {
        };
    /**
     *@todo 初始化方法
     */
    jqIndex.prototype.init = function (options) {
        this.resize();
        this.loadMenu();
        this.refresh();
    }

    /**
     *@todo 绑定刷新按钮单击事件
     */
    jqIndex.prototype.refresh = function () {
        $('.fresh-btn').bind("click", function () {
            $('.jqadmin-body .layui-show').children('iframe')[0].contentWindow.location.reload(true);
        })
    }
    jqIndex.prototype.doNothing = function () {
        window.event.returnValue = false;
        return false;
    }

    jqIndex.prototype.loadMenu = function () {
        username.innerHTML = layui.data('hykUser').userName;
        $.ajax({
            url: rootUrl + '/f/web/menuList?time=' + new Date().getTime(),
            type: "post",
            dataType: "jsonp",
            jsonp: 'callback',
            data: {
                token:token
            },
            async: false,
            success: function (result) {
                console.log('index:' + result.code);
                if (result.code === '500') {
                    window.location.href = 'login.html';
                     return false;
                } else if (result.code !== '200') {
                    layer.msg(msg.msg)
                    return false;
                } else {
                    data = result.menuList;
                    // var module = main.innerHTML;
                    // console.log(data)
                    // jqtpl(module).render(data, function (html) {
                    //     main_menu.innerHTML = html;
                    // });
                    var submodule = submain.innerHTML;
                    jqtpl(submodule).render(data, function (html) {
                        left_bar.innerHTML = html;
                    });
                    element.init();
                    index.menuBind();
                    index.showMenu();
                }
            }
        })


    }
    /**
     *@todo 绑定左侧菜单显示隐藏按钮单击事件
     */
    jqIndex.prototype.showMenu = function () {
        $('.jqadmin-home').bind("click", function () {
            var bar = $('.jqamdin-left-bar');
            $('.jqadmin-body').animate({
                left: '0'
            });
            bar.animate({
                width: '0'
            });
            $('.jqadmin-foot').animate({
                left: '0'
            });
            $('.display-arrow').fadeIn();
        })

        $('.display-arrow').bind("click", function () {
            var bar = $('.jqamdin-left-bar');
            $('.jqadmin-body').animate({
                left: '200'
            });
            bar.animate({
                width: '200'
            });
            $('.jqadmin-foot').animate({
                left: '200'
            });
            $('.display-arrow').fadeOut();
        })

    }

    /**
     *@todo 自适应窗口
     */
    jqIndex.prototype.resize = function () {
        $(window).on('resize', function () {
            tabmenu.init();
            tabmenu.tabMove(0, 1);
        }).resize();
    }
    /**
     *@todo 初始化菜单
     */
    jqIndex.prototype.menuBind = function () {
        var _this = this;
        //初始化时显示第一个菜单

        //$('.sub-menu').eq(0).slideDown();

        //绑定左侧树菜单的单击事件
        $('.sub-menu .layui-nav-item,.tab-menu').bind("click", function () {
            var obj = $(this);
            $('.sub-menu .layui-nav-item').removeClass('layui-this');
            $(this).addClass('layui-this')
            if (obj.find('dl').length > 0) {
                if (obj.attr('data-bind') == 1) {
                    return;
                }
                obj.attr('data-bind', '1');
                obj.find('dd').bind("click", function () {
                    _this.menuSetOption($(this));
                });
            } else {
                _this.menuSetOption(obj);
            }
        });

        // //绑定主菜单单击事件，点击时显示相应的菜单
        // element.on('nav(main-menu)', function (elem) {
        //     var index = (elem.index());
        //     $('.sub-menu ul').find('.layui-nav-item').removeClass('layui-this');
        //     $('.sub-menu').find('.layui-nav-bar').css('opacity', 0);
        //     $('.sub-menu').slideUp().eq(index).slideDown();
        // });

        //绑定tag菜单向左滚动按钮事件
        $('span.move-left-btn').bind("click", function () {
            var item = tabmenu.config.item,
                ml = parseInt($('' + item + '').children('ul.layui-tab-title').css("margin-left"));
            if (ml < 0) {
                ml = ml + 130;
                if (ml > 0) {
                    ml = 0;
                }
                $('' + item + '').children('ul.layui-tab-title').css("margin-left", ml);
            }
        });

        //绑定tag菜单向右滚动按钮事件
        $('span.move-right-btn').bind("click", function () {
            var obj = $('' + tabmenu.config.item + '').children('ul.layui-tab-title'),
                ml = parseInt(obj.css("margin-left")),
                tab_all_width = parseInt(obj.prop('scrollWidth')),
                navWidth = parseInt(obj.parent('div').width());
            if (ml - navWidth > -tab_all_width) {
                ml = ml - 130;
                if (ml <= parseInt(navWidth - tab_all_width - 54)) {
                    ml = navWidth - tab_all_width - 54;
                }
                obj.css("margin-left", ml);
            }
        });

        //禁止双击选中
        $('span.move-left-btn,span.move-right-btn').bind("selectstart", function () {
            return false;
        })

    }

    /**
     *@todo 设置菜单项，并创建tab菜单
     */
    jqIndex.prototype.menuSetOption = function (obj) {
        var $a = obj.children('a'),
            href = $a.data('url'),
            icon = $a.children('i:first').data('icon'),
            title = $a.data('title'),
            data = {
                href: href,
                icon: icon,
                title: title
            }
        tabmenu.tabAdd(data);
    }

    var index = new jqIndex();
    index.init();
    document.oncontextmenu = function () {
        index.doNothing();
    };

    exports('index', {});
});
