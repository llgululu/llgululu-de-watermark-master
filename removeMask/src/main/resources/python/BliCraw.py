# -*- coding: utf8 -*-
import json
import re  # 正则模块
import sys

import requests  # 数据请求库第三方库

# 伪装请求头方式/请求头参数
headers = {
    'cookie': "请填入你的Cooke",
    'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                  'Chrome/104.0.5112.102 Safari/537.36 Edg/104.0.1293.70 ',
    'Referer': 'https://www.bilibili.com/'
}


def BilVideoCraw(url):
    response = requests.get(url=url, headers=headers)
    # print(response.text)
    try:
        title = re.findall('<title data-vue-meta="true">(.*?)</title>', response.text)[0]
        # print(title)
        cover_data = 'https:' + re.findall('itemprop="thumbnailUrl" content="(.*?)">', response.text)[0]
        # print(cover_data)
        html_data = re.findall('<script>window.__playinfo__=(.*?)</script>', response.text)[0]
        # print(html_data)
        json_data = json.loads(html_data)
        # pprint(json_data)
        # 字典数据  b站视频  和 音频是分离的
        audio_url = json_data['data']['dash']['audio'][0]['baseUrl']
        video_url = json_data['data']['dash']['video'][0]['baseUrl']
        video_obj = {
            "type": "video",
            "video": video_url,
            "audio": audio_url,
            "title": title,
            "cover": cover_data
        }
        r_data = json.dumps(video_obj)
        print(r_data)
    except IndexError:
        print("1")

if __name__ == '__main__':
    a = []
    for i in range(1, len(sys.argv)):
        a.append(sys.argv[i])
    BilVideoCraw(a[0])