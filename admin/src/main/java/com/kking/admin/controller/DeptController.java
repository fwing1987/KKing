package com.kking.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.kking.admin.dto.JsonResult;
import com.kking.common.annotation.DataScope;
import com.kking.common.utils.TreeUtil;
import com.kking.dao.entity.TSysDept;
import com.kking.dao.entity.TSysMenu;
import com.kking.dao.entity.TSysUser;
import com.kking.dao.service.TSysDeptService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/dept")
public class DeptController extends BaseController{
    @Autowired
    TSysDeptService deptService;

    @RequiresPermissions("dept:list")
    @RequestMapping("list")
    public JsonResult list(@RequestBody TSysDept dept){
        return toJson(TreeUtil.toTreeList(deptService.selectList(dept)));
    }

    @RequiresPermissions("dept:list")
    @RequestMapping("listRole")
    public JsonResult listWithRoleStatus(@RequestBody TSysDept dept){
        List<JSONObject> deptList = TreeUtil.toTreeList(deptService.getDeptWithRoleStatus(dept));
        TreeUtil.setChildrenRoleWithParent(deptList, null);
        return toJson(deptList);
    }

    @RequiresPermissions("dept:add")
    @RequestMapping("add")
    public JsonResult add(@RequestBody TSysDept dept){
        return toJson(deptService.insert(dept));
    }

    @RequiresPermissions("dept:edit")
    @RequestMapping("update")
    public JsonResult update(@RequestBody TSysDept dept){
        return toJson(deptService.update(dept));
    }

    @RequiresPermissions("dept:remove")
    @RequestMapping("delete")
    public JsonResult delete(@RequestBody TSysDept dept){
        return toJson(deptService.deleteById(dept));
    }
}
