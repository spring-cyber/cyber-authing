package com.cyber.authing.entity.request;

import com.cyber.authing.entity.domain.ProductOrder;
import com.cyber.domain.entity.PagingRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductOrderRequest extends PagingRequest {
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 项目ID
     */
    private Long projectId;

    public ProductOrder toEvent(String tenantCode) {
        ProductOrder productOrder = new ProductOrder();
        BeanUtils.copyProperties(this, productOrder);
        productOrder.setTenantCode(tenantCode);
        return productOrder;
    }
}