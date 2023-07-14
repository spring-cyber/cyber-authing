package com.cyber.authing.application;

import com.cyber.application.service.BaseService;

import com.cyber.authing.domain.entity.Product;
import com.cyber.authing.domain.response.CountStatus;

import java.util.List;

public interface ProductService extends BaseService<Product> {


    List<CountStatus> countStatus();

}
