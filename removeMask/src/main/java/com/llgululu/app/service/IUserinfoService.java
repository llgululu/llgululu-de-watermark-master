package com.llgululu.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.llgululu.app.entity.Userinfo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-27
 */
public interface IUserinfoService extends IService<Userinfo> {

    Userinfo checkUserIsExist(String openid,int dailyCount);

    void addUserTotalCount(int uid,int total);

    void getUserinfoDataHome(Map<Object, Object> map);

    Boolean editUserInfoByAdmin(Integer uid, Integer uSysCount, Integer uTotalUse);
}
