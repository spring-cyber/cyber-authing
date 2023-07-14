package com.cyber.authing.presentation.rest;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;


import cn.hutool.core.lang.tree.Tree;
import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.domain.request.UpdateProductRequest;
import com.cyber.authing.domain.response.CountStatus;
import com.cyber.domain.constant.HttpResultCode;
import org.springframework.web.bind.annotation.*;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;

import lombok.RequiredArgsConstructor;

import com.cyber.authing.domain.entity.Product;
import com.cyber.authing.domain.request.ProductRequest;
import com.cyber.authing.domain.request.CreateProductRequest;

import com.cyber.authing.application.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductRest extends AuthingTokenController{

	private final ProductService productService;

	@GetMapping("/product/search")
	public Response searchProduct(@Valid ProductRequest request) {
		DataResponse<PagingData<Product>> response = new DataResponse<>();
        Product  product = request.toEvent(request.getTenantCode());
		PagingData<Product> productPage = productService.selectPage(product);
		response.setData(productPage);
		return response;
	}

	@GetMapping("/product/status/count")
	public Response selectProductStatusCount() {
		DataResponse<List<CountStatus>> response = new DataResponse<>();
		List<CountStatus> countStatus = productService.countStatus();
		response.setData(countStatus);
		return response;
	}

	@GetMapping("/product/tree")
	public Response selectProduct(@Valid ProductRequest request) {
		DataResponse<List<Tree<String>>> response = new DataResponse<>();
		Product product = request.toEvent(request.getTenantCode());
		List<Tree<String>> productTree = productService.selectTree(product);
		response.setData(productTree);
		return response;
	}


	@GetMapping("/product")
	public Response selectOneProduct(@Valid IdRequest idRequest) {
		DataResponse<Product> response = new DataResponse<>();

		Product product = new Product();
		product.setId(idRequest.getId());
        product.setTenantCode(idRequest.getTenantCode());
		product = productService.selectOne(product);

		response.setData(product);
		return response;
	}

	@PostMapping("/product")
	public Response saveProduct(@RequestBody @Valid CreateProductRequest request) {
	    Product  product = request.toEvent(getSessionId(),request.getTenantCode());

		int result = productService.save(product);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/product")
	public Response updateProduct(@RequestBody @Valid UpdateProductRequest request) {
	    Product  product = request.toEvent(getSessionId(),request.getTenantCode());
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
		product.setUpdator(getSessionId());
        product.setUpdateTime(new Date());

		int result = productService.deleteById(product);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
