package com.example.filterfast.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
//@Component
public class Loggerfilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 진입전
        log.info(">>>>>>> 진입");

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper((HttpServletResponse) response);

        chain.doFilter(req, res);

        String reqJson = new String(req.getContentAsByteArray());
        log.info("req : {}", reqJson);

        String resJson = new String(res.getContentAsByteArray());
        log.info("res : {}", resJson);

        // 진입후
        log.info("<<<<<<< 리턴");

        res.copyBodyToResponse(); //한번 읽어도 카피해 놓은 res를 다시 복제 하도록 함
    }
}
