
package com.permission.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @auther: shenke
 * @date: 2019/10/27 21:25
 * @description: Http请求拦截器
 */
@Slf4j
@Component
public class HttpInterceptor implements HandlerInterceptor {


    /**
     * 记录请求开始时间key
     */
    private static final String REQUEST_START_TIME = "request_start_time";


    /**
     * 请求开始前置拦截处理
     * @param request
     * @param response
     * @param handler
     * @return
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("[请求开始] url = {}, param = {}", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()));
        long requestStartTime = System.currentTimeMillis();
        request.setAttribute(REQUEST_START_TIME, requestStartTime);
        return true;
    }


   /**
     * 请求结束后置拦截处理
     * @param request
     * @param response
     * @param handler
     * @param ex
     */

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long requestStartTime = (long) request.getAttribute(REQUEST_START_TIME);
        long requestEndTime = System.currentTimeMillis();
        long requestTimeMs = requestEndTime - requestStartTime;
        log.info("[请求结束] url = {}, param = {}, time = {}ms", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), requestTimeMs);
    }

}

