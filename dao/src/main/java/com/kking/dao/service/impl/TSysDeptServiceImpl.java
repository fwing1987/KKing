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
        TSysDept parentDept = tSysDeptService.selectOneByProperty("id",tSysDept.getPid());
        tSysDept.setPids(parentDept.getPids() + "," + tSysDept.getPid());
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
        TSysDept oldDept = tSysDeptService.selectOneByProperty("id",tSysDept.getId());
        if(!oldDept.getPid().equals(tSysDept.getPid())){
            //修改父部门
            String oldPids = oldDept.getPids();
            TSysDept parentDept = tSysDeptService.selectOneByProperty("id",tSysDept.getPid());
            //修改祖先pids
            tSysDept.setPids(parentDept.getPids() + "," + tSysDept.getPid());

            List<TSysDept> childDeptList = tSysDeptMapper.getChildrenDept(tSysDept);
            if(childDeptList.size() > 0){
                //修改所有层级的孩子结点的祖先pids
                for(TSysDept childDept: childDeptList){
                    childDept.setPids(childDept.getPids().replace(oldPids, tSysDept.getPids()));
                }
                tSysDeptMapper.updateDeptListPids(childDeptList);
            }
        }
        return tSysDeptMapper.update(tSysDept);
    }

    @Override
    @DataScope(tableAlias = "d")
    public List<TSysDept> getDeptWithRoleStatus(TSysDept dept) {
        return tSysDeptMapper.getDeptWithRoleStatus(dept);
    }

}
