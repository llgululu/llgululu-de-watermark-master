window.addEventListener("message", function (e) {
    if (typeof e.data === 'number'){
        const kidFrame = document.getElementById('kidFrame');
        kidFrame.style.height = e.data + "px";
    }
}, false);

function showLeftNav() {
    const $ = layui.$;
    let icon = $('#show-nav-icon');
    $('#admin-container-body').css("left", "200px")
    $('#admin-header-icon').css("left", "200px")
    $('#admin-left-nav').css({
        "transform": "translate3d(0,0,0)",
        "-webkit-transform": "translate3d(0,0,0)"
    })
    $('#admin-title').css({
        "transform": "translate3d(0,0,0)",
        "-webkit-transform": "translate3d(0,0,0)"
    })
    $('body').css({
        "overflow-x": "hidden",
        "overflow-y": "hidden"
    })
    icon.removeClass("layui-icon-spread-left").addClass("layui-icon-shrink-right")
}
function closeLeftNav(){
    const $ = layui.$;
    let icon = $('#show-nav-icon');
    $('#admin-container-body').css("left", "0")
    $('#admin-header-icon').css("left", "0")
    $('#admin-left-nav').css({
        "transform": "translate3d(-200px, 0, 0)",
        "-webkit-transform": "translate3d(-200px, 0, 0)"
    })
    $('#admin-title').css({
        "transform": "translate3d(-200px, 0, 0)",
        "-webkit-transform": "translate3d(-200px, 0, 0)"
    })
    $('body').css({
        "overflow-x": "auto",
        "overflow-y": "auto"
    })
    icon.removeClass("layui-icon-shrink-right").addClass("layui-icon-spread-left")
}
//JS
layui.use(['util'], function () {
    const util = layui.util;
    const $ = layui.$;
    //头部事件
    util.event('lay-header-event', {
        menuLeft: function () { // 左侧菜单事件
            let icon = $('#show-nav-icon');
            if (icon.hasClass("layui-icon-spread-left")) {
                showLeftNav()
            } else {
                closeLeftNav()
            }
        }
    });
});