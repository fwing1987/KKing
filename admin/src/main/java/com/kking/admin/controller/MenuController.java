package com.kking.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.kking.admin.dto.JsonResult;
import com.kking.dao.entity.TSysMenu;
import com.kking.dao.entity.TSysPerm;
import com.kking.dao.service.TSysActionService;
import com.kking.dao.service.TSysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/menu")
public class MenuController extends BaseController{
    @Autowired
    TSysMenuService menuService;
    @Autowired
    TSysActionService actionService;

    @RequestMapping("list")
    public JsonResult list(@RequestBody TSysMenu menu){
        JSONObject retJson = new JSONObject();
        retJson.put("menus",menuService.getTreeList(menu));

        List<Map<String,String>> actionList = actionService.getPermTypeAction(TSysPerm.PERM_TYPE.MENU);
        retJson.put("actions",actionList);
        return toJson(retJson);
    }

    @RequestMapping("add")
    public JsonResult add(@RequestBody TSysMenu menu){
        return toJson(menuService.insert(menu));
    }

    @RequestMapping("update")
    public JsonResult update(@RequestBody TSysMenu menu){
        return toJson(menuService.update(menu));
    }

    @RequestMapping("delete")
    public JsonResult delete(@RequestParam Integer id){
        return toJson(menuService.deleteById(id));
    }
}
