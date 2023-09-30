package com.llgululu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.llgululu.app.entity.Announcement;
import com.llgululu.app.mapper.AnnouncementMapper;
import com.llgululu.app.service.IAnnouncementService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-27
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements IAnnouncementService {

    @Override
    public List<Announcement> getAnnouncements() {
        QueryWrapper<Announcement> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("an_create_time");
        return baseMapper.selectList(wrapper);
    }
}
