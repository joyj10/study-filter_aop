package com.example.filterfast.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class OpenApiInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("pre handle");

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        OpenApi methodeLevel = handlerMethod.getMethodAnnotation(OpenApi.class);    // 메서드 레벨에 어노테이션 있는지 확인
        if (methodeLevel != null) {
            log.info("method level");
            return true;
        }

        OpenApi classLevel = handlerMethod.getBeanType().getAnnotation(OpenApi.class);  // 클래스 레벨에 어노테이션 있는지 확인
        if (classLevel != null) {
            log.info("class level");
            return true;
        }

        log.info("open api 아닙니다.");
        // controller 전달, false 전달하지 않음
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("post handle");
        // HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("after completion");
        // HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
