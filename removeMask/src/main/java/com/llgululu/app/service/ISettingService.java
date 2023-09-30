package com.llgululu.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.llgululu.app.entity.Setting;
import com.llgululu.app.vo.SettingVo;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-27
 */
public interface ISettingService extends IService<Setting> {

    SettingVo getSettingVo();
}
