package	com.cyber.authing.application.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.util.IdUtil;
import com.cyber.authing.application.UserDeptService;
import com.cyber.authing.application.UserPositionService;
import com.cyber.authing.domain.entity.UserDept;
import com.cyber.authing.domain.entity.UserPosition;
import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.UserMapper;
import com.cyber.authing.domain.entity.User;
import com.cyber.authing.application.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserPositionService userPositionService;

    private final UserDeptService userDeptService;

    @Override
    @Transactional
    public Integer save(User user) {

        if( null == user ) {
            log.warn("save user, but user is null...");
            return 0;
        }

        user.setId(String.valueOf(IdUtil.getSnowflakeNextId()));

        //构建用户部门关系
        List<UserDept> userDepts = user.getDeptIds().stream().map(deptId -> {
            UserDept userDept = new UserDept();
            userDept.setUserId(Long.valueOf(user.getId()));
            userDept.setDeptId(deptId);
            return userDept;
        }).collect(Collectors.toList());

        //构建用户岗位关系
        List<UserPosition> userPositions = user.getDeptIds().stream().map(positionId -> {
            UserPosition userDept = new UserPosition();
            userDept.setUserId(Long.valueOf(user.getId()));
            userDept.setPositionId(positionId);
            return userDept;
        }).collect(Collectors.toList());

        userPositionService.saveBatch(userPositions);
        userDeptService.saveBatch(userDepts);

        return userMapper.save( user );
    }

    @Override
    @Transactional
    public Integer deleteById(User user) {

        if( null == user ) {
            log.warn("delete user, but user is null  or user id is null...");
            return 0;
        }

        return userMapper.deleteById( user );
    }

    @Override
    @Transactional
    public Integer updateById(User user) {

        if( null == user ) {
            log.warn("update user, but user is null  or user id is null...");
            return 0;
        }

        return userMapper.updateById( user );
    }

    @Override
    public User selectOne(User user) {
        if( null == user ) {
            log.warn("select user one, but user is null ...");
            return null;
        }
        user = userMapper.selectOne( user );
        return user;
    }


    @Override
    public PagingData<User> selectPage(User user) {
        PagingData<User> PagingData = new PagingData<>();

        if( null == user ) {
            log.warn("select user page, but user is null...");
            return PagingData;
        }

        Integer queryCount = userMapper.selectByIndexCount( user );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select user page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<User> users =  selectByIndex( user );

        PagingData.setData( users );

        //查询岗位和部门信息
        List<String> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        if (userIds.isEmpty()){
            log.info("select userPosition and userDept, but userIds isEmpty ...");
            return PagingData;
        }
        List<UserPosition> positionInfo = userPositionService.selectUserPosition(userIds);
        Map<String, List<UserPosition>> userPositionCollect = positionInfo.stream().collect(Collectors.groupingBy(userPosition -> String.valueOf(userPosition.getUserId())));
        users.forEach(userItem -> userItem.setPositionInfo(userPositionCollect.getOrDefault(userItem.getId(),null)));

        List<UserDept> userDeptInfo = userDeptService.selectUserDept(userIds);
        Map<String, List<UserDept>> userDeptCollect = userDeptInfo.stream().collect(Collectors.groupingBy(userDept -> String.valueOf(userDept.getUserId())));
        users.forEach(userItem -> userItem.setUserDeptInfo(userDeptCollect.getOrDefault(userItem.getId(),null)));

        return PagingData;
    }

    @Override
    public List<User> selectByIndex(User user) {
        List<User> users = new ArrayList<>();
        if( null == user ) {
            log.warn("select user by index, but user is null ...");
            return users;
        }

        users = userMapper.selectByIndex( user );

        return users;
    }
}
