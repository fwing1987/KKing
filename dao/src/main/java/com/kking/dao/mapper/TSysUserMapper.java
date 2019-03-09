package com.kking.dao.mapper;

import com.kking.dao.entity.TSysUser;
import java.util.List;

public interface TSysUserMapper {
    public TSysUser selectById(Integer id);
    public List<TSysUser> selectList(TSysUser tSysUser);
    public TSysUser selectOneByProperty(String key, Object value);
    public List<TSysUser> selectListByProperty(String key, Object value);
    public int insert(TSysUser tSysUser);
    public int deleteById(TSysUser tSysUser);
    public int update(TSysUser tSysUser);
    public int checkUserRoleEditPermission(Integer userId,Integer roleId);
}
