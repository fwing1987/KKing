package com.kking.dao.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kking.common.annotation.DataScope;
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
    @Autowired
    TSysDeptService tSysDeptService;
    @Override
    @DataScope
    public TSysDept selectById(TSysDept tSysDept) {
        return tSysDeptMapper.selectById(tSysDept);
    }

    @Override
    @DataScope
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
        TSysDept deptCond = new TSysDept();
        deptCond.setId(tSysDept.getPid());
        TSysDept parentDept = tSysDeptService.selectById(deptCond);
        tSysDept.setPids(tSysDept.getPid() + "," + parentDept.getPids());
        return tSysDeptMapper.insert(tSysDept);
    }

    @Override
    @DataScope
    public int deleteById(TSysDept tSysDept) {
        return tSysDeptMapper.deleteById(tSysDept);
    }

    @Override
    @DataScope
    public int update(TSysDept tSysDept) {
        return tSysDeptMapper.update(tSysDept);
    }

    @Override
    @DataScope(tableAlias = "d")
    public List<TSysDept> getDeptWithRoleStatus(TSysDept dept) {
        return tSysDeptMapper.getDeptWithRoleStatus(dept);
    }

}
