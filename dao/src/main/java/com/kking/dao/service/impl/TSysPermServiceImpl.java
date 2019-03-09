package com.kking.dao.service.impl;

import com.kking.dao.entity.TSysPerm;
import com.kking.dao.mapper.TSysPermMapper;
import com.kking.dao.service.TSysPermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TSysPermServiceImpl implements TSysPermService {
    @Autowired
    TSysPermMapper tSysPermMapper;
    @Override
    public TSysPerm selectById(Integer id) {
        return tSysPermMapper.selectById(id);
    }

    @Override
    public List<TSysPerm> selectList(TSysPerm tSysPerm) {
        return tSysPermMapper.selectList(tSysPerm);
    }

    @Override
    public TSysPerm selectOneByProperty(String key, Object value) {
        return tSysPermMapper.selectOneByProperty(key,value);
    }

    @Override
    public List<TSysPerm> selectListByProperty(String key, Object value) {
        return tSysPermMapper.selectListByProperty(key,value);
    }

    @Override
    public int insert(TSysPerm tSysPerm) {
        return tSysPermMapper.insert(tSysPerm);
    }

    @Override
    public int deleteById(TSysPerm tSysPerm) {
        return tSysPermMapper.deleteById(tSysPerm);
    }

    @Override
    public int update(TSysPerm tSysPerm) {
        return tSysPermMapper.update(tSysPerm);
    }

}
