package com.cyber.authing.controller;

import cn.hutool.core.lang.Assert;
import com.cyber.authing.common.util.AuthenticationUtil;
import com.cyber.authing.entity.domain.DeptUser;
import com.cyber.authing.entity.domain.Product;
import com.cyber.authing.entity.domain.ProductOrder;
import com.cyber.authing.entity.request.*;
import com.cyber.authing.service.ProductService;
import com.cyber.domain.constant.HttpResultCode;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.Valid;
import java.util.Date;

@RestController
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/product/search")
    public Response searchProduct(@Valid ProductRequest request) {
        Product product = request.toEvent(request.getTenantCode());
        return productService.selectPage(product);
    }

    @GetMapping("/product")
    public Response selectOneProduct(@Valid IdRequest idRequest) {
        DataResponse<Product> response = new DataResponse<Product>();
        Product product = new Product();
        product.setId(idRequest.getId());
        product.setTenantCode(idRequest.getTenantCode());
        product = productService.selectOne(product);
        response.setData(product);
        return response;
    }

    @PostMapping("/product")
    public Response saveProduct(@RequestBody @Valid CreateProductRequest request) {
        if (StringUtils.isBlank(request.getTenantCode())) {
            return Response.fail(HttpResultCode.PARAM_ERROR);
        }
        Assert.isNull(request.getTenantCode());
        Product product = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = productService.save(product);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @PutMapping("/product")
    public Response updateProduct(@RequestBody @Valid UpdateProductRequest request) {
        Product product = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = productService.updateById(product);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    @DeleteMapping("/product")
    public Response deleteProduct(@Valid IdRequest idRequest) {
        Product product = new Product();
        product.setId(idRequest.getId());
        product.setTenantCode(idRequest.getTenantCode());
        product.setUpdator(AuthenticationUtil.getUserCode());
        product.setUpdateTime(new Date());
        int result = productService.deleteById(product);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }

    /**
     * 查询产品订阅关系
     *
     * @param request
     * @return
     */
    @GetMapping("/productorder/search")
    public Response searchProductOrder(@Valid ProductOrderRequest request) {
        ProductOrder productorder = request.toEvent(request.getTenantCode());
        return productService.selectOrderPage(productorder);
    }

    @GetMapping("/productorder")
    public Response selectOneProductOrder(@Valid IdRequest idRequest) {
        DataResponse<ProductOrder> response = new DataResponse<ProductOrder>();
        ProductOrder productOrder = new ProductOrder();
        productOrder.setId(idRequest.getId());
        productOrder.setTenantCode(idRequest.getTenantCode());
        productOrder = productService.selectOneOrder(productOrder);
        response.setData(productOrder);
        return response;
    }

    @PostMapping("/productorder")
    public Response saveProductOrder(@RequestBody @Valid CreateProductOrderRequest request) {
        if (StringUtils.isBlank(request.getTenantCode())) {
            return Response.fail(HttpResultCode.PARAM_ERROR);
        }
        Assert.isNull(request.getTenantCode());
        ProductOrder productorder = request.toEvent(AuthenticationUtil.getUserCode(), request.getTenantCode());
        int result = productService.saveOrder(productorder);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }


    @DeleteMapping("/productorder")
    public Response deleteProductOrder(@Valid IdRequest requestId) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setId(requestId.getId());
        productOrder.setTenantCode(requestId.getTenantCode());
        productOrder.setUpdator(AuthenticationUtil.getUserCode());
        productOrder.setUpdateTime(new Date());
        int result = productService.deleteOrderById(productOrder);
        if (result < 1) {
            return Response.fail(HttpResultCode.SERVER_ERROR);
        }
        return Response.success();
    }
}
