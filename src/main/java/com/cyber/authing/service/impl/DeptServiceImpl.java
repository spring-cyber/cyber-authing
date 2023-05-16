package com.cyber.authing.service.impl;

import com.cyber.authing.entity.domain.Dept;
import com.cyber.authing.mapper.DeptMapper;
import com.cyber.authing.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class DeptServiceImpl implements DeptService {

	private static final Logger LOGGER = LoggerFactory.getLogger( DeptServiceImpl.class );

    @Autowired
    DeptMapper deptMapper;
    
    
    @Override
    @Transactional(readOnly = false)
    public Integer save(Dept dept) {

        if( null == dept) {
            LOGGER.warn("save dept, but dept is null...");
            return 0;
        }

        return deptMapper.insert(dept);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteById(Dept dept) {

        if( null == dept) {
            LOGGER.warn("delete dept, but dept is null  or dept id is null...");
            return 0;
        }

        return deptMapper.deleteById(dept);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateById(Dept dept) {

        if( null == dept) {
            LOGGER.warn("update dept, but dept is null  or dept id is null...");
            return 0;
        }

        return deptMapper.updateById(dept);
    }

    @Override
    public Dept selectOne(Dept dept) {
        if( dept == null) {
            LOGGER.warn("select dept one, but dept is null ...");
            return null;
        }
        dept = deptMapper.selectOne( dept );
        return dept;
    }
//
//
//    @Override
//    public PagingData<Dept> selectPage(Dept dept) {
//        PagingData<Dept> pagingData = new PagingData<Dept>();
//
//        if( null == dept ) {
//            LOGGER.warn("select dept page, but dept is null...");
//            return pagingData;
//        }
//
//        Integer queryCount = deptMapper.selectByIndexCount( dept );
//        pagingData.setTotal( queryCount );
//
//        if( null != queryCount && queryCount <= 0 ) {
//            LOGGER.info("select dept page , but count {} == 0 ...",queryCount);
//            return pagingData;
//        }
//
//        List<Dept> depts =  selectByIndex( dept );
//        pagingData.setRows(depts);
//        return pagingData;
//    }
//
//    @Override
//    public List<Dept> selectByIndex(Dept dept) {
//        List<Dept> depts = new ArrayList<Dept>();
//        if( dept == null) {
//            LOGGER.warn("select dept by index, but dept is null ...");
//            return depts;
//        }
//
//        depts = deptMapper.selectByIndex( dept );
//
//        return depts;
//    }
}
