package com.kking.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.kking.admin.dto.JsonResult;
import com.kking.dao.entity.TSysRole;
import com.kking.dao.service.TSysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/role")
public class RoleController extends BaseController {
    @Autowired
    TSysRoleService roleService;

    @RequiresPermissions("role:view")
    @RequestMapping("list")
    public JsonResult list(@RequestBody TSysRole role){
        return toJson(roleService.selectList(role));
    }

    @RequestMapping("add")
    public JsonResult addRole(@RequestBody TSysRole role){
        return toJson(roleService.insert(role));
    }

    @RequestMapping("perm/update")
    public JsonResult editPermission(@RequestBody JSONObject json){
        return toJson(roleService.editPermission(json));
    }

    @RequestMapping("update")
    public JsonResult updateRole(@RequestBody TSysRole role){
        return toJson(roleService.update(role));
    }

    @RequestMapping("delete")
    public JsonResult deleteRole(@RequestParam Integer id){
        return toJson(roleService.deleteById(id));
    }
}
