package com.zstu.mijazz.wms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private BasicAuthInterceptor basicAuthInterceptor;
    private AdminAuthInteceptor adminAuthInteceptor;

//    @Value("interceptorURL.jump")
//    private String excludeFromConfigFile;

    public InterceptorConfig(BasicAuthInterceptor basicAuthInterceptor, AdminAuthInteceptor adminAuthInteceptor) {
        this.basicAuthInterceptor = basicAuthInterceptor;
        this.adminAuthInteceptor = adminAuthInteceptor;
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
        excludePath.add("/adminapi/**");
        registry.addInterceptor(adminAuthInteceptor).addPathPatterns("/adminapi/**");
        registry.addInterceptor(basicAuthInterceptor).addPathPatterns("/**").excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);

    }
}
