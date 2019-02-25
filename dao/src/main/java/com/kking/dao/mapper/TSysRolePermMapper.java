package com.kking.dao.mapper;

import com.kking.dao.entity.TSysRolePerm;
import java.util.List;

public interface TSysRolePermMapper {
    public TSysRolePerm selectById(Integer id);
    public List<TSysRolePerm> selectList(TSysRolePerm tSysRolePerm);
    public TSysRolePerm selectOneByProperty(String key, Object value);
    public List<TSysRolePerm> selectListByProperty(String key, Object value);
    public int insert(TSysRolePerm tSysRolePerm);
    public int deleteById(Integer id);
    public int update(TSysRolePerm tSysRolePerm);
}
