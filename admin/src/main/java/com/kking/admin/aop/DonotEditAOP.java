package com.kking.admin.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DonotEditAOP {
//    @Pointcut("execution(* com.kking.admin.controller.*.*add*(..))")
//    public void pointCutAdd(){}
//
//    @Pointcut("execution(* com.kking.admin.controller.*.*update*(..))")
//    public void pointCutUpdate(){}
//
//    @Pointcut("execution(* com.kking.admin.controller.*.*delete*(..))")
//    public void pointCutDelete(){}
//
//    @Pointcut("execution(* com.kking.admin.controller.*.*edit*(..))")
//    public void pointCutEdit(){}
//
//    @Pointcut("pointCutAdd() || pointCutUpdate() || pointCutEdit() || pointCutDelete()")
//    public void pointCutAll(){}
//
//    @Before("pointCutAll()")
//    public void donotEdit(){
//        throw new RuntimeException("演示模式，不允许修改");
//    }
}
