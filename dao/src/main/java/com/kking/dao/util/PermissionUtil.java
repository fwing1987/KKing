package com.kking.dao.util;

import com.kking.dao.entity.TSysPerm;
import com.kking.dao.entity.TSysRole;
import com.kking.dao.entity.TSysUser;

public class PermissionUtil {
    public static boolean hasPermission(TSysUser user,Integer permId){
        if(user.isAdmin()){
            return true;
        }
        for(TSysRole role : user.getRoleList()){
            for(TSysPerm perm : role.getPermList()){
                if(permId.equals(perm.getId())){
                    return true;
                }
            }
        }
        return false;
    }
}
