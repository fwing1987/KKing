package com.kking.dao.service;

import com.kking.dao.entity.TSysUserRole;
import java.util.List;

public interface TSysUserRoleService {
    public TSysUserRole selectById(Integer id);
    public List<TSysUserRole> selectList(TSysUserRole tSysUserRole);
    public TSysUserRole selectOneByProperty(String key, Object value);
    public List<TSysUserRole> selectListByProperty(String key, Object value);
    public int insert(TSysUserRole tSysUserRole);
    public int deleteById(Integer id);
    public int update(TSysUserRole tSysUserRole);
}
