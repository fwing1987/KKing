package com.kking.dao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kking.common.annotation.DataScope;
import com.kking.common.utils.TreeUtil;
import com.kking.dao.entity.TSysMenu;
import com.kking.dao.entity.TSysPerm;
import com.kking.dao.mapper.TSysMenuMapper;
import com.kking.dao.mapper.TSysPermMapper;
import com.kking.dao.service.TSysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TSysMenuServiceImpl implements TSysMenuService {
    @Autowired
    TSysMenuMapper tSysMenuMapper;
    @Autowired
    TSysPermMapper tSysPermMapper;

    @Override
    public TSysMenu selectById(TSysMenu tSysMenu) {
        return tSysMenuMapper.selectById(tSysMenu);
    }

    @Override
    public List<TSysMenu> selectList(TSysMenu tSysMenu) {
        return tSysMenuMapper.selectList(tSysMenu);
    }

    @Override
    public TSysMenu selectOneByProperty(String key, Object value) {
        return tSysMenuMapper.selectOneByProperty(key,value);
    }

    @Override
    public List<TSysMenu> selectListByProperty(String key, Object value) {
        return tSysMenuMapper.selectListByProperty(key,value);
    }

    @Override
    @Transactional
    public int insert(TSysMenu tSysMenu) {
        tSysMenuMapper.insert(tSysMenu);
        //添加权限
        TSysPerm perm = new TSysPerm();
        perm.setPermName(tSysMenu.getPermName());
        perm.setResourceId(tSysMenu.getId());
        perm.setPermType(TSysPerm.PERM_TYPE.MENU);
        return tSysPermMapper.insert(perm);
    }

    @Override
    @DataScope(type = "menu")
    public int deleteById(TSysMenu tSysMenu) {
        return tSysMenuMapper.deleteById(tSysMenu);
    }

    @Override
    @DataScope(type = "menu")
    public int update(TSysMenu tSysMenu) {
        TSysMenu tmpMenu = tSysMenuMapper.selectById(tSysMenu);
        if(tmpMenu == null){
            throw new RuntimeException("菜单不存在~");
        }
        //添加权限
        TSysPerm perm = tSysPermMapper.selectOneByProperty("resource_id",tSysMenu.getId());
        if(perm == null){
            //添加权限
            perm = new TSysPerm();
            perm.setPermName(tSysMenu.getPermName());
            perm.setResourceId(tSysMenu.getId());
            perm.setPermType(TSysPerm.PERM_TYPE.MENU);
            tSysPermMapper.insert(perm);
        }else if(!tSysMenu.getPermName().equals(perm.getPermName())){
            //修改权限标识
            perm.setPermName(tSysMenu.getPermName());
            tSysPermMapper.update(perm);
        }

        return tSysMenuMapper.update(tSysMenu);
    }

    @Override
    @DataScope(type = "menu")
    public List<JSONObject> getUserMenu(TSysMenu tSysMenu) {
        List<TSysMenu> menuList = tSysMenuMapper.getUserMenu(tSysMenu);
        return TreeUtil.toTreeList(menuList);
    }

    @Override
    @DataScope(type = "menu")
    public List<JSONObject> getUserMenuWithRoleStatus(TSysMenu tSysMenu) {
        List<TSysMenu> menuList = tSysMenuMapper.getUserMenuWithRoleStatus(tSysMenu);
        return TreeUtil.toTreeList(menuList);
    }
}
