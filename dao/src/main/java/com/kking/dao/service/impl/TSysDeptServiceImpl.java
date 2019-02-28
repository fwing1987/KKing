package com.kking.dao.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kking.common.utils.TreeUtil;
import com.kking.dao.entity.TSysDept;
import com.kking.dao.mapper.TSysDeptMapper;
import com.kking.dao.service.TSysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TSysDeptServiceImpl implements TSysDeptService {
    @Autowired
    TSysDeptMapper tSysDeptMapper;
    @Override
    public TSysDept selectById(Integer id) {
        return tSysDeptMapper.selectById(id);
    }

    @Override
    public List<TSysDept> selectList(TSysDept tSysDept) {
        return tSysDeptMapper.selectList(tSysDept);
    }

    @Override
    public TSysDept selectOneByProperty(String key, Object value) {
        return tSysDeptMapper.selectOneByProperty(key,value);
    }

    @Override
    public List<TSysDept> selectListByProperty(String key, Object value) {
        return tSysDeptMapper.selectListByProperty(key,value);
    }

    @Override
    public int insert(TSysDept tSysDept) {
        return tSysDeptMapper.insert(tSysDept);
    }

    @Override
    public int deleteById(Integer id) {
        return tSysDeptMapper.deleteById(id);
    }

    @Override
    public int update(TSysDept tSysDept) {
        return tSysDeptMapper.update(tSysDept);
    }

    @Override
    public List<JSONObject> selectListWithUser(TSysDept dept) {
        return TreeUtil.toTreeList(tSysDeptMapper.selectListWithUser(dept));
    }
}
