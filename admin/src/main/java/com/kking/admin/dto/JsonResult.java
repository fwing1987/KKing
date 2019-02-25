package com.kking.admin.dto;

import java.util.HashMap;

public class JsonResult extends HashMap<String,Object> {
    private static final String CODE = "code";
    private static final String MSG = "msg";
    public JsonResult(){
    }
    public JsonResult(int code,String msg){
        super.put(CODE,code);
        super.put(MSG,msg);
    }
    public static JsonResult instance(int code,String msg){
        JsonResult ret = new JsonResult();
        ret.put(CODE,code);
        ret.put(MSG,msg);
        return ret;
    }

    public static JsonResult instance(int code){
        JsonResult ret = new JsonResult();
        ret.put(CODE,code);
        if(code == 0) {
            ret.put(MSG, "操作成功");
        }else{
            ret.put(MSG, "操作失败");
        }
        return ret;
    }

    public static JsonResult error(int code,String msg){
        return instance(code,msg);
    }
    public static JsonResult error(String msg){
        return instance(1,msg);
    }
    public static JsonResult error(){
        return instance(1,"操作失败");
    }
    public static JsonResult success(){
        return instance(0,"操作成功");
    }

}
