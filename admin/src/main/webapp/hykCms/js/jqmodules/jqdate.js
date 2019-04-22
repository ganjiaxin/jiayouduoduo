layui.define(['jquery', 'laydate'], function(exports) {
    var $ = layui.jquery,
        laydate = layui.laydate;
    var start = {
        istime: true,
        istoday: false,format: 'YYYY-MM-DD',
        choose: function (datas) {
            end.min = datas;
            end.start = datas
        }
    };
    var end = {
        istime: true,
        istoday: false,format: 'YYYY-MM-DD',
        // choose: function (datas) {
        //     start.max = datas;
        // }
    };
    $('.start-date').click(function () {
        start.elem = this;
        laydate(start);
    })
    $('.end-date').click(function () {
        end.elem = this;
        laydate(end);
    })
    exports('jqdate', {});
});
