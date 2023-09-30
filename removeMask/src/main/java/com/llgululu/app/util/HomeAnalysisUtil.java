package com.llgululu.app.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeAnalysisUtil {
    private static final Pattern pattern1 = Pattern.compile("https://v.douyin.com");
    private static final Pattern pattern2 = Pattern.compile("https://www.iesdouyin.com/share");
    private static final Pattern pattern3 = Pattern.compile("https://b23.tv");
    private static final Pattern pattern4 = Pattern.compile("https://b23.tv/ep");
    private static final Pattern pattern5 = Pattern.compile("https://www.douyin.com/");
    // 下方两个文件地址主要要和python文件位置对应
    private static final String BliPath = "C:\\Users\\咕噜咕噜哟\\Desktop\\文件\\python\\BliCraw.py";
    private static final String DyPath = "C:\\Users\\咕噜咕噜哟\\Desktop\\文件\\python\\DyCraw.py";

    public static String getRedirectUrl(String path) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(path)
                    .openConnection();
            //设置为不对http链接进行重定向处理
            conn.setInstanceFollowRedirects(false);
            conn.setConnectTimeout(5000);
            //返回重定向的链接（父类UrlConnection的方法）
            return conn.getHeaderField("Location");
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean checkUrl(String url, Map<Object, Object> map) {
        Matcher matcher1 = pattern1.matcher(url);
        Matcher matcher3 = pattern3.matcher(url);
        Matcher matcher4 = pattern4.matcher(url);
        Matcher matcher5 = pattern5.matcher(url);
        String location;
        if (matcher1.lookingAt()) {
            String red = getRedirectUrl(url);
            System.out.println(red);
            Matcher matcher2 = null;
            if (red != null) {
                matcher2 = pattern2.matcher(red);
            }
            if (matcher2 != null && matcher2.lookingAt()) {
                location = matcher2.replaceFirst("https://www.douyin.com");
                return getData(location, DyPath, map);
            } else {
                return false;
            }
        } else if (matcher5.lookingAt()) {
            location = url;
            return getData(location, DyPath, map);
        } else if (matcher3.lookingAt() && !matcher4.lookingAt()) {
            location = getRedirectUrl(url);
            return getData(location, BliPath, map);
        } else {
            return false;
        }
    }

    public static boolean getData(String url, String pythonPath, Map<Object, Object> map) {
        try {
            String[] args1 = new String[]{"python", pythonPath, url};
            Process proc = Runtime.getRuntime().exec(args1);//执行PY文件
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = in.readLine();
            int exitVal = proc.waitFor();
            in.close();
            proc.waitFor();
            if (!line.equals("1") && exitVal == 0) {
                jsonParse(line,map);
                return true;
            } else {
                return false;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void jsonParse(String json, Map<Object, Object> map) {
        JSONObject jsonObject = JSON.parseObject(json);
        Map<Object, Object> data = new HashMap<>(jsonObject);
        map.putAll(data);
        System.out.println(map);
    }

}
 
