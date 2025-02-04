package com.bighu.web.admin.custom.config;

import com.bighu.web.admin.custom.converter.StringToBaseEnumConverterFactory;
import com.bighu.web.admin.interceptor.AuthenticationInterceptor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private StringToBaseEnumConverterFactory stringToBaseEnumConverterFactory;

@Autowired
    private  AuthenticationInterceptor authenticationInterceptor;
    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(stringToBaseEnumConverterFactory);
        registry.addConverterFactory(stringToBaseEnumConverterFactory);
        WebMvcConfigurer.super.addFormatters(registry);

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login/**");
        WebMvcConfigurer.super.addInterceptors(registry);

    }
}
