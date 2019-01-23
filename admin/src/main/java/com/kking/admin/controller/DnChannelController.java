package com.kking.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.kking.admin.dto.JsonResult;
import com.kking.dao.entity.DnChannel;
import com.kking.dao.service.DnChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/api/channel")
public class DnChannelController extends BaseController{
    static Logger log = LoggerFactory.getLogger(DnChannelController.class);

    @Autowired
    DnChannelService dnChannelService;

    @GetMapping("/get/{id}")
    public DnChannel index(@PathVariable Integer id){
        dnChannelService.selectOneByProperty("token","0A2AD2021F6664E1344B40257983F1F8");
        return dnChannelService.selectOne(id);
    }

    @DeleteMapping("/del/{id}")
    public JsonResult delete(@PathVariable Integer id){
        return toJson(dnChannelService.deleteById(id));
    }

    @PostMapping("/add")
    public JsonResult insert(@RequestBody  DnChannel dnChannel){
        return toJson(dnChannelService.insert(dnChannel));
    }

    @PostMapping("/update")
    public JsonResult update(@RequestBody  DnChannel dnChannel){
        return toJson(dnChannelService.update(dnChannel));
    }

    @PostMapping("/select")
    public JsonResult select(@RequestBody JSONObject json){
        startPageWithMore(json);
        return toJsonWithPageMore(dnChannelService.select(json));
    }
}
