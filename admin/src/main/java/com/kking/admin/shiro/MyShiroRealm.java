package com.kking.admin.shiro;

import com.kking.dao.entity.TSysAction;
import com.kking.dao.entity.TSysPerm;
import com.kking.dao.entity.TSysRole;
import com.kking.dao.entity.TSysUser;
import com.kking.dao.service.TSysRoleService;
import com.kking.dao.service.TSysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private TSysUserService userService;
    @Autowired
    private TSysRoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        TSysUser user = (TSysUser) principals.getPrimaryPrincipal();
        List<TSysRole> roleList = user.getRoleList();
        if(roleList == null){
            roleList = roleService.getUserRoleInfo(user.getId(), TSysPerm.PERM_TYPE.MENU);
            user.setRoleList(roleList);
        }

        for(TSysRole role : roleList){
            authorizationInfo.addRole(role.getRoleName());
            for(TSysPerm perm : role.getPermList()){
                for(TSysAction action: perm.getActionList()){
                    authorizationInfo.addStringPermission(perm.getPermName()+":"+action.getActionName());
                }
            }
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
//        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();
//        System.out.println(token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        TSysUser user = userService.selectOneByProperty("user_name",username);
//        System.out.println("----->>userInfo="+userInfo);
        List<TSysRole> roleList = user.getRoleList();
        if(roleList == null){
            roleList = roleService.getUserRoleInfo(user.getId(), TSysPerm.PERM_TYPE.MENU);
            user.setRoleList(roleList);
        }
        if (user == null) {
            return null;
        }
        if (user.getState() == TSysUser.STATE.INVALID) { //账户冻结
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getSalt()),// md5(salt+password),采用明文访问时，不需要此句
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
