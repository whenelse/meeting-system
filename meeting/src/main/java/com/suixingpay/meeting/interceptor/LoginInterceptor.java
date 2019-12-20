package com.suixingpay.meeting.interceptor;

import com.suixingpay.meeting.annotation.NoneAuth;
import com.suixingpay.meeting.token.Token;
import com.suixingpay.meeting.token.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @description: 根据token对未登录进行拦截
 * @author: Huang Yafeng
 * @date: Created in 2019/12/8 19:30
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenHelper tokenHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 如果不是映射到方法直接通过
        //handler是需要程序员开发的，它说白了就是Controller层下的一个个方法，也就RequestMapping下的方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //如果被@NoneAuth注解代表不需要登录验证，直接通过
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(method.getAnnotation(NoneAuth.class) != null) {
            return true;
        }
        //token验证
        String authStr = request.getHeader("authStr");
        Token model = tokenHelper.get(authStr);
        //验证通过
        //封装到request中
        if(tokenHelper.check(model)) {
           // request.setAttribute("userPhone", model.getUserPhone());
           // request.setAttribute("userName", model.getUserName());
           // request.setAttribute("userSex", model.getUserSex());
           // request.setAttribute("userProvince", model.getUserProvince());
            //request.getSession().setAttribute("userId", model.getUserId());
            return true;
        }
        //验证未通过
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write("权限未认证");
        response.setStatus(401);
        return false;
    }
}
