package com.kking.admin.controller;

import com.kking.admin.dto.JsonResult;
import com.kking.common.annotation.DataScope;
import com.kking.dao.entity.TSysMenu;
import com.kking.dao.entity.TSysUser;
import com.kking.dao.service.TSysMenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/menu")
public class MenuController extends BaseController{
    @Autowired
    TSysMenuService menuService;

    @RequiresPermissions("menu:list")
    @RequestMapping("list")
    public JsonResult list(@RequestBody TSysMenu menu){
        return toJson(menuService.getUserMenu(menu));
    }

    @RequiresPermissions("menu:list")
    @RequestMapping("listRole")
    public JsonResult listWithRoleStatus(@RequestBody TSysMenu menu){
        return toJson(menuService.getUserMenuWithRoleStatus(menu));
    }

    @RequiresPermissions("menu:add")
    @RequestMapping("add")
    public JsonResult add(@RequestBody TSysMenu menu){
        return toJson(menuService.insert(menu));
    }

    @RequiresPermissions("menu:edit")
    @RequestMapping("update")
    public JsonResult update(@RequestBody TSysMenu menu){
        return toJson(menuService.update(menu));
    }

    @RequiresPermissions("menu:remove")
    @RequestMapping("delete")
    public JsonResult delete(@RequestBody TSysMenu menu){
        return toJson(menuService.deleteById(menu));
    }
}
