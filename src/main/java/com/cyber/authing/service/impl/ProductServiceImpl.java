package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.Product;
import com.cyber.authing.entity.domain.ProductOrder;
import com.cyber.authing.mapper.ProductMapper;
import com.cyber.authing.mapper.ProductOrderMapper;
import com.cyber.authing.service.ProductService;
import com.cyber.domain.entity.PagingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductOrderMapper productOrderMapper;

    @Override
    @Transactional(readOnly = false)
    public Integer save(Product product) {

        if (null == product) {
            LOGGER.warn("save product, but product is null...");
            return 0;
        }

        return productMapper.save(product);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(Product product) {

        if (null == product) {
            LOGGER.warn("delete product, but product is null  or product id is null...");
            return 0;
        }

        return productMapper.deleteById(product);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(Product product) {

        if (null == product) {
            LOGGER.warn("update product, but product is null  or product id is null...");
            return 0;
        }

        return productMapper.updateById(product);
    }

    @Override
    public Product selectOne(Product product) {
        if (product == null) {
            LOGGER.warn("select product one, but product is null ...");
            return null;
        }
        product = productMapper.selectOne(product);
        return product;
    }


    @Override
    public PagingResponse<Product> selectPage(Product product) {
        PagingResponse<Product> pagingData = new PagingResponse<Product>();

        if (null == product) {
            LOGGER.warn("select product page, but product is null...");
            return pagingData;
        }

        Integer queryCount = productMapper.selectByIndexCount(product);
        pagingData.setRow(queryCount);

        if (queryCount <= 0) {
            LOGGER.info("select product page , but count {} == 0 ...", queryCount);
            return pagingData;
        }
        pagingData.setData(selectByIndex(product));
        return pagingData;
    }

    @Override
    public List<Product> selectByIndex(Product product) {
        List<Product> products = new ArrayList<Product>();
        if (product == null) {
            LOGGER.warn("select product by index, but product is null ...");
            return products;
        }

        products = productMapper.selectByIndex(product);
        return products;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer saveOrder(ProductOrder productOrder) {
        if (null == productOrder) {
            LOGGER.warn("save productOrder, but productOrder is null...");
            return 0;
        }
        return productOrderMapper.save(productOrder);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteOrderById(ProductOrder productOrder) {

        if (null == productOrder) {
            LOGGER.warn("delete productOrder, but productOrder is null  or productOrder id is null...");
            return 0;
        }

        return productOrderMapper.deleteById(productOrder);
    }

    @Override
    public ProductOrder selectOneOrder(ProductOrder productOrder) {
        if (productOrder == null) {
            LOGGER.warn("select productOrder one, but productOrder is null ...");
            return null;
        }
        productOrder = productOrderMapper.selectOne(productOrder);
        return productOrder;
    }


    @Override
    public PagingResponse<ProductOrder> selectOrderPage(ProductOrder productOrder) {
        PagingResponse<ProductOrder> pagingData = new PagingResponse<>();

        if (null == productOrder) {
            LOGGER.warn("select productOrder page, but productOrder is null...");
            return pagingData;
        }

        Integer queryCount = productOrderMapper.selectByIndexCount(productOrder);
        pagingData.setRow(queryCount);

        if (queryCount <= 0) {
            LOGGER.info("select productOrder page , but count {} == 0 ...", queryCount);
            return pagingData;
        }
        pagingData.setData(selectOrderByIndex(productOrder));
        return pagingData;
    }

    @Override
    public List<ProductOrder> selectOrderByIndex(ProductOrder productOrder) {
        List<ProductOrder> productOrders = new ArrayList<>();
        if (productOrder == null) {
            LOGGER.warn("select productOrder by index, but productOrder is null ...");
            return productOrders;
        }
        return productOrderMapper.selectByIndex(productOrder);
    }
}
