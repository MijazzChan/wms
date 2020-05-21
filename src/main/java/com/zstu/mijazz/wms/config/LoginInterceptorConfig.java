package com.zstu.mijazz.wms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {
    private LoginInterceptor interceptor;

//    @Value("interceptorURL.jump")
//    private String excludeFromConfigFile;

    public LoginInterceptorConfig(LoginInterceptor interceptor){
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> excludePath = new ArrayList<>();
        excludePath.add("/login");
        excludePath.add("/logout");
        excludePath.add("/js/**");
        excludePath.add("/css/**");
        excludePath.add("/img/**");
        excludePath.add("/favicon.ico");
        excludePath.add("/api/login");

        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);

    }
}
