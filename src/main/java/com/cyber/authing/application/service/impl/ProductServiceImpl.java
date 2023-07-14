package com.cyber.authing.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.IdUtil;
import com.cyber.authing.domain.response.CountStatus;
import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.ProductMapper;
import com.cyber.authing.domain.entity.Product;
import com.cyber.authing.application.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

        product.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
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
        // 查询全部部门
//        List<Dept> deptAllList = deptMapper.selectList(dept);
//
//        List<TreeNode<String>> collect = deptAllList.stream()
//                .sorted(Comparator.comparingInt(Dept::getOrderNum)).map(deptMap -> {
//                    TreeNode<String> treeNode = new TreeNode<>();
//                    treeNode.setId(deptMap.getId());
//                    treeNode.setParentId(String.valueOf(deptMap.getParentId()));
//                    treeNode.setName(deptMap.getName());
//                    treeNode.setWeight(deptMap.getOrderNum());
//                    return treeNode;
//                }).collect(Collectors.toList());

        return TreeUtil.build(new ArrayList<>(), "0");
    }
}
