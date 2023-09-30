package com.llgululu.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.llgululu.app.entity.Admin;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-29
 */
public interface IAdminService extends IService<Admin> {

    Admin checkAdminLogin(String adminName, String password);
}
