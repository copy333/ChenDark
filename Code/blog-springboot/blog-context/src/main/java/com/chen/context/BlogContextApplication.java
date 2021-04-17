package com.chen.context;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.chen.context.mapper")
@ComponentScan(basePackages = {"com.chen"})
public class BlogContextApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogContextApplication.class, args);
    }

}
