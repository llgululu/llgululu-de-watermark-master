//弹出窗口填充数据的变量
let table = null;

layui.use(['table', 'jquery'], function () {
    table = layui.table;
    const form = layui.form;
    const laydate = layui.laydate;
    const $ = layui.jquery;
    table.render({
        elem: '#test',
        url: '/sysadmin/admin/notification/getAdminNotificationData',
        cellMinWidth: 80,
        toolbar: '#toolbar',
        defaultToolbar: [],
        page: true,
        limits: [10],
        cols: [[{field: 'anId', fixed: 'left', width: 110, title: '通知编号', sort: true}
            , {field: 'anTag', width: 130, title: '通告标签'}, {
                field: 'anTagType', width: 130, title: '标签颜色', templet: function (d) {
                    if (d.anTagType === 'primary') {
                        return '<span style="color: #2979ff">primary</span>';
                    } else if (d.anTagType === 'success') {
                        return '<span style="color: #19be6b">success</span>';
                    }else if (d.anTagType === 'error') {
                        return '<span style="color: #fa3534">error</span>';
                    }else if (d.anTagType === 'warning') {
                        return '<span style="color: #ff9900">warning</span>';
                    }else {
                        return '<span style="color: #909399">info</span>';
                    }
                }
            }, {field: 'anTitle', title: '通告标题', width: 280}, {
                field: 'anCreateTime', title: '创建时间', width: 120
            }, {field: 'anContent', title: '通告内容', width: 200}
            , {fixed: 'right', title: '操作', width: 134, minWidth: 125, toolbar: '#barRight'}]]
    })
    table.on('tool(test)', function (obj) { // 双击 toolDouble
        const data = obj.data;
        if (obj.event === 'edit') {
            const index = layer.open({
                title: '编辑 - id:' + data.anId,
                type: 1,
                area: ['80%', '80%'],
                content: '<form class="layui-form" lay-filter="val-filter" style="padding: 10px">\n' +
                    '    <div class="layui-form-item">\n' +
                    '        <label class="layui-form-label">通知编号</label>\n' +
                    '        <div class="layui-input-inline layui-input-wrap">\n' +
                    '            <input type="text" id="anId" name="anId" lay-verify="required|number" readonly placeholder="请输入"\n' +
                    '                   autocomplete="off" class="layui-input">\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '    <div class="layui-form-item">\n' +
                    '        <label class="layui-form-label">通告标题</label>\n' +
                    '        <div class="layui-input-inline layui-input-wrap">\n' +
                    '            <input type="text" id="anTitle" name="anTitle" lay-verify="required" autocomplete="off" lay-affix="clear"\n' +
                    '                   class="layui-input">\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '    <div class="layui-form-item">\n' +
                    '        <label class="layui-form-label">通告标签</label>\n' +
                    '        <div class="layui-input-inline layui-input-wrap">\n' +
                    '            <input type="text" id="anTag" name="anTag" lay-verify="required"\n' +
                    '                   autocomplete="off" class="layui-input">\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '    <div class="layui-form-item">\n' +
                    '        <label class="layui-form-label">标签颜色</label>\n' +
                    '        <div class="layui-input-block">\n' +
                    '            <input type="radio" name="anTagType" value="primary" title="primary" checked>\n' +
                    '            <div lay-radio>\n' +
                    '                <span style="color:#2979ff">primary</span>\n' +
                    '            </div>\n' +
                    '            <input type="radio" name="anTagType" value="success" title="success" >\n' +
                    '            <div lay-radio>\n' +
                    '                <span style="color:#19be6b">success</span>\n' +
                    '            </div>\n' +
                    '            <input type="radio" name="anTagType" value="error" title="error" >\n' +
                    '            <div lay-radio>\n' +
                    '                <span style="color:#fa3534">error</span>\n' +
                    '            </div>\n' +
                    '            <input type="radio" name="anTagType" value="warning" title="warning" >\n' +
                    '            <div lay-radio>\n' +
                    '                <span style="color:#ff9900">warning</span>\n' +
                    '            </div>\n' +
                    '            <input type="radio" name="anTagType" value="info" title="info" >\n' +
                    '            <div lay-radio>\n' +
                    '                <span style="color:#909399">info</span>\n' +
                    '            </div>\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '    <div class="layui-form-item layui-form-text">\n' +
                    '        <label class="layui-form-label">通告内容</label>\n' +
                    '        <div class="layui-input-block">\n' +
                    '            <textarea placeholder="请输入通告内容" id="anContent" lay-verify="required" name="anContent"\n' +
                    '                      class="layui-textarea"></textarea>\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '    <div class="layui-form-item">\n' +
                    '        <label class="layui-form-label">创建时间</label>\n' +
                    '        <div class="layui-input-inline layui-input-wrap">\n' +
                    '            <div class="layui-input-prefix">\n' +
                    '                <i class="layui-icon layui-icon-date"></i>\n' +
                    '            </div>\n' +
                    '            <input type="text" name="anCreateTime" id="anCreateTime" lay-verify="date" lay-verify="required" placeholder="yyyy-MM-dd"\n' +
                    '                   autocomplete="off" class="layui-input">\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '    <div class="layui-form-item">\n' +
                    '        <div class="layui-input-block">\n' +
                    '            <button type="submit" class="layui-btn" lay-submit lay-filter="edit-an">立即提交</button>\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '</form>',
                success: function () {
                    // 对弹层中的表单进行初始化渲染
                    form.render();
                    form.val('val-filter', {
                        "anId": data.anId,
                        "anTag": data.anTag,
                        "anTagType": data.anTagType,
                        "anTitle": data.anTitle,
                        "anContent": data.anContent
                    });
                    // 初始赋值
                    laydate.render({
                        elem: '#anCreateTime',
                        value: data.anCreateTime,
                        isInitValue: true
                    });
                    // 表单提交事件
                    form.on('submit(edit-an)', function (data) {
                        const field = data.field; // 获取表单字段值
                        field.anCreateTime = new Date(field.anCreateTime)
                        $.ajax({
                            type: "post",
                            url: "/sysadmin/admin/notification/editAdminNotificationData",
                            dataType: "json",
                            data: field,
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
        } else {
            layer.confirm('确定删除id为' + data.anId + '的通告吗?', function (index) {
                $.ajax({
                    type: "post",
                    url: "/sysadmin/admin/notification/deleteAdminNotificationData",
                    dataType: "json",
                    data: {anId: data.anId},
                    success: function (obj) {
                        if (obj.code === 1001) {
                            layer.close(index);
                            table.reload('test', {})
                        } else {
                            layer.close(index);
                            layer.msg(obj.msg)
                        }
                    }
                })
            });
        }
    });
    table.on('toolbar(test)', function (obj) {
        switch (obj.event) {
            case 'add':
                const index = layer.open({
                    title: '新增通告',
                    type: 1,
                    area: ['80%', '80%'],
                    content: '<form class="layui-form" style="padding: 10px">\n' +
                        '    <div class="layui-form-item">\n' +
                        '        <label class="layui-form-label">通告标题</label>\n' +
                        '        <div class="layui-input-inline layui-input-wrap">\n' +
                        '            <input type="text" id="anTitle" name="anTitle" lay-verify="required" autocomplete="off" lay-affix="clear"\n' +
                        '                   class="layui-input">\n' +
                        '        </div>\n' +
                        '    </div>\n' +
                        '    <div class="layui-form-item">\n' +
                        '        <label class="layui-form-label">通告标签</label>\n' +
                        '        <div class="layui-input-inline layui-input-wrap">\n' +
                        '            <input type="text" id="anTag" name="anTag" lay-verify="required"\n' +
                        '                   autocomplete="off" class="layui-input">\n' +
                        '        </div>\n' +
                        '    </div>\n' +
                        '    <div class="layui-form-item">\n' +
                        '        <label class="layui-form-label">标签颜色</label>\n' +
                        '        <div class="layui-input-block">\n' +
                        '            <input type="radio" name="anTagType" value="primary" title="primary" checked>\n' +
                        '            <div lay-radio>\n' +
                        '                <span style="color:#2979ff">primary</span>\n' +
                        '            </div>\n' +
                        '            <input type="radio" name="anTagType" value="success" title="success" >\n' +
                        '            <div lay-radio>\n' +
                        '                <span style="color:#19be6b">success</span>\n' +
                        '            </div>\n' +
                        '            <input type="radio" name="anTagType" value="error" title="error" >\n' +
                        '            <div lay-radio>\n' +
                        '                <span style="color:#fa3534">error</span>\n' +
                        '            </div>\n' +
                        '            <input type="radio" name="anTagType" value="warning" title="warning" >\n' +
                        '            <div lay-radio>\n' +
                        '                <span style="color:#ff9900">warning</span>\n' +
                        '            </div>\n' +
                        '            <input type="radio" name="anTagType" value="info" title="info" >\n' +
                        '            <div lay-radio>\n' +
                        '                <span style="color:#909399">info</span>\n' +
                        '            </div>\n' +
                        '        </div>\n' +
                        '    </div>\n' +
                        '    <div class="layui-form-item layui-form-text">\n' +
                        '        <label class="layui-form-label">通告内容</label>\n' +
                        '        <div class="layui-input-block">\n' +
                        '            <textarea placeholder="请输入通告内容" id="anContent" lay-verify="required" name="anContent"\n' +
                        '                      class="layui-textarea"></textarea>\n' +
                        '        </div>\n' +
                        '    </div>\n' +
                        '    <div class="layui-form-item">\n' +
                        '        <label class="layui-form-label">创建时间</label>\n' +
                        '        <div class="layui-input-inline layui-input-wrap">\n' +
                        '            <div class="layui-input-prefix">\n' +
                        '                <i class="layui-icon layui-icon-date"></i>\n' +
                        '            </div>\n' +
                        '            <input type="text" name="anCreateTime" id="anCreateTime" lay-verify="date" placeholder="yyyy-MM-dd"\n' +
                        '                   autocomplete="off" lay-verify="required" class="layui-input">\n' +
                        '        </div>\n' +
                        '    </div>\n' +
                        '    <div class="layui-form-item">\n' +
                        '        <div class="layui-input-block">\n' +
                        '            <button type="submit" class="layui-btn" lay-submit lay-filter="add-an">立即提交</button>\n' +
                        '        </div>\n' +
                        '    </div>\n' +
                        '</form>',
                    success: function () {
                        // 对弹层中的表单进行初始化渲染
                        form.render();
                        laydate.render({
                            elem: '#anCreateTime',
                            value: new Date(),
                            isInitValue: true
                        });
                        // 表单提交事件
                        form.on('submit(add-an)', function (data) {
                            const field = data.field; // 获取表单字段值
                            field.anCreateTime = new Date(field.anCreateTime)
                            $.ajax({
                                type: "post",
                                url: "/sysadmin/admin/notification/addAdminNotificationData",
                                dataType: "json",
                                data: field,
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
                break;
        }
    });
    post_Message()
});

function post_Message() {
    let height = document.body.scrollHeight;
    top.postMessage(height + 50, "*");
}