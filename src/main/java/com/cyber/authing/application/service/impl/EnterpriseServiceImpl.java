package com.cyber.authing.application.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.IdUtil;
import com.cyber.authing.application.service.EnterpriseService;
import com.cyber.authing.application.service.UserDeptService;
import com.cyber.authing.application.service.UserService;
import com.cyber.authing.domain.entity.Dept;
import com.cyber.authing.domain.entity.Enterprise;
import com.cyber.authing.domain.entity.User;
import com.cyber.authing.domain.entity.UserDept;
import com.cyber.authing.domain.repository.DeptMapper;
import com.cyber.authing.domain.repository.EnterpriseMapper;
import com.cyber.authing.domain.response.CountStatus;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.SortingField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseMapper enterpriseMapper;

    private final DeptMapper deptMapper;

    private final UserService userService;

    private final UserDeptService userDeptService;

    @Override
    @Transactional
    public Integer save(Enterprise enterprise) {

        if (null == enterprise) {
            log.warn("save enterprise, but enterprise is null...");
            return 0;
        }

        enterprise.setId(IdUtil.simpleUUID());
        return enterpriseMapper.save(enterprise);
    }

    @Override
    @Transactional
    public Integer deleteById(Enterprise enterprise) {

        if (null == enterprise) {
            log.warn("delete enterprise, but enterprise is null  or enterprise id is null...");
            return 0;
        }

        return enterpriseMapper.deleteById(enterprise);
    }

    @Override
    @Transactional
    public Integer updateById(Enterprise enterprise) {

        if (null == enterprise) {
            log.warn("update enterprise, but enterprise is null  or enterprise id is null...");
            return 0;
        }

        return enterpriseMapper.updateById(enterprise);
    }

    @Override
    public Enterprise selectOne(Enterprise enterprise) {
        if (null == enterprise) {
            log.warn("select enterprise one, but enterprise is null ...");
            return null;
        }
        enterprise = enterpriseMapper.selectOne(enterprise);
        return enterprise;
    }


    @Override
    public PagingData<Enterprise> selectPage(Enterprise enterprise) {
        PagingData<Enterprise> PagingData = new PagingData<>();

        if (null == enterprise) {
            log.warn("select enterprise page, but enterprise is null...");
            return PagingData;
        }

        Integer queryCount = enterpriseMapper.selectByIndexCount(enterprise);
        PagingData.setRow(queryCount);

        if (queryCount <= 0) {
            log.info("select enterprise page , but count {} == 0 ...", queryCount);
            return PagingData;
        }

        List<Enterprise> enterprises = selectByIndex(enterprise);
        PagingData.setData(enterprises);
        return PagingData;
    }

    @Override
    public List<Enterprise> selectByIndex(Enterprise enterprise) {
        List<Enterprise> enterprises = new ArrayList<>();
        if (null == enterprise) {
            log.warn("select enterprise by index, but enterprise is null ...");
            return enterprises;
        }
        enterprise.setSortBy("order_num");
        enterprise.setSortType(SortingField.ORDER_ASC);
        enterprises = enterpriseMapper.selectByIndex(enterprise);

        return enterprises;
    }

    @Override
    public List<Enterprise> selectList(Enterprise enterprise) {
        List<Enterprise> enterprises = new ArrayList<>();
        if (null == enterprise) {
            log.warn("select enterprise by index, but enterprise is null ...");
            return enterprises;
        }
        enterprises = enterpriseMapper.selectList(enterprise);
        return enterprises;
    }

    @Override
    public List<Tree<String>> selectEnterpriseUserTree(Enterprise enterprise) {
        List<Enterprise> enterprises = this.selectList(new Enterprise());
        //组织树 企业
        List<TreeNode<String>> collect = enterprises.stream()
                .sorted(Comparator.comparingInt(Enterprise::getOrderNum)).map(enterpriseItem -> {
                    TreeNode<String> treeNode = new TreeNode<>();
                    treeNode.setId(enterpriseItem.getId());
                    treeNode.setParentId("0");
                    treeNode.setName(enterpriseItem.getName());
                    treeNode.setWeight(enterpriseItem.getOrderNum());
                    treeNode.setExtra(new HashMap<String, Object>() {{
                        put("type", "enterprise");
                    }});
                    return treeNode;
                }).collect(Collectors.toList());

        List<Dept> deptAllList = deptMapper.selectList(new Dept());
        //组织树 部门
        collect.addAll(deptAllList.stream()
                .sorted(Comparator.comparingInt(Dept::getOrderNum)).map(deptMap -> {
                    TreeNode<String> treeNode = new TreeNode<>();
                    String parenId = String.valueOf(deptMap.getParentId());
                    treeNode.setId(deptMap.getId());
                    treeNode.setParentId(parenId.equals("0") ? String.valueOf(deptMap.getEnterpriseId()) : parenId);
                    treeNode.setName(deptMap.getName());
                    treeNode.setWeight(deptMap.getOrderNum());
                    treeNode.setExtra(new HashMap<String, Object>() {{
                        put("type", "dept");
                    }});
                    return treeNode;
                }).collect(Collectors.toList()));

        List<User> userList = userService.selectList(new User());
        Map<String, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, user -> user));

        List<UserDept> userDeptList = userDeptService.selectList(new UserDept());

        HashSet<String> extUserId = new HashSet<>();

        //组织树 用户（含有部门）
        collect.addAll(userDeptList.stream()
                .map(userDept -> {
                    TreeNode<String> treeNode = new TreeNode<>();
                    String parenId = String.valueOf(userDept.getDeptId());
                    treeNode.setId(String.valueOf(userDept.getUserId()));
                    treeNode.setParentId(parenId);
                    User user = userMap.get(String.valueOf(userDept.getUserId()));
                    treeNode.setName(user.getName());
                    treeNode.setExtra(new HashMap<String, Object>() {{
                        put("type", "user");
                    }});
                    extUserId.add(String.valueOf(userDept.getUserId()));
                    return treeNode;
                }).collect(Collectors.toList()));

        //组织树 用户（直属企业）
        if (!userMap.isEmpty()) {
            userMap.forEach((userId, user) -> {
                if (!extUserId.contains(userId)){
                    TreeNode<String> treeNode = new TreeNode<>();
                    String parenId = String.valueOf(user.getEnterpriseId());
                    treeNode.setId(userId);
                    treeNode.setParentId(parenId);
                    treeNode.setName(user.getName());
                    treeNode.setExtra(new HashMap<String, Object>() {{
                        put("type", "user");
                    }});
                    collect.add(treeNode);
                }
            });
        }

        return TreeUtil.build(collect, "0");

    }

    @Override
    public List<CountStatus> countStatus() {
        return enterpriseMapper.countStatus();
    }
}
