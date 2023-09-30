package com.llgululu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llgululu.app.entity.Userinfo;
import com.llgululu.app.mapper.UserinfoMapper;
import com.llgululu.app.service.IUserinfoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import static com.llgululu.app.util.TimeFormatUtil.getNowDay;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-27
 */
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements IUserinfoService {

    @Override
    public Userinfo checkUserIsExist(String openid,int dailyCount) {
        QueryWrapper<Userinfo> wrapper = new QueryWrapper<>();
        wrapper.eq("u_open_id", openid);
        Userinfo userinfo = baseMapper.selectOne(wrapper);
        if (userinfo==null){
            userinfo = new Userinfo();
            userinfo.setuOpenId(openid);
            userinfo.setuLoginTime(new Date());
            userinfo.setuRegTime(new Date());
            userinfo.setuSysCount(dailyCount);
            userinfo.setuTotalUse(0);
            baseMapper.insert(userinfo);
        }else {
            userinfo.setuLoginTime(new Date());
            baseMapper.updateById(userinfo);
        }
        userinfo = baseMapper.selectOne(wrapper);
        return userinfo;
    }

    @Override
    public void addUserTotalCount(int uid,int total) {
        Userinfo userinfo = new Userinfo();
        userinfo.setuId(uid);
        userinfo.setuTotalUse(total);
        baseMapper.updateById(userinfo);
    }

    @Override
    public void getUserinfoDataHome(Map<Object, Object> map) {
        QueryWrapper<Userinfo> wrapper = new QueryWrapper<>();
        int totalCount = Math.toIntExact(baseMapper.selectCount(wrapper));
        Map<String,Date> mapDate = getNowDay();
        if (mapDate!=null) {
            wrapper.ge("u_login_time",mapDate.get("start")).le("u_login_time",mapDate.get("end"));
        }
        int dayCount = Math.toIntExact(baseMapper.selectCount(wrapper));
        map.put("totalUserCount",totalCount);
        map.put("dayUserCount",dayCount);
    }

    @Override
    public Boolean editUserInfoByAdmin(Integer uid, Integer uSysCount, Integer uTotalUse) {
        QueryWrapper<Userinfo> wrapper = new QueryWrapper<>();
        wrapper.eq("u_id",uid);
        Userinfo userinfo = baseMapper.selectOne(wrapper);
        if (userinfo==null){
            return true;
        }else {
            userinfo.setuId(uid);
            userinfo.setuSysCount(uSysCount);
            userinfo.setuTotalUse(uTotalUse);
            baseMapper.updateById(userinfo);
            return false;
        }
    }
}
