package com.llgululu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llgululu.app.entity.Newdownload;
import com.llgululu.app.mapper.NewdownloadMapper;
import com.llgululu.app.service.INewdownloadService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-28
 */
@Service
public class NewdownloadServiceImpl extends ServiceImpl<NewdownloadMapper, Newdownload> implements INewdownloadService {

    @Override
    public void deleteAllNewDownload() {
        QueryWrapper<Newdownload> wrapper = new QueryWrapper<>();
        baseMapper.delete(wrapper);
    }
}
