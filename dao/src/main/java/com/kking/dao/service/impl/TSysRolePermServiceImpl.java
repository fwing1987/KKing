package com.kking.dao.service.impl;

import com.kking.dao.entity.TSysRolePerm;
import com.kking.dao.mapper.TSysRolePermMapper;
import com.kking.dao.service.TSysRolePermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TSysRolePermServiceImpl implements TSysRolePermService {
    @Autowired
    TSysRolePermMapper tSysRolePermMapper;
    @Override
    public TSysRolePerm selectById(Integer id) {
        return tSysRolePermMapper.selectById(id);
    }

    @Override
    public List<TSysRolePerm> selectList(TSysRolePerm tSysRolePerm) {
        return tSysRolePermMapper.selectList(tSysRolePerm);
    }

    @Override
    public TSysRolePerm selectOneByProperty(String key, Object value) {
        return tSysRolePermMapper.selectOneByProperty(key,value);
    }

    @Override
    public List<TSysRolePerm> selectListByProperty(String key, Object value) {
        return tSysRolePermMapper.selectListByProperty(key,value);
    }

    @Override
    public int insert(TSysRolePerm tSysRolePerm) {
        return tSysRolePermMapper.insert(tSysRolePerm);
    }

    @Override
    public int deleteById(Integer id) {
        return tSysRolePermMapper.deleteById(id);
    }

    @Override
    public int update(TSysRolePerm tSysRolePerm) {
        return tSysRolePermMapper.update(tSysRolePerm);
    }

}
