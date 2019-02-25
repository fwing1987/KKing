package com.kking.dao.service.impl;

import com.kking.dao.entity.TSysAction;
import com.kking.dao.mapper.TSysActionMapper;
import com.kking.dao.service.TSysActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class TSysActionServiceImpl implements TSysActionService {
    @Autowired
    TSysActionMapper tSysActionMapper;
    @Override
    public TSysAction selectById(Integer id) {
        return tSysActionMapper.selectById(id);
    }

    @Override
    public List<TSysAction> selectList(TSysAction tSysAction) {
        return tSysActionMapper.selectList(tSysAction);
    }

    @Override
    public TSysAction selectOneByProperty(String key, Object value) {
        return tSysActionMapper.selectOneByProperty(key,value);
    }

    @Override
    public List<TSysAction> selectListByProperty(String key, Object value) {
        return tSysActionMapper.selectListByProperty(key,value);
    }

    @Override
    public int insert(TSysAction tSysAction) {
        return tSysActionMapper.insert(tSysAction);
    }

    @Override
    public int deleteById(Integer id) {
        return tSysActionMapper.deleteById(id);
    }

    @Override
    public int update(TSysAction tSysAction) {
        return tSysActionMapper.update(tSysAction);
    }

    public List<Map<String,String>> getPermTypeAction(String permType){
        return tSysActionMapper.getPermTypeAction(permType);
    }
}
