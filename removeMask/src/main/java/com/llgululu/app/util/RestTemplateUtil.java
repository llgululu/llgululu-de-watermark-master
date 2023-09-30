package com.llgululu.app.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

public class RestTemplateUtil {
    private static final RestTemplate restTemplate = new RestTemplate();

    public static String getUserOpenID(String appid, String appsecret, String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code&appid=" + appid + "&secret=" + appsecret + "&js_code=" + code;
//        System.out.println(url);
        ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        String body = results.getBody();
        JSONObject json = JSONObject.parseObject(body);
        if (json != null && !json.containsKey("errcode") && !json.containsKey("errmsg")) {
            return json.getString("openid");
        } else {
            return null;
        }
    }

    public static boolean getInfoByApi(String apiUrl, String parseUrl, Map<Object, Object> map) {
        try {
            String url = apiUrl + parseUrl;
            ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            String body = results.getBody();
            JSONObject json = JSONObject.parseObject(body);
            if (json != null && json.getInteger("code") == 200) {
                map.put("title", json.getString("title"));
                if (Objects.equals(json.getString("pics"), "")) {
                    map.put("type", "video");
                    map.put("cover", json.getString("img"));
                        map.put("video", json.getString("url"));
                } else {
                    map.put("type", "images");
                    map.put("images", json.getJSONArray("pics"));
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
