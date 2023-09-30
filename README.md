# llgululu万能去水印

#### 介绍


该小程序没有后门！！！如果好用，请多多star！！！这对我很重要！！！

这是一个工具类小程序，由我个人开发，其主要功能就是帮助用户去除视频的水印（这里的水印是现在一些热门平台如dy、ks、xhs等下载视频和图片上的平台作者水印）。

小程序通过微信接口wxLogin中返回的openId作为识别用户的标志，从而建立用户的后台管理，实现对用户日使用次数的调整、用户的使用情况的数据收集。

小程序端和后端通过jwt生成的token进行接口验证和用户信息传递。

小程序可开通微信流量主，后台管理系统可设置是否开启广告和banner广告、激励视频广告、插屏广告、视频广告的adId设置。

小程序去水印功能可通过本地去水印功能或者第三方接口实现。（注：本地去水印功能是通过python爬虫实现的）

小程序配置通告功能，可在后台管理系统对通告进行增删改查操作。

后端一些接口使用Redis进行数据缓存，减少对数据库的压力和第三方接口使用次数的浪费。

后台管理系统现实现功能有：每日数据的统计和图标展示、用户管理（数据库）、每日用量管理（redis）、解析记录、新增download域名、系统参数设置、通告设置。

小程序已经上线微信平台，可搜索【ll万能视频去水印】或微信扫描下方小程序码查看。

<img src="https://gitee.com/Gulu_Lv/llgululu-de-watermark/raw/master/showImage/%E5%8E%BB%E6%B0%B4%E5%8D%B0%E5%B0%8F%E7%A8%8B%E5%BA%8F.jpg" width="200" height="200" alt="小程序码"/>

#### 软件架构

小程序端由uniapp+uview进行构建。

后端使用springboot+mybatisPlus+redis+mysql。

后台管理系统使用layui+thymeleaf构建页面，使用echarts实现数据图像化，利用媒体查询适配移动端，使用ajax进行前后端数据交互。

#### 安装教程

1.  removeMaskWx 是小程序前端目录，removeMask 是后端及后台管理系统目录。
2.  先运行removemask.sql文件，建立数据库，本地也要有redis服务。
3.  removeMask目录下，修改yml文件中的mysql和redis的名称、密码。本地去水印功能只支持抖音，如果要使用第三方接口，请自行开通并在yal文件上配置。
4.  resource文件夹下python文件夹里面的两个python文件里面的cookie设置为你自己的cookie，然后修改util文件夹下HomeAnalysisUtil.java文件中的两个python文件的地址（如果使用第三方接口可忽略）。
5.  启动springboot服务，浏览器输入 域名:8004/login/admin/login（本地运行则为 localhost:8004/login/admin/login） ，进入后台管理系统，输入账号密码，默认都为admin，进入页面系统设置下的参数设置，修改后台登录的账号和密码（管理员账号设置），修改系统设置里面的AppId和AppSecret为你小程序的，如果你的小程序没有开通流量主就不用开启广告，如果开通了流量主，请先填写每种广告对应的adId再开启。（这里后台就配置好了）
6.  removeMaskWx 目录用HbulidX打开，修改common.js文件里面的baseURL为你的域名（本地则为localhost:8004）。再修改manifest.json文件微信配置下的appid为你小程序的appid。然后点击发行到微信小程序，进入微信小程序查看前后端是否交互成功即可。


#### 页面展示

##### 小程序页面展示

<img src="https://gitee.com/Gulu_Lv/llgululu-de-watermark/raw/master/showImage/IMG_20230910_235212.jpg" height="450" alt="首页"/>
<img src="https://gitee.com/Gulu_Lv/llgululu-de-watermark/raw/master/showImage/IMG_20230910_235119.jpg" height="450" alt="解析"/>
<img src="https://gitee.com/Gulu_Lv/llgululu-de-watermark/raw/master/showImage/IMG_20230910_235056.jpg" height="450" alt="我的"/>
<img src="https://gitee.com/Gulu_Lv/llgululu-de-watermark/raw/master/showImage/IMG_20230910_235012.jpg" height="450" alt="视频解析"/>
<img src="https://gitee.com/Gulu_Lv/llgululu-de-watermark/raw/master/showImage/IMG_20230910_235258.jpg" height="450" alt="图片解析"/>
<img src="https://gitee.com/Gulu_Lv/llgululu-de-watermark/raw/master/showImage/IMG_20230910_235033.jpg" height="450" alt="通告"/>

##### 后台管理页面展示

<img src="https://gitee.com/Gulu_Lv/llgululu-de-watermark/raw/master/showImage/QQ%E6%88%AA%E5%9B%BE20230911000231.png"  alt="登录"/>
<img src="https://gitee.com/Gulu_Lv/llgululu-de-watermark/raw/master/showImage/QQ%E6%88%AA%E5%9B%BE20230911000247.png"  alt="首页"/>
<img src="https://gitee.com/Gulu_Lv/llgululu-de-watermark/raw/master/showImage/%7B03E6C45A-EA3C-438e-88AF-93381E1D0925%7D.png" alt="用户管理"/>
<img src="https://gitee.com/Gulu_Lv/llgululu-de-watermark/raw/master/showImage/QQ%E6%88%AA%E5%9B%BE20230911000039.png"  alt="参数设置"/>

#### 其他

我的公众号二维码：

<img src="https://gitee.com/Gulu_Lv/wechat-wallpaper-mini-program/raw/master/%E5%9B%BE%E7%89%87/qrcode_for_gh_8ae253c75106_258.jpg" width="200" height="200" alt="公众号二维码"/>

我的其他小程序，望君看看：

<img src="https://gitee.com/Gulu_Lv/wechat-wallpaper-mini-program/raw/master/%E5%9B%BE%E7%89%87/%E5%8F%96%E5%A3%81%E7%BA%B8.jpg" width="200" height="200" alt="小程序码"/>

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request



