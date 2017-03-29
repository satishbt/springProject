package com.app.stock.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.app..*.*(..))")
    public void loggingPointCut() {
    }

    @Before("loggingPointCut()")
    @Order(1)
    public void logBefore(JoinPoint joinPoint) {
	logger.info("Class :" + joinPoint.getClass());
	logger.info("Signature :" + joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "loggingPointCut()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
	logger.info("Exiting from Method :" + joinPoint.getSignature().getName());
	logger.info("Return value :" + result);
    }

}
