package com.kking.dao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kking.dao.entity.DnChannel;
import com.kking.dao.mapper.DnChannelMapper;
import com.kking.dao.service.DnChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DnChannelServiceImpl implements DnChannelService {
    @Autowired
    DnChannelMapper dnChannelMapper;
    @Override
    public DnChannel selectOne(Integer id) {
        return dnChannelMapper.selectOne(id);
    }

    @Override
    public int insert(DnChannel dnChannel) {
        return dnChannelMapper.insert(dnChannel);
    }

    @Override
    public DnChannel selectOneByProperty(String key, Object value) {
        return dnChannelMapper.selectOneByProperty(key,value);
    }

    @Override
    public int deleteById(Integer id) {
        return dnChannelMapper.deleteById(id);
    }

    @Override
    public int update(DnChannel dnChannel) {
        return dnChannelMapper.update(dnChannel);
    }

    @Override
    public List<DnChannel> select(JSONObject json) {
        return dnChannelMapper.select(json);
    }
}
