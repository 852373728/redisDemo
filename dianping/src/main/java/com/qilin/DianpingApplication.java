package com.qilin;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qilin.mapper")
public class DianpingApplication {
    public static void main(String[] args) {
        SpringApplication.run(DianpingApplication.class, args);
    }
}