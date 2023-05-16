package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.Product;
import com.cyber.authing.mapper.ProductMapper;
import com.cyber.authing.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger( ProductServiceImpl.class );

    @Autowired
    ProductMapper productMapper;
    
    
    @Override
    @Transactional(readOnly = false)
    public Integer save(Product product) {

        if( null == product) {
            LOGGER.warn("save product, but product is null...");
            return 0;
        }

        return productMapper.insert(product);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(Product product) {

        if( null == product) {
            LOGGER.warn("delete product, but product is null  or product id is null...");
            return 0;
        }

        return productMapper.deleteById(product);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(Product product) {

        if( null == product) {
            LOGGER.warn("update product, but product is null  or product id is null...");
            return 0;
        }

        return productMapper.updateById(product);
    }

    @Override
    public Product selectOne(Product product) {
        if( product == null) {
            LOGGER.warn("select product one, but product is null ...");
            return null;
        }
        product = productMapper.selectOne( product );
        return product;
    }


//    @Override
//    public PagingData<Product> selectPage(Product product) {
//        PagingData<Product> pagingData = new PagingData<Product>();
//
//        if( null == product ) {
//            LOGGER.warn("select product page, but product is null...");
//            return pagingData;
//        }
//
//        Integer queryCount = productMapper.selectByIndexCount( product );
//        pagingData.setTotal( queryCount );
//
//        if( null != queryCount && queryCount <= 0 ) {
//            LOGGER.info("select product page , but count {} == 0 ...",queryCount);
//            return pagingData;
//        }
//
//        List<Product> products =  selectByIndex( product );
//        pagingData.setRows(products);
//        return pagingData;
//    }
//
//    @Override
//    public List<Product> selectByIndex(Product product) {
//        List<Product> products = new ArrayList<Product>();
//        if( product == null) {
//            LOGGER.warn("select product by index, but product is null ...");
//            return products;
//        }
//
//        products = productMapper.selectByIndex( product );
//
//        return products;
//    }
}
