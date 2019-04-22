layui.define(['jquery', 'elem'], function (exports) {
    var $ = layui.jquery,
        element = layui.elem(),
        tabMenu = function () {
            this.config = {
                item: '.jqadmin-tab-box',
                closed: true
            };
        },
        lastTabIdIndex = 0,
        objTab = {},
        rightMenu = "<div id=\"titleClickMenu\" class=\"layui-layer layui-anim layui-anim-upbit\" style=\"color:#333;z-index: 19891015;display:none;\">"
            + "<div id=\"\" class=\"layui-layer-content\">"
            + "<dl class=\"layui-layer-wrap\" style=\"display: block;\">"
            + "<dd id=\"closeAll\"><a href=\"#\"><i class=\"iconfont\">&#xe634;</i><cite>关闭全部</cite></a></dd>"
            // + "<dd id=\"closeSelf\"><a href=\"#\"><i class=\"iconfont\">&#xe634;</i><cite>关闭当前</cite></a></dd>"
            + "<dd id=\"closeOther\"><a href=\"#\"><i class=\"iconfont\">&#xe634;</i><cite>关闭其他</cite></a></dd>"
            + "<dd id=\"reflesh\"><a href=\"#\"><i class=\"iconfont\">&#xe603;</i><cite>刷新</cite></a></dd>"
            + "</dl>"
            + "</div>"
            + "</div>";

    /**
     * [参数设置 options]
     */
    tabMenu.prototype.set = function (options) {
        if (typeof(options) == 'string' && options != "") {
            this.config.item = options;
        } else if (typeof(options) == 'object') {
            $.extend(true, this.config.config, options);
        }
        return this;
    };

    /**
     *@todo 初始化对象
     *@return 返回对象参数初始化结果
     */
    tabMenu.prototype.init = function () {
        var _this = this,
            config = _this.config,
            $container = $('' + config.item + ''),
            filter = $container.attr('lay-filter');

        if (filter === undefined || filter === '') {
            console.log('错误:请设置Tab菜单选项卡属性lay-filter过滤器');
        }
        objTab.titleBox = $container.children('ul.layui-tab-title');
        objTab.titleBox.parent().append(rightMenu);
        objTab.contentBox = $container.children('div.layui-tab-content');
        objTab.tabFilter = filter;
        return _this;
    }

    /**
     *@todo 检查页面是否已打开，如果已打开则返回索引值，否则返回-1
     *@param string title 打开页面的标题
     *@return int tab的索引值，元则返回-1
     */
    tabMenu.prototype.exited = function (title) {
        var tab_index = -1;
        if (objTab.titleBox === undefined) {
            this.init()
        }
        objTab.titleBox.find('li').each(function (i, e) {
            var $em = $(this).children('em');
            if ($em.text() === title) {
                tab_index = i;
            }
            ;
        });
        return tab_index;
    }

    /**
     *@todo 添加tab菜单选项卡
     *@param object data [ title 菜单选项卡标题
     ,href 菜单URL地址
     ,icon 菜单的ICON图标
     ]
     */
    tabMenu.prototype.tabAdd = function (data) {
        var tab_index = this.exited(data.title),
            _this = this;
        if (tab_index === -1) {
            lastTabIdIndex++;
            var content = '<iframe src="' + data.href + '" data-id="' + lastTabIdIndex + '" class="jqadmin-iframe"></iframe>';
            var title = '';

            // 如果icon有定义则添加到标题中
            if (data.icon !== undefined) {
                title += '<i class="iconfont ">' + data.icon + '</i>';
            }
            title += '<em>' + data.title + '</em>';
            if (this.config.closed) {
                title += '<i class="iconfont layui-unselect layui-tab-close" data-id="' + lastTabIdIndex + '">&#xe62d;</i>';
            }

            //添加tab
            element.tabAdd(objTab.tabFilter, {
                title: title,
                content: content,
            });
            if (this.config.closed) {
                //监听关闭事件
                objTab.titleBox.find('li').children('i.layui-tab-close[data-id=' + lastTabIdIndex + ']').on('click', function () {
                    element.tabDelete(objTab.tabFilter, $(this).parent('li').index());
                    _this.tabMove(1, 1);
                    element.init();
                });
            }
            ;
            objTab.titleBox.find('.isTab').on('mouseup', function (event) {
                if (event.which == 3) {
                    var menu = $("#titleClickMenu");
                    var len = objTab.titleBox.find('li').length;
                    var id = $(this).index();
                    menu.css("top", event.screenY - 110);
                    menu.css("left", event.screenX - 190);
                    menu.show();
                    event.stopPropagation();
                    menu.find("#reflesh").unbind("click");
                    menu.find("#reflesh").click(function () {
                        $('.jqadmin-body .layui-show').children('iframe')[0].contentWindow.location.reload(true);
                    });
                    menu.find("#closeAll").unbind("click");
                    menu.find("#closeAll").click(function () {
                        for (i = len; i > 0; i--) {
                            element.tabDelete(objTab.tabFilter, i);
                        }
                        _this.tabMove(1, 1);
                        element.init();
                    });
                    menu.find("#closeOther").unbind("click");
                    menu.find("#closeOther").click(function () {
                        for (i = len; i > 0; i--) {
                            if (id != i) {
                                element.tabDelete(objTab.tabFilter, i);
                            }
                        }
                        _this.tabMove(1, 1);
                        element.init();
                    });
                    menu.find("#closeSelf").unbind("click");
                    menu.find("#closeSelf").click(function () {
                        element.tabDelete(objTab.tabFilter, id);
                        _this.tabMove(1, 1);
                        element.init();
                    });
                }
            })

            this.tabMove(tab_index, 0);
            //切换到当前打开的选项卡
            element.tabChange(objTab.tabFilter, objTab.titleBox.find('li').length - 1);
        } else {
            element.tabChange(objTab.tabFilter, tab_index);
            this.tabMove(tab_index, 0);
        }
    }

    /**
     *@todo 判断菜单选项卡是否已超出了总宽度，若超出则显示左右移动按钮，否则隐藏按钮
     *@param int index 大于等于0时表示菜单选项卡已经存在，才有移动的需求
     *@param int scene 为1时表示删除tab菜单选项卡，为0时表示切换或是添加菜单选项卡
     */



    tabMenu.prototype.tabMove = function (index, scene) {
        //取得屏幕总宽度
        var navWidth = parseInt(objTab.titleBox.parent('div').width());

        //取得菜单选项卡总宽度
        var $tabNav = objTab.titleBox.find('li'),
            tab_all_width = 0;
        $tabNav.each(function (i, n) {
            tab_all_width += $(n).outerWidth(true);
        });
        if (!$tabNav[0]) {
            return
        }
        if (tab_all_width > navWidth + 1) {
            var ml = navWidth - tab_all_width - 54;
            if (ml < 0) {
                if (index >= 0) {
                    var current_tab_left = parseInt(objTab.titleBox.find('.layui-this').position().left),
                        curent_tab_ml = parseInt(objTab.titleBox.css("marginLeft")),
                        curent_ml = current_tab_left + parseInt(curent_tab_ml);

                    if (curent_ml <= 0) {
                        ml = 0 - current_tab_left;
                    } else {
                        var is_show = -(curent_tab_ml - navWidth + parseInt(objTab.titleBox.find('.layui-this').outerWidth(true)) + current_tab_left + 54);
                        if (is_show <= 0) {
                            ml = navWidth - current_tab_left - parseInt(objTab.titleBox.find('.layui-this').outerWidth(true)) - 54;
                        } else {
                            if (scene == 1 && parseInt(curent_tab_ml) < 0) {
                                ml = navWidth - current_tab_left - parseInt(objTab.titleBox.find('.layui-this').outerWidth(true)) - 54;

                                if (ml > 0) {
                                    ml = 0;
                                }
                            } else {
                                return;
                            }
                        }
                    }
                }
                objTab.titleBox.css({"marginLeft": ml});
            }
            if (ml == 0 && tab_all_width < navWidth + 1) {
                objTab.titleBox.parent('div').find('.tab-move-btn').hide();
            } else {
                objTab.titleBox.parent('div').find('.tab-move-btn').show();
            }
        } else {
            objTab.titleBox.parent('div').find('.tab-move-btn').hide();
            objTab.titleBox.css({"marginLeft": 0});
        }
    }

    exports('tabmenu', function (options) {
        var navtab = new tabMenu();
        document.onmouseup = function (event) {
            var menu = $("#titleClickMenu");
            if (menu) {
                menu.hide();
            }
        };
        return navtab.set(options)
    });
});
