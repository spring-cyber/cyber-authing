package	com.cyber.authing.application.impl;

import java.util.ArrayList;
import java.util.List;

import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.UserPositionMapper;
import com.cyber.authing.domain.entity.UserPosition;
import com.cyber.authing.application.UserPositionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserPositionServiceImpl implements UserPositionService {

    private final UserPositionMapper userPositionMapper;

    @Override
    @Transactional
    public Integer save(UserPosition userPosition) {

        if( null == userPosition ) {
            log.warn("save userPosition, but userPosition is null...");
            return 0;
        }

        return userPositionMapper.save( userPosition );
    }

    @Override
    @Transactional
    public Integer deleteById(UserPosition userPosition) {

        if( null == userPosition ) {
            log.warn("delete userPosition, but userPosition is null  or userPosition id is null...");
            return 0;
        }

        return userPositionMapper.deleteById( userPosition );
    }

    @Override
    @Transactional
    public Integer updateById(UserPosition userPosition) {

        if( null == userPosition ) {
            log.warn("update userPosition, but userPosition is null  or userPosition id is null...");
            return 0;
        }

        return userPositionMapper.updateById( userPosition );
    }

    @Override
    public UserPosition selectOne(UserPosition userPosition) {
        if( null == userPosition ) {
            log.warn("select userPosition one, but userPosition is null ...");
            return null;
        }
        userPosition = userPositionMapper.selectOne( userPosition );
        return userPosition;
    }


    @Override
    public PagingData<UserPosition> selectPage(UserPosition userPosition) {
        PagingData<UserPosition> PagingData = new PagingData<>();

        if( null == userPosition ) {
            log.warn("select userPosition page, but userPosition is null...");
            return PagingData;
        }

        Integer queryCount = userPositionMapper.selectByIndexCount( userPosition );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select userPosition page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<UserPosition> userPositions =  selectByIndex( userPosition );
        PagingData.setData( userPositions );
        return PagingData;
    }

    @Override
    public List<UserPosition> selectByIndex(UserPosition userPosition) {
        List<UserPosition> userPositions = new ArrayList<>();
        if( null == userPosition ) {
            log.warn("select userPosition by index, but userPosition is null ...");
            return userPositions;
        }

        userPositions = userPositionMapper.selectByIndex( userPosition );

        return userPositions;
    }

    @Override
    public Integer saveBatch(List<UserPosition> list) {
        if( null == list || list.size()==0 ) {
            log.warn("saveBatch userPosition by list, but list is null or list.size() == 0 ...");
            return 0;
        }
        return userPositionMapper.saveBatch(list);
    }

    @Override
    public List<UserPosition> selectUserPosition(List<String> userIds) {

        return userPositionMapper.selectUserPosition(userIds);
    }

    @Override
    public Integer deleteByUserId(long userId) {
        return userPositionMapper.deleteByUserId(userId);
    }
}
