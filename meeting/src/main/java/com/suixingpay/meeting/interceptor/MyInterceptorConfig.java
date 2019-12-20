package com.suixingpay.meeting.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @description  配置拦截器
 * @author Huang Yafeng
 * @date 2019/12/10 17:44
 */
@Configuration
public class MyInterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //未登录拦截
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }

}
