package com.llgululu.app.util;


import com.llgululu.app.entity.Userinfo;
import com.llgululu.app.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public void expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     */
    public void hmset(String key, Map<Object, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 储存 用户信息
     *
     * @param userinfo 用户信息 储存24小时
     */
    public void checkUserIsExist(Userinfo userinfo) {
        Map<Object, Object> userInfoMap = hmget("userinfo");
        if (!userInfoMap.containsKey("user" + userinfo.getuId())) {
            userInfoMap.put("user" + userinfo.getuId(), userinfo);
        }
        hmset("userinfo", userInfoMap, 25 * 60 * 60);
    }

    /**
     * @param uId 从token提取的用户id
     * @return 是否还剩解析次数
     */
    public boolean userHasCount(int uId) {
        Map<Object, Object> userInfoMap = hmget("userinfo");
        Userinfo userinfo = (Userinfo) userInfoMap.get("user" + uId);
        int count = userinfo.getuSysCount();
        return count != 0;
    }

    /**
     * 解析成功 减少解析次数
     *
     * @param uId 从token提取的用户id
     */
    public int userCountDelete(int uId) {
        Map<Object, Object> userInfoMap = hmget("userinfo");
        Userinfo userinfo = (Userinfo) userInfoMap.get("user" + uId);
        int count = userinfo.getuSysCount();
        count = count - 1;
        userinfo.setuSysCount(count);
        userinfo.setuTotalUse(userinfo.getuTotalUse() + 1);
        userInfoMap.remove("user" + uId);
        userInfoMap.put("user" + uId, userinfo);
        redisTemplate.opsForHash().putAll("userinfo", userInfoMap);
        return userinfo.getuTotalUse();
    }

    public UserInfoVo userCountAdd(int uId, int addCount) {
        Map<Object, Object> userInfoMap = hmget("userinfo");
        Userinfo userinfo = (Userinfo) userInfoMap.get("user" + uId);
        int count = userinfo.getuSysCount();
        count = count + addCount;
        userinfo.setuSysCount(count);
        userInfoMap.remove("user" + uId);
        userInfoMap.put("user" + uId, userinfo);
        redisTemplate.opsForHash().putAll("userinfo", userInfoMap);
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setuId(userinfo.getuId());
        userInfoVo.setuSysCount(userinfo.getuSysCount());
        userInfoVo.setuTotalUse(userinfo.getuTotalUse());
        return userInfoVo;
    }

    public UserInfoVo getUserInfo(int uid) {
        Map<Object, Object> userInfoMap = hmget("userinfo");
        Userinfo userinfo = (Userinfo) userInfoMap.get("user" + uid);
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setuId(userinfo.getuId());
        userInfoVo.setuSysCount(userinfo.getuSysCount());
        userInfoVo.setuTotalUse(userinfo.getuTotalUse());
        return userInfoVo;
    }

    public void setUserInfoMap(List<Userinfo> list) {
        Map<Object, Object> userInfoMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            userInfoMap.put("user" + list.get(i).getuId(), list.get(i));
        }
        hmset("userinfo", userInfoMap, 25 * 60 * 60);
    }

    public List<Userinfo> getUserInfoListByPage(Integer limit, Integer page) {
        List<Userinfo> rList = new ArrayList<>();
        Map<Object, Object> userInfoMap = hmget("userinfo");
        List<Userinfo> totalList = new ArrayList<>();
        for (Object value : userInfoMap.values()) {
            Userinfo userinfo = (Userinfo) value;
            totalList.add(userinfo);
        }
        Collections.sort(totalList);
        int start;
        int end;
        int length = totalList.size();
        int temp = length / limit;
        if (page - 1 >= temp) {
            start = temp * limit;
            end = length;
        } else {
            start = (page - 1)*10;
            end = start + limit;
        }
        for (int i = start; i < end; i++) {
            rList.add(totalList.get(i));
        }
        
        return rList;
    }

    public long getUserInfoSize() {
        Map<Object, Object> userInfoMap = hmget("userinfo");
        return userInfoMap.size();
    }

    public boolean editUserInfoByAdmin(Integer uid, Integer uSysCount) {
        Map<Object, Object> userInfoMap = hmget("userinfo");
        if (userInfoMap.containsKey("user"+uid)){
            Userinfo userinfo = (Userinfo) userInfoMap.get("user"+uid);
            userinfo.setuSysCount(uSysCount);
            userInfoMap.remove("user" + uid);
            userInfoMap.put("user" + uid, userinfo);
            redisTemplate.opsForHash().putAll("userinfo", userInfoMap);
            return true;
        }else{
            return false;
        }
    }
}
