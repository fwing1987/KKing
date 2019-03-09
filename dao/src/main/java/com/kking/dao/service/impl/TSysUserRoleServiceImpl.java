package com.kking.dao.service.impl;

import com.kking.dao.entity.TSysUserRole;
import com.kking.dao.mapper.TSysUserRoleMapper;
import com.kking.dao.service.TSysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TSysUserRoleServiceImpl implements TSysUserRoleService {
    @Autowired
    TSysUserRoleMapper tSysUserRoleMapper;
    @Override
    public TSysUserRole selectById(Integer id) {
        return tSysUserRoleMapper.selectById(id);
    }

    @Override
    public List<TSysUserRole> selectList(TSysUserRole tSysUserRole) {
        return tSysUserRoleMapper.selectList(tSysUserRole);
    }

    @Override
    public TSysUserRole selectOneByProperty(String key, Object value) {
        return tSysUserRoleMapper.selectOneByProperty(key,value);
    }

    @Override
    public List<TSysUserRole> selectListByProperty(String key, Object value) {
        return tSysUserRoleMapper.selectListByProperty(key,value);
    }

    @Override
    public int insert(TSysUserRole tSysUserRole) {
        return tSysUserRoleMapper.insert(tSysUserRole);
    }

    @Override
    public int deleteById(TSysUserRole tSysUserRole) {
        return tSysUserRoleMapper.deleteById(tSysUserRole);
    }

    @Override
    public int update(TSysUserRole tSysUserRole) {
        return tSysUserRoleMapper.update(tSysUserRole);
    }

}
