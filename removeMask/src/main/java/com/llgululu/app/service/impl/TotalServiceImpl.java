package com.llgululu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llgululu.app.entity.Total;
import com.llgululu.app.mapper.TotalMapper;
import com.llgululu.app.service.ITotalService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.llgululu.app.util.TimeFormatUtil.getStrTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-31
 */
@Service
public class TotalServiceImpl extends ServiceImpl<TotalMapper, Total> implements ITotalService {

    @Override
    public void getTotalDataHome(Map<Object, Object> map) {
        QueryWrapper<Total> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("t_time");
        IPage<Total> totalIPage = baseMapper.selectPage(new Page<>(1,7),wrapper);
        List<Total> list = totalIPage.getRecords();
        List<String> timeList = new ArrayList<>();
        List<Integer> userList = new ArrayList<>();
        List<Integer> uList = new ArrayList<>();
        Collections.reverse(list);
        for (int i = 0; i < list.size(); i++){
            Total total = list.get(i);
            timeList.add(getStrTime(total.gettTime()));
            userList.add(total.gettUser());
            uList.add(total.gettTotalUse());
        }
        map.put("tTime",timeList);
        map.put("tUserList",userList);
        map.put("tUList",uList);
    }
}
