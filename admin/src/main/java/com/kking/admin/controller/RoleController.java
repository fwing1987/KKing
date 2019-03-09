package com.kking.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.kking.admin.dto.JsonResult;
import com.kking.dao.entity.TSysRole;
import com.kking.dao.entity.TSysUser;
import com.kking.dao.service.TSysRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/role")
public class RoleController extends BaseController {
    @Autowired
    TSysRoleService roleService;

    @RequiresPermissions("role:list")
    @RequestMapping("list")
    public JsonResult list(@RequestBody TSysRole role){
        return toJson(roleService.selectList(role));
    }

    @RequiresPermissions("role:add")
    @RequestMapping("add")
    public JsonResult addRole(@RequestBody TSysRole role){
        return toJson(roleService.insert(role));
    }

    @RequiresPermissions("role:edit")
    @RequestMapping("perm/update")
    public JsonResult editMenu(@RequestBody JSONObject json){
        TSysUser user = (TSysUser) SecurityUtils.getSubject().getPrincipal();
        return toJson(roleService.editMenu(user, json));
    }

    @RequiresPermissions("role:edit")
    @RequestMapping("dept/update")
    public JsonResult editDept(@RequestBody JSONObject json){
        TSysUser user = (TSysUser) SecurityUtils.getSubject().getPrincipal();
        return toJson(roleService.editDept(user, json));
    }

    @RequiresPermissions("role:edit")
    @RequestMapping("update")
    public JsonResult updateRole(@RequestBody TSysRole role){
        return toJson(roleService.update(role));
    }

    @RequiresPermissions("role:remove")
    @RequestMapping("delete")
    public JsonResult deleteRole(@RequestBody TSysRole role){
        return toJson(roleService.deleteById(role));
    }
}
