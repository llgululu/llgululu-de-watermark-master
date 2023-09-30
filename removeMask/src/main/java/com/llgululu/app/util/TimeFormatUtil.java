package com.llgululu.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeFormatUtil {
    /**
     *  
     * @return  今日时间区间  2023/8/31 0:0:0 - 2023/8/31 23:59:59
     */
    public static Map<String,Date> getNowDay() {
        try {
            Map<String,Date> map = new HashMap<>();
            Date date = new Date();
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=formatter.format(date).split(" ")[0];
            Date start  = formatter.parse(time+" 00:00:00");
            Date end  = formatter.parse(time+" 23:59:59");
            map.put("start",start);
            map.put("end",end);
            return map;
        }catch (Exception e) {
            System.out.println("出差了："+e);
            return null;
        }
    }
    public static String getStrTime(Date date) {
        SimpleDateFormat formatter=new SimpleDateFormat("MM/dd");
        return formatter.format(date);
    }
}
