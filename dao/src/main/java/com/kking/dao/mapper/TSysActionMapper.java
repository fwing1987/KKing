package com.kking.dao.mapper;

import com.kking.dao.entity.TSysAction;
import java.util.List;
import java.util.Map;

public interface TSysActionMapper {
    public TSysAction selectById(Integer id);
    public List<TSysAction> selectList(TSysAction tSysAction);
    public TSysAction selectOneByProperty(String key, Object value);
    public List<TSysAction> selectListByProperty(String key, Object value);
    public int insert(TSysAction tSysAction);
    public int deleteById(Integer id);
    public int update(TSysAction tSysAction);

    List<Map<String,String>> getPermTypeAction(String permType);
}
