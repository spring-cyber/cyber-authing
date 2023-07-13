package com.cyber.authing.application;

import com.cyber.application.service.BaseService;

import com.cyber.authing.domain.entity.Enterprise;

import java.util.List;

public interface EnterpriseService extends BaseService<Enterprise> {


    /**
     * 企业下拉框
     * @param enterprise
     * @return
     */
    List<Enterprise> selectList(Enterprise enterprise);
}
