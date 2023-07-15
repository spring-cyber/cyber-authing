package com.cyber.authing.application.service;

import cn.hutool.core.lang.tree.Tree;
import com.cyber.application.service.BaseService;
import com.cyber.authing.domain.entity.Menu;

import java.util.List;

public interface MenuService extends BaseService<Menu> {


    List<Tree<String>> selectTree(Menu menu);
}
