layui.use(['form'], function () {
    const form = layui.form;
    const layer = layui.layer;
    const $ = layui.$;

    function adminAjaxRequest() {
        $.ajax({
            type: "post",
            url: "/sysadmin/admin/setting/getAdminData",
            dataType: "json",
            success: function (obj) {
                if (obj.code === 1001) {
                    form.val('admin-val-filter', {
                        "adId": obj.obj.adId
                        , "adName": obj.obj.adName
                        , "adPassword": obj.obj.adPassword
                        , "adLoginTime": obj.obj.adLoginTime
                    });
                } else {
                    layer.msg(obj.msg)
                }
            }
        })
    }

    adminAjaxRequest()

    function settingsAjaxRequest() {
        $.ajax({
            type: "post",
            url: "/sysadmin/admin/setting/getSettingData",
            dataType: "json",
            success: function (obj) {
                if (obj.code === 1001) {
                    form.val('settings-val-filter', {
                        "seId": obj.obj.seId
                        ,"seApi":obj.obj.seApi
                        , "seIsOpenAd": obj.obj.seIsOpenAd === "1"
                        , "seAdBannerId": obj.obj.seAdBannerId
                        , "seAdInterstitialId": obj.obj.seAdInterstitialId
                        , "seAdRewardedId": obj.obj.seAdRewardedId
                        , "seAdVideoId": obj.obj.seAdVideoId
                        , "seAppId": obj.obj.seAppId
                        , "seAppSecret": obj.obj.seAppSecret
                        , "seUserDailyCount": obj.obj.seUserDailyCount
                        , "seWatchAdd": obj.obj.seWatchAdd
                    });
                } else {
                    layer.msg(obj.msg)
                }
            }
        })
    }

    settingsAjaxRequest()
    post_Message()
    form.on('switch(switchTest)', function (data) {
        if (this.checked) {
            layer.tips('温馨提示：开启广告，请务必输入各个广告码', data.othis)
        } else {
            layer.tips('温馨提示：关闭广告，各个广告码可忽略', data.othis)
        }
    });

    // 提交事件
    form.on('submit(admin-submit)', function (data) {
        const field = data.field; // 获取表单字段值
        $.ajax({
            type: "post",
            url: "/sysadmin/admin/setting/editAdminData",
            dataType: "json",
            data: field,
            success: function (obj) {
                if (obj.code === 1001) {
                    layer.msg(obj.msg)
                    adminAjaxRequest()
                } else {
                    layer.msg(obj.msg)
                }
            }
        })
        return false; // 阻止默认 form 跳转
    });
    // 提交事件
    form.on('submit(setting-submit)', function (data) {
        const field = data.field; // 获取表单字段值

        if (field.seIsOpenAd === undefined) {
            field.seIsOpenAd = "0"
        }else {
            field.seIsOpenAd = "1"
        }
        // console.log(field);
        $.ajax({
            type: "post",
            url: "/sysadmin/admin/setting/editSettingData",
            dataType: "json",
            data: field,
            success: function (obj) {
                if (obj.code === 1001) {
                    layer.msg(obj.msg)
                    settingsAjaxRequest()
                } else {
                    layer.msg(obj.msg)
                }
            }
        })
        return false; // 阻止默认 form 跳转
    });
});

function post_Message() {
    let height = document.body.scrollHeight;
    top.postMessage(height + 50, "*");
}