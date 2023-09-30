package com.llgululu.app.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llgululu.app.entity.Setting;
import com.llgululu.app.mapper.SettingMapper;
import com.llgululu.app.service.ISettingService;
import com.llgululu.app.vo.SettingVo;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-27
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements ISettingService {

    @Override
    public SettingVo getSettingVo() {
        Setting setting = baseMapper.selectById(1);
        SettingVo settingVo = new SettingVo();
        settingVo.setSeIsOpenAd(Objects.equals(setting.getSeIsOpenAd(), "1"));
        settingVo.setSeAdBannerId(setting.getSeAdBannerId());
        settingVo.setSeAdInterstitialId(setting.getSeAdInterstitialId());
        settingVo.setSeAdRewardedId(setting.getSeAdRewardedId());
        settingVo.setSeAdVideoId(setting.getSeAdVideoId());
        return settingVo;
    }
}
