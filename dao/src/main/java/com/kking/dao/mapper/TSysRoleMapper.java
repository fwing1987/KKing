package com.kking.dao.mapper;

import com.kking.dao.entity.TSysRole;
import java.util.List;

public interface TSysRoleMapper {
    public TSysRole selectById(Integer id);
    public List<TSysRole> selectList(TSysRole tSysRole);
    public TSysRole selectOneByProperty(String key, Object value);
    public List<TSysRole> selectListByProperty(String key, Object value);
    public int insert(TSysRole tSysRole);
    public int deleteById(Integer id);
    public int update(TSysRole tSysRole);

    public List<TSysRole> getUserRoleInfo(Integer userId,String permType);
}
