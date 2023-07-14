package com.cyber.authing.domain.request;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import com.cyber.domain.entity.OperateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.cyber.authing.domain.entity.Product;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UpdateProductRequest extends OperateEntity {

	

	public Product toEvent(String userCode,String tenantCode) {
		Product product = new Product();
		BeanUtils.copyProperties(this, product);

        product.setTenantCode(tenantCode);
        product.setUpdator(userCode);
        product.setUpdateTime(new Date());
		return product;
	}
}