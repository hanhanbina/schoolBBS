package com.xu.sladmin.config;

import com.xu.sladmin.common.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * token验证拦截器配置
 *
 * @Name InterceptorConfig
 * @Author 31671
 * @Date 2023-10-21 23:16
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有请求，通过判断token决定是否登录
       registry.addInterceptor(jwtInterceptor())
               .addPathPatterns("")  //将会拦截匹配的路径
               .excludePathPatterns("/user/login","/user/register", "/file/**")  //不拦截匹配的路径
       ;
    }
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

}
