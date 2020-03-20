package com.permission.interceptor;

import com.permission.util.CookieUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther: shenke
 * @date: 2020/3/6 13:31
 * @description: 登录拦截器,注意执行顺序: 过滤器 -> 拦截器 -> AOP切面
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 预处理回调方法，实现处理器的预处理
     * @param request
     * @param response
     * @param handler 响应的处理器
     * @return
     *      true表示继续流程（如调用下一个拦截器或处理器）
     *      false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时需要通过response来产生响应
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 检查用户是否已登录,未抛出异常则已登录
        CookieUtils.currentCasUserInfo(request);

        return true;
    }

    /**
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前）
     * 可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView也可能为null
     * @param request
     * @param response
     * @param handler
     * @param modelAndView 模型和视图对象
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调
     * 类似于try-catch-finally中的finally，但仅处理调用处理器执行链中的内容
     * @param request
     * @param response
     * @param handler 响应的处理器
     * @param ex 异常对象
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

}
