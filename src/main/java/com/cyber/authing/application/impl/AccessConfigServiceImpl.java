package	com.cyber.authing.application.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.AccessConfigMapper;
import com.cyber.authing.domain.entity.AccessConfig;
import com.cyber.authing.application.AccessConfigService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccessConfigServiceImpl implements AccessConfigService {

    private final AccessConfigMapper accessConfigMapper;

    @Override
    @Transactional
    public Integer save(AccessConfig accessConfig) {

        if( null == accessConfig ) {
            log.warn("save accessConfig, but accessConfig is null...");
            return 0;
        }
        accessConfig.setId(String.valueOf(IdUtil.getSnowflakeNextId()));

        return accessConfigMapper.save( accessConfig );
    }

    @Override
    @Transactional
    public Integer deleteById(AccessConfig accessConfig) {

        if( null == accessConfig ) {
            log.warn("delete accessConfig, but accessConfig is null  or accessConfig id is null...");
            return 0;
        }

        return accessConfigMapper.deleteById( accessConfig );
    }

    @Override
    @Transactional
    public Integer updateById(AccessConfig accessConfig) {

        if( null == accessConfig ) {
            log.warn("update accessConfig, but accessConfig is null  or accessConfig id is null...");
            return 0;
        }

        return accessConfigMapper.updateById( accessConfig );
    }

    @Override
    public AccessConfig selectOne(AccessConfig accessConfig) {
        if( null == accessConfig ) {
            log.warn("select accessConfig one, but accessConfig is null ...");
            return null;
        }
        accessConfig = accessConfigMapper.selectOne( accessConfig );
        return accessConfig;
    }


    @Override
    public PagingData<AccessConfig> selectPage(AccessConfig accessConfig) {
        PagingData<AccessConfig> PagingData = new PagingData<>();

        if( null == accessConfig ) {
            log.warn("select accessConfig page, but accessConfig is null...");
            return PagingData;
        }

        Integer queryCount = accessConfigMapper.selectByIndexCount( accessConfig );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select accessConfig page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<AccessConfig> accessConfigs =  selectByIndex( accessConfig );
        PagingData.setData( accessConfigs );
        return PagingData;
    }

    @Override
    public List<AccessConfig> selectByIndex(AccessConfig accessConfig) {
        List<AccessConfig> accessConfigs = new ArrayList<>();
        if( null == accessConfig ) {
            log.warn("select accessConfig by index, but accessConfig is null ...");
            return accessConfigs;
        }

        accessConfigs = accessConfigMapper.selectByIndex( accessConfig );

        return accessConfigs;
    }
}
