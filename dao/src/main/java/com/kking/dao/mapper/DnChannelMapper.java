package com.kking.dao.mapper;

import com.alibaba.fastjson.JSONObject;
import com.kking.dao.entity.DnChannel;

import java.util.List;

public interface DnChannelMapper {
    public DnChannel selectOne(Integer id);
    public List<DnChannel> select(JSONObject json);
    public DnChannel selectOneByProperty(String key, Object value);
    public int insert(DnChannel DnChannel);
    public int deleteById(Integer id);
    public int update(DnChannel DnChannel);
}
