package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.ActivityLog;
import com.cyber.authing.mapper.ActivityLogMapper;
import com.cyber.authing.service.ActivityLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class ActivityLogServiceImpl implements ActivityLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityLogServiceImpl.class);

    @Autowired
    ActivityLogMapper activityLogMapper;


    @Override
    @Transactional(readOnly = false)
    public Integer save(ActivityLog activityLog) {

        if (null == activityLog) {
            LOGGER.warn("save activityLog, but activityLog is null...");
            return 0;
        }

        return activityLogMapper.insert(activityLog);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(ActivityLog activityLog) {

        if (null == activityLog) {
            LOGGER.warn("delete activityLog, but activityLog is null  or activityLog id is null...");
            return 0;
        }

        return activityLogMapper.deleteById(activityLog);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(ActivityLog activityLog) {

        if (null == activityLog) {
            LOGGER.warn("update activityLog, but activityLog is null  or activityLog id is null...");
            return 0;
        }

        return activityLogMapper.updateById(activityLog);
    }

    @Override
    public ActivityLog selectOne(ActivityLog activityLog) {
        if (activityLog == null) {
            LOGGER.warn("select activityLog one, but activityLog is null ...");
            return null;
        }
        activityLog = activityLogMapper.selectOne(activityLog);
        return activityLog;
    }
//
//
//    @Override
//    public PagingData<ActivityLog> selectPage(ActivityLog activityLog) {
//        PagingData<ActivityLog> pagingData = new PagingData<ActivityLog>();
//
//        if( null == activityLog ) {
//            LOGGER.warn("select activityLog page, but activityLog is null...");
//            return pagingData;
//        }
//
//        Integer queryCount = activityLogMapper.selectByIndexCount( activityLog );
//        pagingData.setTotal( queryCount );
//
//        if( null != queryCount && queryCount <= 0 ) {
//            LOGGER.info("select activityLog page , but count {} == 0 ...",queryCount);
//            return pagingData;
//        }
//
//        List<ActivityLog> activityLogs =  selectByIndex( activityLog );
//        pagingData.setRows(activityLogs);
//        return pagingData;
//    }
//
//    @Override
//    public List<ActivityLog> selectByIndex(ActivityLog activityLog) {
//        List<ActivityLog> activityLogs = new ArrayList<ActivityLog>();
//        if( activityLog == null) {
//            LOGGER.warn("select activityLog by index, but activityLog is null ...");
//            return activityLogs;
//        }
//
//        activityLogs = activityLogMapper.selectByIndex( activityLog );
//
//        return activityLogs;
//    }
}
