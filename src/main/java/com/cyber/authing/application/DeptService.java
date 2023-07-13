package com.cyber.authing.application;

import cn.hutool.core.lang.tree.Tree;
import com.cyber.application.service.BaseService;

import com.cyber.authing.domain.entity.Dept;

import java.util.List;

public interface DeptService extends BaseService<Dept> {

    /**
     * 查询企业下部门树
     * @param dept
     * @return
     */
    List<Tree<String>> selectTree(Dept dept);

    /**
     * 查询企业部门树
     * @param dept
     * @return
     */
    List<Tree<String>> selectEnterpriseTree(Dept dept);
}
