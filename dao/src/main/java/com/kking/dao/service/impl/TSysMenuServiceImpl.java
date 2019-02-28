package com.kking.dao.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kking.common.utils.TreeUtil;
import com.kking.dao.entity.TSysAction;
import com.kking.dao.entity.TSysMenu;
import com.kking.dao.entity.TSysPerm;
import com.kking.dao.mapper.TSysActionMapper;
import com.kking.dao.mapper.TSysMenuMapper;
import com.kking.dao.mapper.TSysPermMapper;
import com.kking.dao.service.TSysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TSysMenuServiceImpl implements TSysMenuService {
    @Autowired
    TSysMenuMapper tSysMenuMapper;
    @Autowired
    TSysPermMapper tSysPermMapper;
    @Autowired
    TSysActionMapper tSysActionMapper;

    @Override
    public TSysMenu selectById(Integer id) {
        return tSysMenuMapper.selectById(id);
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
        if(StringUtils.isEmpty(tSysMenu.getPermName()) &&
                StringUtils.isNotEmpty(tSysMenu.getActionName())){
            throw new RuntimeException("添加操作名必须同时添加资源名");
        }
        tSysMenuMapper.insert(tSysMenu);
        if(StringUtils.isNotEmpty(tSysMenu.getActionName())){
            //添加权限
            TSysAction action = tSysActionMapper.selectOneByProperty("action_name",tSysMenu.getActionName());
            TSysPerm perm;
            if(action == null){
                //没有action，先添加，再加权限
                action = new TSysAction();
                action.setActionName(tSysMenu.getActionName());
                tSysActionMapper.insert(action);
            }
            perm = new TSysPerm();
            perm.setActionId(action.getId());
            perm.setPermName(tSysMenu.getPermName());
            if(tSysMenu.getType().equals(TSysMenu.TYPE.BUTTON)) {
                //按钮资源ID为父菜单ID
                perm.setResourceId(tSysMenu.getPid());
            }else if(tSysMenu.getType().equals(TSysMenu.TYPE.MENU)){
                //菜单资源ID
                perm.setResourceId(tSysMenu.getId());
            }
            perm.setPermType(TSysPerm.PERM_TYPE.MENU);
            tSysPermMapper.insert(perm);

            tSysMenu.setPermId(perm.getId());
            tSysMenuMapper.update(tSysMenu);
        }
        return 1;
    }

    @Override
    public int deleteById(Integer id) {
        return tSysMenuMapper.deleteById(id);
    }

    @Override
    public int update(TSysMenu tSysMenu) {
        if(StringUtils.isEmpty(tSysMenu.getPermName()) &&
                StringUtils.isNotEmpty(tSysMenu.getActionName())){
            throw new RuntimeException("添加操作名必须同时添加资源名");
        }
        if(StringUtils.isNotEmpty(tSysMenu.getActionName())){
            //添加权限
            TSysAction action = tSysActionMapper.selectOneByProperty("action_name",tSysMenu.getActionName());
            TSysPerm perm;
            if(action == null){
                //没有action，先添加，再加权限
                action = new TSysAction();
                action.setActionName(tSysMenu.getActionName());
                tSysActionMapper.insert(action);

                perm = new TSysPerm();
                perm.setActionId(action.getId());
                perm.setPermName(tSysMenu.getPermName());
                if(tSysMenu.getType().equals(TSysMenu.TYPE.BUTTON)) {
                    //按钮资源ID为父菜单ID
                    perm.setResourceId(tSysMenu.getPid());
                }else if(tSysMenu.getType().equals(TSysMenu.TYPE.MENU)){
                    //菜单资源ID
                    perm.setResourceId(tSysMenu.getId());
                }
                perm.setPermType(TSysPerm.PERM_TYPE.MENU);
                tSysPermMapper.insert(perm);
                tSysMenu.setPermId(perm.getId());
            }else{
                TSysPerm permCond = new TSysPerm();
                if(tSysMenu.getType().equals(TSysMenu.TYPE.BUTTON)) {
                    //按钮资源ID为父菜单ID
                    permCond.setResourceId(tSysMenu.getPid());
                }else if(tSysMenu.getType().equals(TSysMenu.TYPE.MENU)){
                    //菜单资源ID
                    permCond.setResourceId(tSysMenu.getId());
                }
                permCond.setActionId(action.getId());
                List<TSysPerm> permList = tSysPermMapper.selectList(permCond);

                if(permList.size() == 0) {
                    //无权限记录，新增
                    perm = new TSysPerm();
                    perm.setActionId(action.getId());
                    perm.setPermName(tSysMenu.getPermName());
                    if(tSysMenu.getType().equals(TSysMenu.TYPE.BUTTON)) {
                        //按钮资源ID为父菜单ID
                        perm.setResourceId(tSysMenu.getPid());
                    }else if(tSysMenu.getType().equals(TSysMenu.TYPE.MENU)){
                        //菜单资源ID
                        perm.setResourceId(tSysMenu.getId());
                    }
                    perm.setPermType(TSysPerm.PERM_TYPE.MENU);
                    tSysPermMapper.insert(perm);
                    tSysMenu.setPermId(perm.getId());
                }else{
                    //有权限记录
                    if(!permList.get(0).getId().equals(tSysMenu.getPermId())){
                        //与菜单原记录不同
                        tSysMenu.setPermId(permList.get(0).getId());
                    }
                }
            }

        }
        tSysMenuMapper.update(tSysMenu);
        return 1;
    }

    @Override
    public List<JSONObject> getUserMenu(TSysMenu tSysMenu) {
        List<TSysMenu> menuList = tSysMenuMapper.getUserMenu(tSysMenu);
        return TreeUtil.toTreeList(menuList);
    }

    @Override
    public List<JSONObject> getTreeList(TSysMenu tSysMenu) {
        List<TSysMenu> menuList = tSysMenuMapper.getUserMenu(tSysMenu);
        return TreeUtil.toTreeList(menuList);
    }
}
