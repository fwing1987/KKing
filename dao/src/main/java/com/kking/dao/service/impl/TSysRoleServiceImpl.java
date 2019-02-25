package com.kking.dao.service.impl;

import com.kking.dao.entity.TSysRole;
import com.kking.dao.mapper.TSysRoleMapper;
import com.kking.dao.service.TSysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TSysRoleServiceImpl implements TSysRoleService {
    @Autowired
    TSysRoleMapper tSysRoleMapper;
    @Override
    public TSysRole selectById(Integer id) {
        return tSysRoleMapper.selectById(id);
    }

    @Override
    public List<TSysRole> selectList(TSysRole tSysRole) {
        return tSysRoleMapper.selectList(tSysRole);
    }

    @Override
    public TSysRole selectOneByProperty(String key, Object value) {
        return tSysRoleMapper.selectOneByProperty(key,value);
    }

    @Override
    public List<TSysRole> selectListByProperty(String key, Object value) {
        return tSysRoleMapper.selectListByProperty(key,value);
    }

    @Override
    public int insert(TSysRole tSysRole) {
        return tSysRoleMapper.insert(tSysRole);
    }

    @Override
    public int deleteById(Integer id) {
        return tSysRoleMapper.deleteById(id);
    }

    @Override
    public int update(TSysRole tSysRole) {
        return tSysRoleMapper.update(tSysRole);
    }

    @Override
    public List<TSysRole> getUserRoleInfo(Integer userId,String permType){
        return tSysRoleMapper.getUserRoleInfo(userId,permType);
    }


}
