layui.use(['jquery', 'form'], function () {
    const $ = layui.jquery;
    const form = layui.form;
    form.on('submit(admin-login)', function () {
        $.ajax({
            type: "post",
            url: "/login/admin/checkLogin",
            dataType: "json",
            data: {
                adminName: $("#adminName").val(),
                password: $("#password").val()
            },
            success: function (obj) {
                if (obj.code === 1001) {
                    window.location.href = "/sysadmin/admin/home";
                } else {
                    layer.msg(obj.msg)
                }
            }
        })
        return false;
    })
});