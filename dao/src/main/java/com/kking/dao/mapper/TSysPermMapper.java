package com.kking.dao.mapper;

import com.kking.dao.entity.TSysPerm;
import java.util.List;

public interface TSysPermMapper {
    public TSysPerm selectById(Integer id);
    public List<TSysPerm> selectList(TSysPerm tSysPerm);
    public TSysPerm selectOneByProperty(String key, Object value);
    public List<TSysPerm> selectListByProperty(String key, Object value);
    public int insert(TSysPerm tSysPerm);
    public int deleteById(Integer id);
    public int update(TSysPerm tSysPerm);
}
