package com.javalearnings.securitydemo.aop;

import com.javalearnings.securitydemo.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class GenericAspect {

    @Before(value = "execution(* com.javalearnings.securitydemo.controllers.MasterLookupController.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.debug("Request to "+joinPoint.getSignature() + " Started at : " + DateUtils.getLocalDateTimeEST());
    }

    @After(value = "execution(* com.javalearnings.securitydemo.controllers.MasterLookupController.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        log.debug("Request to "+joinPoint.getSignature() + " Started at : " + DateUtils.getLocalDateTimeEST());
    }

}
