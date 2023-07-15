package com.cyber.authing.application.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.IdUtil;
import com.cyber.authing.application.service.ProductService;
import com.cyber.authing.domain.entity.Product;
import com.cyber.authing.domain.repository.ProductMapper;
import com.cyber.authing.domain.response.CountStatus;
import com.cyber.domain.entity.PagingData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    @Transactional
    public Integer save(Product product) {

        if( null == product ) {
            log.warn("save product, but product is null...");
            return 0;
        }

        product.setId(IdUtil.simpleUUID());
        return productMapper.save( product );
    }

    @Override
    @Transactional
    public Integer deleteById(Product product) {

        if( null == product ) {
            log.warn("delete product, but product is null  or product id is null...");
            return 0;
        }

        return productMapper.deleteById( product );
    }

    @Override
    @Transactional
    public Integer updateById(Product product) {

        if( null == product ) {
            log.warn("update product, but product is null  or product id is null...");
            return 0;
        }

        return productMapper.updateById( product );
    }

    @Override
    public Product selectOne(Product product) {
        if( null == product ) {
            log.warn("select product one, but product is null ...");
            return null;
        }
        product = productMapper.selectOne( product );
        return product;
    }


    @Override
    public PagingData<Product> selectPage(Product product) {
        PagingData<Product> PagingData = new PagingData<>();

        if( null == product ) {
            log.warn("select product page, but product is null...");
            return PagingData;
        }

        Integer queryCount = productMapper.selectByIndexCount( product );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select product page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<Product> products =  selectByIndex( product );
        PagingData.setData( products );
        return PagingData;
    }

    @Override
    public List<Product> selectByIndex(Product product) {
        List<Product> products = new ArrayList<>();
        if( null == product ) {
            log.warn("select product by index, but product is null ...");
            return products;
        }

        products = productMapper.selectByIndex( product );

        return products;
    }

    @Override
    public List<CountStatus> countStatus() {
        return productMapper.countStatus();
    }

    @Override
    public List<Tree<String>> selectTree(Product product) {
        // 查询类型下应用
        List<Product> productList = productMapper.selectList(product);

        List<TreeNode<String>> collect = productList.stream()
                .sorted(Comparator.comparingInt(Product::getOrderNum)).map(productMap -> {
                    TreeNode<String> treeNode = new TreeNode<>();
                    treeNode.setId(productMap.getId());
                    treeNode.setParentId(String.valueOf(product.getType()));
                    treeNode.setName(productMap.getName());
                    treeNode.setWeight(productMap.getOrderNum());
                    treeNode.setExtra(new HashMap<>(){{
                        put("icon",productMap.getIcon());
                    }});
                    return treeNode;
                }).collect(Collectors.toList());

        return TreeUtil.build(collect, String.valueOf(product.getType()));
    }
}
