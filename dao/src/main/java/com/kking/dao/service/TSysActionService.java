package com.kking.dao.service;

import com.kking.dao.entity.TSysAction;
import java.util.List;
import java.util.Map;

public interface TSysActionService {
    public TSysAction selectById(Integer id);
    public List<TSysAction> selectList(TSysAction tSysAction);
    public TSysAction selectOneByProperty(String key, Object value);
    public List<TSysAction> selectListByProperty(String key, Object value);
    public int insert(TSysAction tSysAction);
    public int deleteById(Integer id);
    public int update(TSysAction tSysAction);

    public List<Map<String,String>> getPermTypeAction(String permType);
}
