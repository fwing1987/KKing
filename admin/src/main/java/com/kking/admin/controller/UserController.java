package com.kking.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.kking.admin.dto.JsonResult;
import com.kking.common.utils.MD5Util;
import com.kking.dao.entity.TSysMenu;
import com.kking.dao.entity.TSysPerm;
import com.kking.dao.entity.TSysRole;
import com.kking.dao.entity.TSysUser;
import com.kking.dao.service.TSysActionService;
import com.kking.dao.service.TSysMenuService;
import com.kking.dao.service.TSysRoleService;
import com.kking.dao.service.TSysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    TSysUserService userService;
    @Autowired
    TSysMenuService menuService;
    @Autowired
    TSysRoleService roleService;

    @RequestMapping("/info")
    public JsonResult getUserInfo(){
        JsonResult ret = JsonResult.success();
        TSysUser user = (TSysUser) SecurityUtils.getSubject().getPrincipal();
        TSysMenu menuParam = new TSysMenu();
        menuParam.setUserId(user.getId());
        menuParam.setType("MENU");
        ret.put("menus",menuService.getUserMenu(menuParam));
        JSONObject userJson = (JSONObject) JSONObject.toJSON(user);
        userJson.remove("password");
        userJson.remove("salt");
        ret.put("user",userJson);

        List<TSysRole> roleList = roleService.getUserRoleInfo(user.getId(),TSysPerm.PERM_TYPE.MENU);
        ret.put("roles",roleList);
        return ret;
    }

    @RequestMapping("/login")
    public JsonResult login(@RequestBody TSysUser user){
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
            subject.login(token);
        }catch (Exception e){
            log.error("",e);
            return JsonResult.error();
        }

        JsonResult retJson = JsonResult.success();
        retJson.put("token",subject.getSession().getId());
        return  retJson;
    }
}
