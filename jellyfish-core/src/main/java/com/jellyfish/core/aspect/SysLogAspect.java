package com.jellyfish.core.aspect;

import com.jellyfish.core.queue.SysLogQueue;
import com.jellyfish.entity.SysLog;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SysLogAspect {
    private static  final Logger logger= LoggerFactory.getLogger(SysLogAspect.class);
    ThreadLocal<SysLog> sysLogThreadLocal=new ThreadLocal<SysLog>();
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)")
    public void logCut(){}

    @Before("logCut()")
    public void doBefore(JoinPoint joinPoint) {
        SysLog sysLog=new SysLog();
        sysLog.setCreateTime(String.valueOf(System.currentTimeMillis()));
        sysLogThreadLocal.set(sysLog);
        System.out.println("前置");
    }
    @AfterReturning(pointcut = "logCut()",returning = "ret")
    public void doAfterReturning(JoinPoint joinPoint,Object ret) {
        System.out.println("返回"+ret);
        SysLog sysLog=sysLogThreadLocal.get();
        if(null!=sysLog){
            this.saveLog();
        }
    }
    @AfterThrowing(pointcut = "logCut()",throwing = "ex")
    public void doAfterReturning(JoinPoint joinPoint,Exception ex) {
        System.out.println("返回");
        SysLog sysLog=sysLogThreadLocal.get();
        if(null!=sysLog){
            this.saveLog();
        }
    }

    private void saveLog(){
        SysLog sysLog=sysLogThreadLocal.get();
        long bTime=Long.valueOf(sysLog.getCreateTime());
        double eTime=(System.currentTimeMillis()-bTime)/1000.0d;
        System.out.println("Spend TIME(sencond):"+eTime);
        try{
            SysLogQueue.getInstance().push(sysLog);
        }catch (Exception ex){
            System.out.println("添加对列失败");
        }finally {
            sysLogThreadLocal.remove();
        }
    }
}
