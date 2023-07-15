package com.cyber.authing.application.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cyber.authing.application.service.ActivityLogService;
import com.cyber.authing.domain.entity.ActivityLog;
import com.cyber.authing.domain.repository.ActivityLogMapper;
import com.cyber.domain.entity.PagingData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogMapper activityLogMapper;

    @Override
    @Transactional
    public Integer save(ActivityLog activityLog) {

        if( null == activityLog ) {
            log.warn("save activityLog, but activityLog is null...");
            return 0;
        }

        activityLog.setId(IdUtil.simpleUUID());
        return activityLogMapper.save( activityLog );
    }

    @Override
    @Transactional
    public Integer deleteById(ActivityLog activityLog) {

        if( null == activityLog ) {
            log.warn("delete activityLog, but activityLog is null  or activityLog id is null...");
            return 0;
        }

        return activityLogMapper.deleteById( activityLog );
    }

    @Override
    @Transactional
    public Integer updateById(ActivityLog activityLog) {

        if( null == activityLog ) {
            log.warn("update activityLog, but activityLog is null  or activityLog id is null...");
            return 0;
        }

        return activityLogMapper.updateById( activityLog );
    }

    @Override
    public ActivityLog selectOne(ActivityLog activityLog) {
        if( null == activityLog ) {
            log.warn("select activityLog one, but activityLog is null ...");
            return null;
        }
        activityLog = activityLogMapper.selectOne( activityLog );
        return activityLog;
    }


    @Override
    public PagingData<ActivityLog> selectPage(ActivityLog activityLog) {
        PagingData<ActivityLog> PagingData = new PagingData<>();

        if( null == activityLog ) {
            log.warn("select activityLog page, but activityLog is null...");
            return PagingData;
        }

        Integer queryCount = activityLogMapper.selectByIndexCount( activityLog );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select activityLog page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<ActivityLog> activityLogs =  selectByIndex( activityLog );
        PagingData.setData( activityLogs );
        return PagingData;
    }

    @Override
    public List<ActivityLog> selectByIndex(ActivityLog activityLog) {
        List<ActivityLog> activityLogs = new ArrayList<>();
        if( null == activityLog ) {
            log.warn("select activityLog by index, but activityLog is null ...");
            return activityLogs;
        }

        activityLogs = activityLogMapper.selectByIndex( activityLog );

        return activityLogs;
    }
}
