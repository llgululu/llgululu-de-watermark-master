package com.llgululu.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.llgululu.app.entity.Record;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author llgululu
 * @since 2023-08-28
 */
public interface IRecordService extends IService<Record> {

    void addNewRecord(int uid, String analysisUrl, boolean b);

    void getRecordDataHome(Map<Object, Object> map);

    void deleteAllRecords();
}
