package com.cyber.authing.application.impl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.cyber.authing.application.EnterpriseService;
import com.cyber.authing.domain.entity.Enterprise;
import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.DeptMapper;
import com.cyber.authing.domain.entity.Dept;
import com.cyber.authing.application.DeptService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeptServiceImpl implements DeptService {

    private final DeptMapper deptMapper;

    private final EnterpriseService enterpriseService;

    @Override
    @Transactional
    public Integer save(Dept dept) {

        if (null == dept) {
            log.warn("save dept, but dept is null...");
            return 0;
        }
        dept.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        return deptMapper.save(dept);
    }

    @Override
    @Transactional
    public Integer deleteById(Dept dept) {

        if (null == dept) {
            log.warn("delete dept, but dept is null  or dept id is null...");
            return 0;
        }

        return deptMapper.deleteById(dept);
    }

    @Override
    @Transactional
    public Integer updateById(Dept dept) {

        if (null == dept) {
            log.warn("update dept, but dept is null  or dept id is null...");
            return 0;
        }

        return deptMapper.updateById(dept);
    }

    @Override
    public Dept selectOne(Dept dept) {
        if (null == dept) {
            log.warn("select dept one, but dept is null ...");
            return null;
        }
        dept = deptMapper.selectOne(dept);
        return dept;
    }


    @Override
    public PagingData<Dept> selectPage(Dept dept) {
        PagingData<Dept> PagingData = new PagingData<>();

        if (null == dept) {
            log.warn("select dept page, but dept is null...");
            return PagingData;
        }

        Integer queryCount = deptMapper.selectByIndexCount(dept);
        PagingData.setRow(queryCount);

        if (queryCount <= 0) {
            log.info("select dept page , but count {} == 0 ...", queryCount);
            return PagingData;
        }

        List<Dept> depts = selectByIndex(dept);
        PagingData.setData(depts);
        return PagingData;
    }

    @Override
    public List<Dept> selectByIndex(Dept dept) {
        List<Dept> depts = new ArrayList<>();
        if (null == dept) {
            log.warn("select dept by index, but dept is null ...");
            return depts;
        }

        depts = deptMapper.selectByIndex(dept);

        return depts;
    }

    /**
     * 查询企业部门树
     *
     * @param dept
     * @return 树 部门名称
     */
    @Override
    public List<Tree<String>> selectTree(Dept dept) {
        // 查询全部部门
        List<Dept> deptAllList = deptMapper.selectList(dept);

        List<TreeNode<String>> collect = deptAllList.stream()
                .sorted(Comparator.comparingInt(Dept::getOrderNum)).map(deptMap -> {
                    TreeNode<String> treeNode = new TreeNode<>();
                    treeNode.setId(deptMap.getId());
                    treeNode.setParentId(String.valueOf(deptMap.getParentId()));
                    treeNode.setName(deptMap.getName());
                    treeNode.setWeight(deptMap.getOrderNum());
                    return treeNode;
                }).collect(Collectors.toList());

        return TreeUtil.build(collect, "0");
    }

    @Override
    public List<Tree<String>> selectEnterpriseTree(Dept dept) {
        List<Enterprise> enterprises = enterpriseService.selectList(new Enterprise());

        List<TreeNode<String>> collect = enterprises.stream()
                .sorted(Comparator.comparingInt(Enterprise::getOrderNum)).map(enterprise -> {
                    TreeNode<String> treeNode = new TreeNode<>();
                    treeNode.setId(enterprise.getId());
                    treeNode.setParentId("0");
                    treeNode.setName(enterprise.getName());
                    treeNode.setWeight(enterprise.getOrderNum());
                    return treeNode;
                }).collect(Collectors.toList());

        List<Dept> deptAllList = deptMapper.selectList(dept);
        collect.addAll(deptAllList.stream()
                .sorted(Comparator.comparingInt(Dept::getOrderNum)).map(deptMap -> {
                    TreeNode<String> treeNode = new TreeNode<>();
                    String parenId = String.valueOf(deptMap.getParentId());
                    treeNode.setId(deptMap.getId());
                    treeNode.setParentId(parenId.equals("0") ? String.valueOf(deptMap.getEnterpriseId()) : parenId);
                    treeNode.setName(deptMap.getName());
                    treeNode.setWeight(deptMap.getOrderNum());
                    return treeNode;
                }).collect(Collectors.toList()));
        return TreeUtil.build(collect, "0");
    }
}
