package	com.cyber.authing.application.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.MenuMapper;
import com.cyber.authing.domain.entity.Menu;
import com.cyber.authing.application.MenuService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    @Override
    @Transactional
    public Integer save(Menu menu) {

        if( null == menu ) {
            log.warn("save menu, but menu is null...");
            return 0;
        }

        menu.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        return menuMapper.save( menu );
    }

    @Override
    @Transactional
    public Integer deleteById(Menu menu) {

        if( null == menu ) {
            log.warn("delete menu, but menu is null  or menu id is null...");
            return 0;
        }

        return menuMapper.deleteById( menu );
    }

    @Override
    @Transactional
    public Integer updateById(Menu menu) {

        if( null == menu ) {
            log.warn("update menu, but menu is null  or menu id is null...");
            return 0;
        }

        return menuMapper.updateById( menu );
    }

    @Override
    public Menu selectOne(Menu menu) {
        if( null == menu ) {
            log.warn("select menu one, but menu is null ...");
            return null;
        }
        menu = menuMapper.selectOne( menu );
        return menu;
    }


    @Override
    public PagingData<Menu> selectPage(Menu menu) {
        PagingData<Menu> PagingData = new PagingData<>();

        if( null == menu ) {
            log.warn("select menu page, but menu is null...");
            return PagingData;
        }

        Integer queryCount = menuMapper.selectByIndexCount( menu );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select menu page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<Menu> menus =  selectByIndex( menu );
        PagingData.setData( menus );
        return PagingData;
    }

    @Override
    public List<Menu> selectByIndex(Menu menu) {
        List<Menu> menus = new ArrayList<>();
        if( null == menu ) {
            log.warn("select menu by index, but menu is null ...");
            return menus;
        }

        menus = menuMapper.selectByIndex( menu );

        return menus;
    }
}
