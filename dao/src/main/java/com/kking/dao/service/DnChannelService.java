package com.kking.dao.service;

import com.alibaba.fastjson.JSONObject;
import com.kking.dao.entity.DnChannel;

import java.util.List;

public interface DnChannelService {
    public DnChannel selectOne(Integer id);
    public List<DnChannel> select(JSONObject json);
    public int insert(DnChannel dnChannel);
    public DnChannel selectOneByProperty(String key, Object value);
    public int deleteById(Integer id);
    public int update(DnChannel dnChannel);
}
