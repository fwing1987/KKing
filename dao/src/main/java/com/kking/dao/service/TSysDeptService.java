package com.kking.dao.service;

import com.alibaba.fastjson.JSONObject;
import com.kking.dao.entity.TSysDept;
import java.util.List;

public interface TSysDeptService {
    public TSysDept selectById(Integer id);
    public List<TSysDept> selectList(TSysDept tSysDept);
    public TSysDept selectOneByProperty(String key, Object value);
    public List<TSysDept> selectListByProperty(String key, Object value);
    public int insert(TSysDept tSysDept);
    public int deleteById(Integer id);
    public int update(TSysDept tSysDept);

    List<JSONObject> selectListWithUser(TSysDept dept);
}
