package com.jellyfish.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jellyfish.annotation.DynamicSource;
import com.jellyfish.entity.Etsuser;
import com.jellyfish.mapper.EtsuserMapper;
import com.jellyfish.service.IEtsuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author will
 * @since 2019-07-11
 */
@Service
public class EtsuserServiceImpl extends ServiceImpl<EtsuserMapper, Etsuser> implements IEtsuserService {
    @DynamicSource(value = "mysqlDataSource")
    @Override
    public List<Etsuser> list(Etsuser etsuser) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.like("name",etsuser.getName());
        return baseMapper.selectList(wrapper);
    }

    @DynamicSource(value = "mysqlDataSource")
    @Override
    public boolean saveOrUpdate(Etsuser entity) {
        return super.saveOrUpdate(entity);
    }
    @DynamicSource(value = "oracleDataSource")
    @Override
    public boolean saveOrUpdateForOracle(Etsuser entity) {
        return super.saveOrUpdate(entity);
    }
}
