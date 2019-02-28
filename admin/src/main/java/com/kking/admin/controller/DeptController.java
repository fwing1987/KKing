package com.kking.admin.controller;

import com.kking.admin.dto.JsonResult;
import com.kking.dao.entity.TSysDept;
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
import java.util.Map;

@RestController
@RequestMapping("/sys/dept")
public class DeptController extends BaseController{
    @Autowired
    TSysDeptService deptService;

    @RequiresPermissions("dept:list")
    @RequestMapping("list")
    public JsonResult list(@RequestBody TSysDept dept){
        TSysUser user = (TSysUser)SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> params = new HashMap<>();
        params.put("userId",user.getId());
        dept.setParams(params);
        return toJson(deptService.selectListWithUser(dept));
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
    public JsonResult delete(@RequestParam Integer id){
        return toJson(deptService.deleteById(id));
    }
}
