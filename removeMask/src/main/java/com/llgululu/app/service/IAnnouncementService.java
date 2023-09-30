package com.llgululu.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.llgululu.app.entity.Announcement;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-27
 */
public interface IAnnouncementService extends IService<Announcement> {

    List<Announcement> getAnnouncements();
}
