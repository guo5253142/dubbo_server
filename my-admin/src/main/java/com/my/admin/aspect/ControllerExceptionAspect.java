package com.my.admin.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller层的异常切面
 */
@Aspect

public class ControllerExceptionAspect {
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Pointcut("execution(* com.my.admin.controller.*.*.*(..))")//连接点是controller子包里面的方法
    private void controllerPointcut() {}
    
    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "e")//切点在controllerPointcut()
    public void handleThrowing(JoinPoint joinPoint, Exception e) {//controller类抛出的异常在这边捕获
      
        Object[] args = joinPoint.getArgs();
        String param="";
        for (int i = 0; i < args.length; i++) {
        	param+=args[i].toString()+" ";
        }
        //开始打log
        logger.error("抓取到异常error,参数:"+param, e);
        
    }
    
    
}
