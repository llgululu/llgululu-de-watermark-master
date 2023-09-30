package com.llgululu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llgululu.app.entity.Admin;
import com.llgululu.app.mapper.AdminMapper;
import com.llgululu.app.service.IAdminService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-29
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Override
    public Admin checkAdminLogin(String adminName, String password) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("ad_name",adminName).eq("ad_password",password);
        Admin admin = baseMapper.selectOne(wrapper);
        if (admin == null) {
            return null;
        }else {
            admin.setAdLoginTime(new Date());
            baseMapper.updateById(admin);
            return admin;
        }
    }
}
