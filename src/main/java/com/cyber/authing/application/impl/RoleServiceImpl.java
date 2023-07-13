package	com.cyber.authing.application.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.RoleMapper;
import com.cyber.authing.domain.entity.Role;
import com.cyber.authing.application.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public Integer save(Role role) {

        if( null == role ) {
            log.warn("save role, but role is null...");
            return 0;
        }

        role.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        return roleMapper.save( role );
    }

    @Override
    @Transactional
    public Integer deleteById(Role role) {

        if( null == role ) {
            log.warn("delete role, but role is null  or role id is null...");
            return 0;
        }

        return roleMapper.deleteById( role );
    }

    @Override
    @Transactional
    public Integer updateById(Role role) {

        if( null == role ) {
            log.warn("update role, but role is null  or role id is null...");
            return 0;
        }

        return roleMapper.updateById( role );
    }

    @Override
    public Role selectOne(Role role) {
        if( null == role ) {
            log.warn("select role one, but role is null ...");
            return null;
        }
        role = roleMapper.selectOne( role );
        return role;
    }


    @Override
    public PagingData<Role> selectPage(Role role) {
        PagingData<Role> PagingData = new PagingData<>();

        if( null == role ) {
            log.warn("select role page, but role is null...");
            return PagingData;
        }

        Integer queryCount = roleMapper.selectByIndexCount( role );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select role page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<Role> roles =  selectByIndex( role );
        PagingData.setData( roles );
        return PagingData;
    }

    @Override
    public List<Role> selectByIndex(Role role) {
        List<Role> roles = new ArrayList<>();
        if( null == role ) {
            log.warn("select role by index, but role is null ...");
            return roles;
        }

        roles = roleMapper.selectByIndex( role );

        return roles;
    }
}
