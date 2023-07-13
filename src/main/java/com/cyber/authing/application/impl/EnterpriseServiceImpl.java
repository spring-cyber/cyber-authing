package	com.cyber.authing.application.impl;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.cyber.domain.entity.PagingData;
import com.cyber.authing.domain.repository.EnterpriseMapper;
import com.cyber.authing.domain.entity.Enterprise;
import com.cyber.authing.application.EnterpriseService;

import com.cyber.domain.entity.SortingField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseMapper enterpriseMapper;

    @Override
    @Transactional
    public Integer save(Enterprise enterprise) {

        if( null == enterprise ) {
            log.warn("save enterprise, but enterprise is null...");
            return 0;
        }

        enterprise.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        return enterpriseMapper.save( enterprise );
    }

    @Override
    @Transactional
    public Integer deleteById(Enterprise enterprise) {

        if( null == enterprise ) {
            log.warn("delete enterprise, but enterprise is null  or enterprise id is null...");
            return 0;
        }

        return enterpriseMapper.deleteById( enterprise );
    }

    @Override
    @Transactional
    public Integer updateById(Enterprise enterprise) {

        if( null == enterprise ) {
            log.warn("update enterprise, but enterprise is null  or enterprise id is null...");
            return 0;
        }

        return enterpriseMapper.updateById( enterprise );
    }

    @Override
    public Enterprise selectOne(Enterprise enterprise) {
        if( null == enterprise ) {
            log.warn("select enterprise one, but enterprise is null ...");
            return null;
        }
        enterprise = enterpriseMapper.selectOne( enterprise );
        return enterprise;
    }


    @Override
    public PagingData<Enterprise> selectPage(Enterprise enterprise) {
        PagingData<Enterprise> PagingData = new PagingData<>();

        if( null == enterprise ) {
            log.warn("select enterprise page, but enterprise is null...");
            return PagingData;
        }

        Integer queryCount = enterpriseMapper.selectByIndexCount( enterprise );
        PagingData.setRow( queryCount );

        if( queryCount <= 0 ) {
            log.info("select enterprise page , but count {} == 0 ...",queryCount);
            return PagingData;
        }

        List<Enterprise> enterprises =  selectByIndex( enterprise );
        PagingData.setData( enterprises );
        return PagingData;
    }

    @Override
    public List<Enterprise> selectByIndex(Enterprise enterprise) {
        List<Enterprise> enterprises = new ArrayList<>();
        if( null == enterprise ) {
            log.warn("select enterprise by index, but enterprise is null ...");
            return enterprises;
        }
        enterprise.setSortBy("order_num");
        enterprise.setSortType(SortingField.ORDER_ASC);
        enterprises = enterpriseMapper.selectByIndex( enterprise );

        return enterprises;
    }

    @Override
    public List<Enterprise> selectList(Enterprise enterprise) {
        List<Enterprise> enterprises = new ArrayList<>();
        if( null == enterprise ) {
            log.warn("select enterprise by index, but enterprise is null ...");
            return enterprises;
        }
        enterprises = enterpriseMapper.selectList( enterprise );
        return enterprises;
    }
}
