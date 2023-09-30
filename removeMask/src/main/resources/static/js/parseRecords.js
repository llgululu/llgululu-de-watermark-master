//弹出窗口填充数据的变量
let table = null;

layui.use(['table', 'jquery'], function () {
    table = layui.table;
    const $ = layui.jquery;
    table.render({
        elem: '#test',
        url: '/sysadmin/admin/parseRecords/getAdminParseRecordsData',
        cellMinWidth: 80,
        toolbar: '#toolbar',
        defaultToolbar: [],
        page: true,
        limits: [10],
        cols: [[{field: 'rId', fixed: 'left', width: 110, title: '记录编号', sort: true}, {
            field: 'uId', width: 110, title: '用户编号'
        }, {field: 'rUrl', width: 430, title: 'URL',}, {
            field: 'rTime', width: 150, title: '解析时间'
        }, {
            field: 'rState', title: '解析状态', width: 120, templet: function (d) {
                if (d.rState === "true") {
                    return '<i class="layui-icon layui-icon-ok" style="color: blue"></i>';
                } else {
                    return '<i class="layui-icon layui-icon-close" style="color: red"></i> ';
                }
            }
        }]]
    })
    post_Message()
    // 工具栏事件
    table.on('toolbar(test)', function (obj) {
        switch (obj.event) {
            case 'deleteAllRecords':
                $.ajax({
                    type: "post",
                    url: "/sysadmin/admin/parseRecords/deleteAllRecords",
                    dataType: "json",
                    success: function (obj) {
                        layer.msg(obj.msg)
                        table.reload('test', {})
                    }
                })
                break;
        }
    });
})
function post_Message() {
    let height = document.body.scrollHeight;
    top.postMessage(height + 50, "*");
}