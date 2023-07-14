package com.cyber.authing.application.service;

import cn.hutool.core.lang.tree.Tree;
import com.cyber.application.service.BaseService;

import com.cyber.authing.domain.entity.Enterprise;
import com.cyber.authing.domain.response.CountStatus;

import java.util.List;

public interface EnterpriseService extends BaseService<Enterprise> {


    /**
     * 企业下拉框
     * @param enterprise
     * @return
     */
    List<Enterprise> selectList(Enterprise enterprise);

    /**
     * 企业组织树
     * @param enterprise
     * @return
     */
    List<Tree<String>> selectEnterpriseUserTree(Enterprise enterprise);

    /**
     * 统计状态
     * @return
     */
    List<CountStatus> countStatus();

}
