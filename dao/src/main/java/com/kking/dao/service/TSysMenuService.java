package com.kking.dao.service;

import com.alibaba.fastjson.JSONObject;
import com.kking.dao.entity.TSysMenu;

import java.util.List;

public interface TSysMenuService {
    public TSysMenu selectById(Integer id);
    public List<TSysMenu> selectList(TSysMenu tSysMenu);
    public TSysMenu selectOneByProperty(String key, Object value);
    public List<TSysMenu> selectListByProperty(String key, Object value);
    public int insert(TSysMenu tSysMenu);
    public int deleteById(Integer id);
    public int update(TSysMenu tSysMenu);

    public List<JSONObject> getUserMenu(TSysMenu tSysMenu);
    public List<JSONObject> getTreeList(TSysMenu tSysMenu);
}
