# -*- coding: utf8 -*-
import requests  # 数据请求库第三方库
import re  # 正则模块
import json
import sys

# 伪装请求头方式/请求头参数
headers = {
    'cookie': '请填写你的cookie',
    'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                  'Chrome/104.0.5112.102 Safari/537.36 Edg/104.0.1293.70 '
}


def DyVideoCraw(url):
    response = requests.get(url=url, headers=headers)
    # 获取网页源代码
    # print(response.text)
    try:
        html_date = re.findall('<script id="RENDER_DATA" type="application/json">(.*?)</script', response.text)[0]
        html_date = requests.utils.unquote(html_date)
        # print(html_date)
        # 解析出标题
        title_data = re.findall('"desc":"(.*?)","', html_date)[0]
        # print(title_date)
        # 解析出封面
        cover_data = 'https:' + re.findall('originCover":"(.*?)","', html_date)[0]
        # print(cover_date)
        # 解析出视频地址
        date1 = re.findall('playAddr(.*?),', html_date)[0]
        video_url = 'https:' + re.findall('"src":"(.*?)"}', date1)[0]
        # print(video_url)
        video_obj = {
            "type": "video",
            "video": video_url,
            "title": title_data,
            "cover": cover_data
        }
        r_data = json.dumps(video_obj)
        print(r_data)
    except IndexError:
        print("1")




def DyImageCraw(url):
    response = requests.get(url=url, headers=headers)
    # 获取网页源代码
    # print(response.text)
    try:
        html_date = re.findall('<script id="RENDER_DATA" type="application/json">(.*?)</script', response.text)[0]
        html_date = requests.utils.unquote(html_date)
        # print(html_date)
        # 解析出标题
        title_date = re.findall('"desc":"(.*?)","', html_date)[0]
        # print(title_date)
        # 解析出图片地址
        date1 = re.findall('"images":\[(.*?)],"imageInfos"', html_date)[0]
        date1 = re.findall('"urlList":\["(.*?)"],"downloadUrlList', date1)
        images_list = []
        for i in date1:
            images_list.append(i.split('","')[0])
        images_obj = {
            "type": "image",
            "images": images_list,
            "title": title_date
        }
        r_data = json.dumps(images_obj)
        print(r_data)
    except IndexError:
        print("1")


if __name__ == '__main__':
    a = []
    for i in range(1, len(sys.argv)):
        a.append(sys.argv[i])   
    # print(a[0])
    if a[0][23:28]=='video':
        DyVideoCraw(a[0])
    else:
        DyImageCraw(a[0])
