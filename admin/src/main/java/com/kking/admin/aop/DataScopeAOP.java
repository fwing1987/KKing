package com.kking.admin.aop;

import com.kking.common.annotation.DataScope;
import com.kking.dao.entity.BaseEntity;
import com.kking.dao.entity.TSysDept;
import com.kking.dao.entity.TSysRole;
import com.kking.dao.entity.TSysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class DataScopeAOP {

    public static final String DEPT = "dept";
    public static final String MENU = "menu";

    public static final String DATA_SCOPE = "dataScope";

    @Pointcut("@annotation(com.kking.common.annotation.DataScope)")
    public void dataScopePointCut(){}

    @Before("dataScopePointCut()")
    public void beforePointCut(JoinPoint jp) throws Throwable{
        TSysUser user = (TSysUser)SecurityUtils.getSubject().getPrincipal();
        if(user.isAdmin()){
            return;
        }
        // 获得注解
        DataScope controllerDataScope = getAnnotationLog(jp);

        if(DEPT.equals(controllerDataScope.type())){
            handleDeptDataScope(jp,controllerDataScope.tableAlias());
        } else if(MENU.equals(controllerDataScope.type())) {
            handleMenuDataScope(jp);
        }
    }

    private void handleDeptDataScope(JoinPoint jp, String alias){
        TSysUser user = (TSysUser)SecurityUtils.getSubject().getPrincipal();
        BaseEntity entity = (BaseEntity)jp.getArgs()[0];

        for(TSysRole role : user.getRoleList()){
            if(TSysRole.PERM_TYPE.ALL.equals(role.getDeptPermType())){
                return;
            }
        }
        String aliasCond = "";
        if(StringUtils.isNotEmpty(alias)){
            aliasCond = alias + ".";
        }
        String sql;
        if(entity instanceof TSysRole){
            sql = String.format("and %sid in (select rp2.role_id\n" +
                    "from t_sys_user_role ur,t_sys_role_perm rp,t_sys_perm p,t_sys_perm p2,t_sys_role_perm rp2\n" +
                    "where ur.user_id=%d and rp.role_id=ur.role_id and p.id=rp.perm_id\n" +
                    "and p.resource_id=p2.resource_id and p2.id=rp2.perm_id\n" +
                    "and p.perm_type='DEPT' and p2.perm_type='DEPT')",aliasCond,user.getId());
        }else{
            String keyId = "dept_id";
            if (entity instanceof TSysDept){
                keyId = "id";
            }
            sql = String.format("and %s%s in (select id from (select distinct d.id\n" +
                    "from t_sys_user_role ur,t_sys_role_perm rp,t_sys_perm p,t_sys_dept d\n" +
                    "where ur.user_id=%d and rp.role_id=ur.role_id and p.id=rp.perm_id and FIND_IN_SET(p.resource_id,concat(d.id,',',d.pids))\n" +
                    "and p.perm_type='DEPT') a)",aliasCond,keyId,user.getId());
        }

        entity.getParams().put("dataScope",sql);
    }

    private void handleMenuDataScope(JoinPoint jp){
        TSysUser user = (TSysUser)SecurityUtils.getSubject().getPrincipal();
        BaseEntity entity = (BaseEntity)jp.getArgs()[0];

        String sql = String.format(
                    "and m.id in (" +
                        "select p.resource_id " +
                        "from t_sys_user_role ur,t_sys_role_perm rp,t_sys_perm p " +
                        "where ur.role_id=rp.role_id and rp.perm_id=p.id " +
                        "and ur.user_id=%d and p.perm_type='MENU')",user.getId());

        entity.getParams().put("dataScope",sql);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private DataScope getAnnotationLog(JoinPoint joinPoint)
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }
}
