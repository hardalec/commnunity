package com.jing.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class WebInterceptor implements WebMvcConfigurer {

    @Autowired
    SecurityInterceptor securityInterceptor;
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("index");
//    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 本来应该是new 一个 拦截器，但是new 的拦截器无法注入repository 中的user数据
        // https://blog.csdn.net/HELLOMRP/article/details/79736502
        registry.addInterceptor(securityInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/", "/index.html", "/static", "/callback", "/publish",
                        "/**/*.css",
                        "/**/*.js", "/**/*.png", "/**/*.jpg",
                        "/**/*.jpeg", "/**/*.gif", "/**/fonts/*"
                        );
    }
}
