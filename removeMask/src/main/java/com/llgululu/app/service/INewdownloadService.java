package com.llgululu.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.llgululu.app.entity.Newdownload;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-28
 */
public interface INewdownloadService extends IService<Newdownload> {

    void deleteAllNewDownload();
}
