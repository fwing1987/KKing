package com.kking.dao.service;

import com.kking.dao.entity.DnChannel;

public interface DnChannelService {
    public DnChannel selectOne(Integer id);
    public int insert(DnChannel dnChannel);
    public DnChannel selectOneByProperty(String key, Object value);
    public int deleteById(Integer id);
    public int update(DnChannel dnChannel);
}
