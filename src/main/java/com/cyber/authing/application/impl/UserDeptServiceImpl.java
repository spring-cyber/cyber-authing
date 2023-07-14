package	com.cyber.authing.application.impl;

import java.util.ArrayList;
import java.util.List;

import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.UserDeptMapper;
import com.cyber.authing.domain.entity.UserDept;
import com.cyber.authing.application.UserDeptService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDeptServiceImpl implements UserDeptService {

    private final UserDeptMapper userDeptMapper;

    @Override
    @Transactional
    public Integer save(UserDept userDept) {

        if( null == userDept ) {
            log.warn("save userDept, but userDept is null...");
            return 0;
        }

        return userDeptMapper.save( userDept );
    }

    @Override
    @Transactional
    public Integer deleteById(UserDept userDept) {

        if( null == userDept ) {
            log.warn("delete userDept, but userDept is null  or userDept id is null...");
            return 0;
        }

        return userDeptMapper.deleteById( userDept );
    }

    @Override
    @Transactional
    public Integer updateById(UserDept userDept) {

        if( null == userDept ) {
            log.warn("update userDept, but userDept is null  or userDept id is null...");
            return 0;
        }

        return userDeptMapper.updateById( userDept );
    }

    @Override
    public UserDept selectOne(UserDept userDept) {
        if( null == userDept ) {
            log.warn("select userDept one, but userDept is null ...");
            return null;
        }
        userDept = userDeptMapper.selectOne( userDept );
        return userDept;
    }


    @Override
    public PagingData<UserDept> selectPage(UserDept userDept) {
        PagingData<UserDept> PagingData = new PagingData<>();

        if( null == userDept ) {
            log.warn("select userDept page, but userDept is null...");
            return PagingData;
        }

        Integer queryCount = userDeptMapper.selectByIndexCount( userDept );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select userDept page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<UserDept> userDepts =  selectByIndex( userDept );
        PagingData.setData( userDepts );
        return PagingData;
    }

    @Override
    public List<UserDept> selectByIndex(UserDept userDept) {
        List<UserDept> userDepts = new ArrayList<>();
        if( null == userDept ) {
            log.warn("select userDept by index, but userDept is null ...");
            return userDepts;
        }

        userDepts = userDeptMapper.selectByIndex( userDept );

        return userDepts;
    }

    @Override
    public List<UserDept> selectUserDept(List<String> userIds) {
        return userDeptMapper.selectUserDept(userIds);
    }

    @Override
    public Integer saveBatch(List<UserDept> userDepts) {
        if( null == userDepts || userDepts.size()==0 ) {
            log.warn("saveBatch userDepts by list, but list is null or list.size() == 0 ...");
            return 0;
        }
        return userDeptMapper.saveBatch(userDepts);
    }

    @Override
    public List<UserDept> selectList(UserDept userDept) {
        List<UserDept> userDepts = new ArrayList<>();
        if( null == userDept ) {
            log.warn("select userDept by index, but userDept is null ...");
            return userDepts;
        }

        userDepts = userDeptMapper.selectList( userDept );

        return userDepts;
    }

    @Override
    public Integer deleteByUserId(long userId) {
        return userDeptMapper.deleteByUserId(userId);
    }
}
