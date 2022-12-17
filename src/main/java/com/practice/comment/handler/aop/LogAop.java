package com.practice.comment.handler.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LogAop {

    @Pointcut("@annotation(com.practice.comment.handler.aop.annotaion.Log)")
    private void enableLog(){}

    @Around("enableLog()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        Map<String, Object> argsMap = getArgs(proceedingJoinPoint);

        log.info(">>>>>>>>>>>>>>> Method Call: {}, {}", proceedingJoinPoint.getSignature().getName(), argsMap);

        result = proceedingJoinPoint.proceed();

        log.info(">>>>>>>>>>>>>>> Method End: {}, {}", proceedingJoinPoint.getSignature().getName(), result);

        return result;
    }

    private Map<String, Object> getArgs(ProceedingJoinPoint proceedingJoinPoint) {
        Map<String, Object> argsMap = new HashMap<>();
        CodeSignature codeSignature = (CodeSignature) proceedingJoinPoint.getSignature();

        int index = 0;

        for(Object arg : proceedingJoinPoint.getArgs()) {
            argsMap.put(codeSignature.getParameterNames()[index], arg);
            index++;
        }
        return argsMap;
    }
}