package com.vitali.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CustomAspect {

    @Pointcut("execution(public * com.vitali.services.*Service.findById(*))")
    public void anyFindByIdServiceMethod() {
    }

    //    @Before("execution(public * com.vitali.services.*Service.findById(*))") // may put pointcut description inside
    @Before(value = "anyFindByIdServiceMethod() " +          // or can refer ot that pointcut
                    "&& args(id) " +
                    "&& target(service) " +
                    "&& this(serviceProxy)" +
                    "&& @within(transactional)",
            argNames = "joinPoint,id,service,serviceProxy,transactional")
    public void addLogging(JoinPoint joinPoint,         // JoinPoint must be first parameter
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional) {
        log.info("before - invoked findById method in class {}, with id {}", service, id);
    }
}
