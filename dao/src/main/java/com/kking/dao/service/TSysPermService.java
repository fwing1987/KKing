package com.kking.dao.service;

import com.kking.dao.entity.TSysPerm;
import java.util.List;

public interface TSysPermService {
    public TSysPerm selectById(Integer id);
    public List<TSysPerm> selectList(TSysPerm tSysPerm);
    public TSysPerm selectOneByProperty(String key, Object value);
    public List<TSysPerm> selectListByProperty(String key, Object value);
    public int insert(TSysPerm tSysPerm);
    public int deleteById(TSysPerm tSysPerm);
    public int update(TSysPerm tSysPerm);
}
