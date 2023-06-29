package com.cyber.authing.service;

import com.cyber.authing.entity.domain.Product;
import com.cyber.authing.entity.domain.ProductOrder;
import com.cyber.domain.entity.PagingResponse;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    Integer saveOrder(ProductOrder productOrder);

    Integer deleteOrderById(ProductOrder productOrder);

    ProductOrder selectOneOrder(ProductOrder productOrder);

    PagingResponse<ProductOrder> selectOrderPage(ProductOrder productOrder);

    List<ProductOrder> selectOrderByIndex(ProductOrder productOrder);
}
