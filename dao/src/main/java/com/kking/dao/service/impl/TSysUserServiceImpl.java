package com.kking.dao.service.impl;

import com.kking.dao.entity.TSysRole;
import com.kking.dao.entity.TSysUser;
import com.kking.dao.mapper.TSysRoleMapper;
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
    public int deleteById(TSysUser tSysUser) {
        return tSysUserMapper.deleteById(tSysUser);
    }

    @Override
    public int update(TSysUser tSysUser) {
        return tSysUserMapper.update(tSysUser);
    }

    @Override
    public boolean isUserHasPermForRole(TSysUser user, Integer roleId) {
        if(user.isAdmin()){
            return true;
        }
        for (TSysRole role : user.getRoleList()) {
            if (TSysRole.PERM_TYPE.ALL.equals(role.getDeptPermType())) {
                return true;
            }
        }
        return tSysUserMapper.checkUserRoleEditPermission(user.getId(), roleId) > 0;
    }
}
