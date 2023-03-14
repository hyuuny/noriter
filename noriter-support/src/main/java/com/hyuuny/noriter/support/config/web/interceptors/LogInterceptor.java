package com.hyuuny.noriter.support.config.web.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;
import java.util.UUID;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String HTTP_REQUEST_ID_KEY = "X-Request-ID";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        String requestId = request.getHeader(HTTP_REQUEST_ID_KEY);
        if (isEmpty(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        MDC.put(HTTP_REQUEST_ID_KEY, requestId);
        log.info("REQUEST  [{}][{}][{}][{}]",
                requestId,
                request.getMethod(),
                request.getRequestURI(),
                getParameters(request)
        );
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {
        log.info("RESPONSE [{}][{}][{}]",
                MDC.get(LogInterceptor.HTTP_REQUEST_ID_KEY),
                request.getMethod(),
                request.getRequestURI()
        );
        MDC.clear();
    }

    private String getParameters(HttpServletRequest request) {
        StringBuilder posted = new StringBuilder();
        Enumeration<?> e = request.getParameterNames();
        if (e != null) {
            posted.append("?");
            while (e.hasMoreElements()) {
                if (posted.length() > 1) {
                    posted.append("&");
                }

                String curr = (String) e.nextElement();
                posted.append(curr).append("=");
                if (curr.contains("password")
                        || curr.contains("pass")
                        || curr.contains("pwd")) {
                    posted.append("*****");
                } else {
                    posted.append(request.getParameter(curr));
                }
            }
        }

        String ip = request.getHeader("X-FORWARDED-FOR");
        String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
        if (ipAddr != null && !ipAddr.equals("")) {
            posted.append("&_clientIp=").append(ipAddr);
        }

        return posted.toString();
    }

    private String getRemoteAddr(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            log.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }

}
