package com.kking.admin.controller;

import com.kking.admin.dto.JsonResult;

public class BaseController {
    protected JsonResult toJson(int rows){
        if(rows > 0){
            return JsonResult.success();
        }else{
            return JsonResult.error();
        }
    }
}
