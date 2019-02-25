package com.kking.dao.service.impl;

import com.kking.dao.entity.TSysUser;
import com.kking.dao.mapper.TSysUserMapper;
import com.kking.dao.service.TSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TSysUserServiceImpl implements TSysUserService {
    @Autowired
    TSysUserMapper tSysUserMapper;
    @Override
    public TSysUser selectById(Integer id) {
        return tSysUserMapper.selectById(id);
    }

    @Override
    public List<TSysUser> selectList(TSysUser tSysUser) {
        return tSysUserMapper.selectList(tSysUser);
    }

    @Override
    public TSysUser selectOneByProperty(String key, Object value) {
        return tSysUserMapper.selectOneByProperty(key,value);
    }

    @Override
    public List<TSysUser> selectListByProperty(String key, Object value) {
        return tSysUserMapper.selectListByProperty(key,value);
    }

    @Override
    public int insert(TSysUser tSysUser) {
        return tSysUserMapper.insert(tSysUser);
    }

    @Override
    public int deleteById(Integer id) {
        return tSysUserMapper.deleteById(id);
    }

    @Override
    public int update(TSysUser tSysUser) {
        return tSysUserMapper.update(tSysUser);
    }

}
