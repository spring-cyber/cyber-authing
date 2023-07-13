package	com.cyber.authing.application.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.SysNoticeMapper;
import com.cyber.authing.domain.entity.SysNotice;
import com.cyber.authing.application.SysNoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SysNoticeServiceImpl implements SysNoticeService {

    private final SysNoticeMapper sysNoticeMapper;

    @Override
    @Transactional
    public Integer save(SysNotice sysNotice) {

        if( null == sysNotice ) {
            log.warn("save sysNotice, but sysNotice is null...");
            return 0;
        }

        sysNotice.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        return sysNoticeMapper.save( sysNotice );
    }

    @Override
    @Transactional
    public Integer deleteById(SysNotice sysNotice) {

        if( null == sysNotice ) {
            log.warn("delete sysNotice, but sysNotice is null  or sysNotice id is null...");
            return 0;
        }

        return sysNoticeMapper.deleteById( sysNotice );
    }

    @Override
    @Transactional
    public Integer updateById(SysNotice sysNotice) {

        if( null == sysNotice ) {
            log.warn("update sysNotice, but sysNotice is null  or sysNotice id is null...");
            return 0;
        }

        return sysNoticeMapper.updateById( sysNotice );
    }

    @Override
    public SysNotice selectOne(SysNotice sysNotice) {
        if( null == sysNotice ) {
            log.warn("select sysNotice one, but sysNotice is null ...");
            return null;
        }
        sysNotice = sysNoticeMapper.selectOne( sysNotice );
        return sysNotice;
    }


    @Override
    public PagingData<SysNotice> selectPage(SysNotice sysNotice) {
        PagingData<SysNotice> PagingData = new PagingData<>();

        if( null == sysNotice ) {
            log.warn("select sysNotice page, but sysNotice is null...");
            return PagingData;
        }

        Integer queryCount = sysNoticeMapper.selectByIndexCount( sysNotice );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select sysNotice page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<SysNotice> sysNotices =  selectByIndex( sysNotice );
        PagingData.setData( sysNotices );
        return PagingData;
    }

    @Override
    public List<SysNotice> selectByIndex(SysNotice sysNotice) {
        List<SysNotice> sysNotices = new ArrayList<>();
        if( null == sysNotice ) {
            log.warn("select sysNotice by index, but sysNotice is null ...");
            return sysNotices;
        }

        sysNotices = sysNoticeMapper.selectByIndex( sysNotice );

        return sysNotices;
    }
}
