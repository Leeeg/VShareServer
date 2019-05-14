package com.lee.vshare.service.service;

import com.lee.vshare.common.entity.VisitLog;
import com.lee.vshare.service.dao.VisitLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 18-12-19
 * @Time 下午7:35
 */
@Service
public class VisitServiceImpl extends BaseService<VisitLog> {

    @Autowired
    private VisitLogMapper logMapper;

    public void addVisitLog(VisitLog visitLog) {
        save(visitLog);
    }

    public List<VisitLog> getAllVisitLog() {
        return logMapper.selectAll();
    }
}
