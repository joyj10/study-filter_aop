package com.example.filterfast.aop;

import com.example.filterfast.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class TimerAop {

    @Pointcut(value = "within(com.example.filterfast.controller.UserApiController)")
    public void timerPointCut() {}

    @Before(value = "timerPointCut()")
    public void before(JoinPoint joinPoint) {
        log.info("before");
    }

    @After(value = "timerPointCut()")
    public void after(JoinPoint joinPoint) {
        log.info("after");
    }

    // 메소드 호출 성공 실행 시 (Not Throws)
    @AfterReturning(value = "timerPointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("after Returning");
    }

    // 메소드 호출 실패 예외 발생 (Throws)
    @AfterThrowing(value = "timerPointCut()", throwing = "tx")
    public void afterThrowing(JoinPoint joinPoint, Throwable tx) {
        log.info("after Throwing");
    }

    @Around(value = "timerPointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("메서드 실행 이전");
        Arrays.stream(joinPoint.getArgs()).forEach(
                it -> {
                    log.info("it 1 : {}", it);
                    if (it instanceof UserRequest) {
                        UserRequest tempUser = (UserRequest) it;
                        tempUser.setPhoneNumber(tempUser.getPhoneNumber().replace("-", ""));
                    }
                    log.info("it 2 : {}", it);
                }
        );

        joinPoint.proceed();
        log.info("메서드 실행 이후");
    }
}
