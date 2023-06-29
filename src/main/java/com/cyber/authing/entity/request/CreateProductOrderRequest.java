package com.cyber.authing.entity.request;

import com.cyber.authing.entity.domain.ProductOrder;
import com.cyber.domain.entity.OperateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateProductOrderRequest extends OperateEntity {

    /**
     * 产品ID
     */
    @NotNull
    private Long productId;
    /**
     * 项目ID
     */
    @NotNull
    private Long projectId;

    public ProductOrder toEvent(String userCode, String tenantCode) {
        ProductOrder productOrder = new ProductOrder();
        BeanUtils.copyProperties(this, productOrder);
        productOrder.setTenantCode(tenantCode);
        productOrder.setCreator(userCode);
        productOrder.setCreateTime(new Date());
        productOrder.setUpdator(userCode);
        productOrder.setUpdateTime(new Date());
        return productOrder;
    }
}