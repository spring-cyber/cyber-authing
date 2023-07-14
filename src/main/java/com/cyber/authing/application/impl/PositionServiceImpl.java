package	com.cyber.authing.application.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.cyber.authing.domain.response.CountStatus;
import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.PositionMapper;
import com.cyber.authing.domain.entity.Position;
import com.cyber.authing.application.PositionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PositionServiceImpl implements PositionService {

    private final PositionMapper positionMapper;

    @Override
    @Transactional
    public Integer save(Position position) {

        if( null == position ) {
            log.warn("save position, but position is null...");
            return 0;
        }

        position.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        return positionMapper.save( position );
    }

    @Override
    @Transactional
    public Integer deleteById(Position position) {

        if( null == position ) {
            log.warn("delete position, but position is null  or position id is null...");
            return 0;
        }

        return positionMapper.deleteById( position );
    }

    @Override
    @Transactional
    public Integer updateById(Position position) {

        if( null == position ) {
            log.warn("update position, but position is null  or position id is null...");
            return 0;
        }

        return positionMapper.updateById( position );
    }

    @Override
    public Position selectOne(Position position) {
        if( null == position ) {
            log.warn("select position one, but position is null ...");
            return null;
        }
        position = positionMapper.selectOne( position );
        return position;
    }


    @Override
    public PagingData<Position> selectPage(Position position) {
        PagingData<Position> PagingData = new PagingData<>();

        if( null == position ) {
            log.warn("select position page, but position is null...");
            return PagingData;
        }

        Integer queryCount = positionMapper.selectByIndexCount( position );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select position page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<Position> positions =  selectByIndex( position );
        PagingData.setData( positions );
        return PagingData;
    }

    @Override
    public List<Position> selectByIndex(Position position) {
        List<Position> positions = new ArrayList<>();
        if( null == position ) {
            log.warn("select position by index, but position is null ...");
            return positions;
        }

        positions = positionMapper.selectByIndex( position );

        return positions;
    }

    @Override
    public List<Position> selectList(Position position) {List<Position> positions = new ArrayList<>();
        if( null == position ) {
            log.warn("select position by index, but position is null ...");
            return positions;
        }

        positions = positionMapper.selectList( position );
        return positions;
    }

    @Override
    public List<CountStatus> countStatus() {
        return positionMapper.countStatus();
    }
}
