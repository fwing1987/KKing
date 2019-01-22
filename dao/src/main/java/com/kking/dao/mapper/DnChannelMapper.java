package com.kking.dao.mapper;

import com.kking.dao.entity.DnChannel;

public interface DnChannelMapper {
    public DnChannel selectOne(Integer id);
    public DnChannel selectOneByProperty(String key, Object value);
    public int insert(DnChannel DnChannel);
    public int deleteById(Integer id);
    public int update(DnChannel DnChannel);
}
