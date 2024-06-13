package com.xu.sladmin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Name JacksonConfig格式化日期
 * @Author 31671
 * @Date 2023-11-01 16:20
 * 所有的 LocalDateTime 类型的时间都会按照指定的格式进行格式化。
 */
@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Java 8 时间模块
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // 添加 LocalDateTime 序列化器
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // 添加 Java 8 时间模块
        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }
}
