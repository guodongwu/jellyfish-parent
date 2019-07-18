package com.jellyfish.service;

import com.jellyfish.entity.Etsuser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author will
 * @since 2019-07-11
 */
public interface IEtsuserService extends IService<Etsuser> {
    public List<Etsuser> list(Etsuser etsuser);
    public boolean saveOrUpdateForOracle(Etsuser entity);
}
