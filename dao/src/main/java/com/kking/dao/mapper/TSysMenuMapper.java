package com.kking.dao.mapper;

import com.kking.dao.entity.TSysMenu;

import java.util.List;

public interface TSysMenuMapper {
    public TSysMenu selectById(Integer id);
    public List<TSysMenu> selectList(TSysMenu tSysMenu);
    public TSysMenu selectOneByProperty(String key, Object value);
    public List<TSysMenu> selectListByProperty(String key, Object value);
    public int insert(TSysMenu tSysMenu);
    public int deleteById(Integer id);
    public int update(TSysMenu tSysMenu);

    List<TSysMenu> getUserMenu(TSysMenu tSysMenu);
}
