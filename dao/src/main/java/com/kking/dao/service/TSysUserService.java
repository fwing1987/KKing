package com.kking.dao.service;

import com.kking.dao.entity.TSysUser;
import java.util.List;

public interface TSysUserService {
    public TSysUser selectById(Integer id);
    public List<TSysUser> selectList(TSysUser tSysUser);
    public TSysUser selectOneByProperty(String key, Object value);
    public List<TSysUser> selectListByProperty(String key, Object value);
    public int insert(TSysUser tSysUser);
    public int deleteById(Integer id);
    public int update(TSysUser tSysUser);
}
