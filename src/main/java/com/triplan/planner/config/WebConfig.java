package com.triplan.planner.config;

import com.triplan.planner.interceptor.AdminCheckInterceptor;
import com.triplan.planner.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //로그인 여부 체크
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/plan/*", "/admin/*", "/*/write");

        //admin 계정 로그인 여부 체크
        registry.addInterceptor(new AdminCheckInterceptor())
                .order(2)
                .addPathPatterns("/admin/*");
    }
}
