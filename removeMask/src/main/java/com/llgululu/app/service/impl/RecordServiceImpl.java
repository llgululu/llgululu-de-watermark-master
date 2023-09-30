package com.llgululu.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llgululu.app.entity.Record;
import com.llgululu.app.mapper.RecordMapper;
import com.llgululu.app.service.IRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import static com.llgululu.app.util.TimeFormatUtil.getNowDay;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-28
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

    @Override
    public void addNewRecord(int uid, String analysisUrl, boolean b) {
        Record record = new Record();
        record.setuId(uid);
        record.setrUrl(analysisUrl);
        record.setrState(String.valueOf(b));
        record.setrTime(new Date());
        baseMapper.insert(record);
    }

    @Override
    public void getRecordDataHome(Map<Object, Object> map) {
        QueryWrapper<Record> wrapper = new QueryWrapper<>();
        int totalCount = Math.toIntExact(baseMapper.selectCount(wrapper));
        Map<String,Date> mapDate = getNowDay();
        if (mapDate!=null) {
            wrapper.ge("r_time",mapDate.get("start")).le("r_time",mapDate.get("end"));
        }
        int dayCount = Math.toIntExact(baseMapper.selectCount(wrapper));
        map.put("totalRecordCount",totalCount);
        map.put("dayRecordCount",dayCount);
    }

    @Override
    public void deleteAllRecords() {
        QueryWrapper<Record> wrapper = new QueryWrapper<>();
        baseMapper.delete(wrapper);
    }


}
