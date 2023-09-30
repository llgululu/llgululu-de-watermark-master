//弹出窗口填充数据的变量
let table = null;

layui.use(['table', 'jquery'], function () {
    table = layui.table;
    const form = layui.form;
    const $ = layui.jquery;
    table.render({
        elem: '#test',
        url: '/sysadmin/admin/accountManagement/getAdminAccountManagementData',
        cellMinWidth: 80,
        defaultToolbar: [],
        page: true,
        limits: [10],
        cols: [[{field: 'uId', fixed: 'left', width: 110, title: '用户编号', sort: true}, {
            field: 'uOpenId', width: 308, title: 'openId'
        }, {field: 'uTotalUse', width: 130, title: '已使用总量', sort: true}, {
            field: 'uSysCount', width: 130, title: '默认单日量', sort: true
        }, {field: 'uRegTime', title: '注册时间', width: 120}, {
            field: 'uLoginTime', title: '登录时间', width: 120
        }, {fixed: 'right', title: '操作', width: 134, minWidth: 125, toolbar: '#barRight'}]]
    })
    post_Message()
    table.on('tool(test)', function (obj) { // 双击 toolDouble
        const data = obj.data;
        if (obj.event === 'edit') {
            const index = layer.open({
                title: '编辑 - id:' + data.uId,
                type: 1,
                area: "350px",
                content: '<form class="layui-form" lay-filter="val-filter" style="padding-top: 10px">\n' + '    <div class="layui-form-item">\n' + '        <div class="layui-inline">\n' + '            <label class="layui-form-label">用户编号</label>\n' + '            <div class="layui-input-inline">\n' + '                <input type="text" id="uId" name="uId" lay-verify="required|number" readonly  class="layui-input">\n' + '            </div>\n' + '        </div>\n' + '    </div>\n' + '    <div class="layui-form-item">\n' + '        <div class="layui-inline">\n' + '            <label class="layui-form-label">openId</label>\n' + '            <div class="layui-input-inline">\n' + '                <input type="text" id="uOpenId" name="uOpenId" lay-verify="required" readonly class="layui-input">\n' + '            </div>\n' + '        </div>\n' + '    </div>\n' + '    <div class="layui-form-item">\n' + '        <div class="layui-inline">\n' + '            <label class="layui-form-label">已使用总量</label>\n' + '            <div class="layui-input-inline">\n' + '                <input type="text" id="uTotalUse" name="uTotalUse" lay-verify="required|number"  lay-affix="clear"   class="layui-input">\n' + '            </div>\n' + '        </div>\n' + '    </div>\n' + '    <div class="layui-form-item">\n' + '        <div class="layui-inline">\n' + '            <label class="layui-form-label">日可使用量</label>\n' + '            <div class="layui-input-inline">\n' + '                <input type="text" id="uSysCount" name="uSysCount" lay-verify="required|number"   lay-affix="clear"  class="layui-input">\n' + '            </div>\n' + '        </div>\n' + '    </div>\n' + '    <div class="layui-form-item">\n' + '        <div class="layui-input-block">\n' + '            <button type="submit" class="layui-btn" lay-submit lay-filter="edit-uCount">立即提交</button>\n' + '        </div>\n' + '    </div>\n' + '</form>',
                success: function () {
                    // 对弹层中的表单进行初始化渲染
                    form.render();
                    form.val('val-filter', {
                        "uId": data.uId,
                        "uOpenId": data.uOpenId,
                        "uTotalUse": data.uTotalUse,
                        "uSysCount": data.uSysCount
                    });
                    // 表单提交事件
                    form.on('submit(edit-uCount)', function () {
                        $.ajax({
                            type: "post",
                            url: "/sysadmin/admin/accountManagement/editAccountManagementData",
                            dataType: "json",
                            data: {
                                uTotalUse: $("#uTotalUse").val(), uSysCount: $("#uSysCount").val(), uid: data.uId,
                            },
                            success: function (obj) {
                                if (obj.code === 1001) {
                                    table.reload('test', {})
                                    layer.close(index);
                                } else {
                                    layer.msg(obj.msg)
                                }
                            }
                        })
                        return false; // 阻止默认 form 跳转
                    });
                }
            });
        }
    });

});
function post_Message() {
    let height = document.body.scrollHeight;
    top.postMessage(height + 50, "*");
}