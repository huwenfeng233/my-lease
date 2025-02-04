package com.bighu.web.admin.custom.config;

import com.bighu.web.admin.custom.converter.StringToBaseEnumConverterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private StringToBaseEnumConverterFactory stringToBaseEnumConverterFactory;
    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(stringToBaseEnumConverterFactory);
        registry.addConverterFactory(stringToBaseEnumConverterFactory);
        WebMvcConfigurer.super.addFormatters(registry);

    }
}
