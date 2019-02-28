package com.kking.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class TreeUtil {
    public static List<JSONObject> toTreeList(List oldList) {
        int prePid = -1;
        List<JSONObject> tmpList = new ArrayList<>();
        Map<Integer, List<JSONObject>> childMap = new HashMap<>();
        Map<Integer, JSONObject> allMap = new HashMap<>();
        for (Object obj : oldList) {
            JSONObject menuJSON = (JSONObject) JSON.toJSON(obj);

            if (menuJSON.getInteger("pid") != prePid && prePid != -1) {
                if (allMap.containsKey(prePid) && !allMap.get(prePid).containsKey("children")) {
                    allMap.get(prePid).put("children", tmpList);
                } else {
                    childMap.put(prePid, tmpList);
                }
                tmpList = new ArrayList<>();
            }
            if (childMap.containsKey(menuJSON.getInteger("id"))) {
                //菜单有子菜单，安装上
                menuJSON.put("children", childMap.get(menuJSON.getInteger("id")));
                childMap.remove(menuJSON.getInteger("id"));
            }
            prePid = menuJSON.getInteger("pid");
            tmpList.add(menuJSON);
            allMap.put(menuJSON.getInteger("id"), menuJSON);
        }

        if(childMap.size() > 0){
            for(List<JSONObject> child : childMap.values()){
                tmpList.addAll(child);
            }

        }

        return tmpList;
    }
}
