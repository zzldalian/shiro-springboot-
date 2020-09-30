package com.zhangzhilin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ShiroSpringbootSaltApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroSpringbootSaltApplication.class, args);
    }

}
