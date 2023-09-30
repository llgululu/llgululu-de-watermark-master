const chartDom1 = document.querySelector('.myChart1');
const myChart1 = echarts.init(chartDom1);
const chartDom2 = document.querySelector('.myChart2');
const myChart2 = echarts.init(chartDom2);
const option1 = {
    title: {
        text: '七日总用户数折线图(可左右滑动)'
    },
    tooltip: {
        trigger: 'axis'
    },
    xAxis: {
        type: 'category',
        data: []
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            data: [],
            type: 'line'
        }
    ],
    dataZoom: [
        {
            type: "inside",  // 支持内部鼠标滚动平移
            start: 20,
            end: 100,
            zoomOnMouseWheel: false,  // 关闭滚轮缩放
            moveOnMouseWheel: true, // 开启滚轮平移
            moveOnMouseMove: true  // 鼠标移动能触发数据窗口平移 
        }
    ]

}
const option2 = {
    title: {
        text: '七日总解析数柱形图(可左右滑动)'
    },
    tooltip: {
        trigger: 'axis'
    },
    xAxis: {
        type: 'category',
        data: []
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            data: [],
            type: 'bar'
        }
    ],
    dataZoom: [
        {
            type: "inside",  // 支持内部鼠标滚动平移
            start: 20,
            end: 100,
            zoomOnMouseWheel: false,  // 关闭滚轮缩放
            moveOnMouseWheel: true, // 开启滚轮平移
            moveOnMouseMove: true  // 鼠标移动能触发数据窗口平移 
        }
    ]
}
myChart1.setOption(option1);
myChart2.setOption(option2);
window.onload = () => {
    getChartsData()
}

function getChartsData() {
    const $ = layui.$;
    $.ajax({
        type: "post",
        url: "/sysadmin/admin/home/getAdminHomeData",
        dataType: "json",
        success: function (obj) {
            setData(obj.data);
            post_Message()
        }
    })
}

function setData(data) {
    myChart1.setOption({
        xAxis: {
            type: 'category',
            data: data["tTime"]
        },
        series: [
            {
                data: data["tUserList"],
                type: 'line'
            }
        ]
    })
    myChart2.setOption({
        xAxis: {
            type: 'category',
            data: data["tTime"]
        },
        series: [
            {
                data: data["tUList"],
                type: 'bar'
            }
        ]
    })
    document.getElementById('dayUserCount').innerText = data["dayUserCount"]
    document.getElementById('totalUserCount').innerText = data["totalUserCount"]
    document.getElementById('dayUCount').innerText = data["dayRecordCount"]
    document.getElementById('totalUCount').innerText = data["totalRecordCount"]
}


function post_Message() {
    let height = document.body.scrollHeight;
    top.postMessage(height + 50, "*");
}

let timeId;
window.onresize = () => {
    clearTimeout(timeId);
    timeId = setTimeout(() => {
        myChart1.resize();
        myChart2.resize();
    }, 500)
}


