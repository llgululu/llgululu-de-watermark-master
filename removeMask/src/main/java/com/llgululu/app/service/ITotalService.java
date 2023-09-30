package com.llgululu.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.llgululu.app.entity.Total;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-31
 */
public interface ITotalService extends IService<Total> {

    void getTotalDataHome(Map<Object, Object> map);
}
