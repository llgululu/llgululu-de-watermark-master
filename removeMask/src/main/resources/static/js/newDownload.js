//弹出窗口填充数据的变量
let table = null;

layui.use(['table', 'jquery'], function () {
    table = layui.table;
    const $ = layui.jquery;
    table.render({
        elem: '#test',
        url: '/sysadmin/admin/newDownload/getAdminNewDownloadData',
        cellMinWidth: 80,
        toolbar: '#toolbar',
        defaultToolbar: [],
        page: true,
        limits: [10],
        cols: [[{field: 'nId', fixed: 'left', width: 80, title: '编号', sort: true},
            {
                field: 'nUrl',
                width: 430,
                title: 'URL'
            }]]
    })
    post_Message()
    // 工具栏事件
    table.on('toolbar(test)', function (obj) {
        const id = obj.config.id;
        switch (obj.event) {
            case 'deleteAllRecords':
                $.ajax({
                    type: "post",
                    url: "/sysadmin/admin/newDownload/deleteAllNewDownload",
                    dataType: "json",
                    success: function (obj) {
                        layer.msg(obj.msg)
                        table.reload('test', {})
                    }
                })
                break;
            case 'getData':
                const getData = table.getData(id);
                layer.alert(listFormat(getData),{  area: "350px",});
                break;
        }
    });
})

function listFormat(list) {
    let str = "";
    for (let i = 0; i < list.length; i++) {
        if (i !== list.length - 1) {
            str =str+list[i].nUrl+";"
        }else {
            str = str+list[i].nUrl
        }
    }
    return str;
}
function post_Message() {
    let height = document.body.scrollHeight;
    top.postMessage(height + 50, "*");
}
