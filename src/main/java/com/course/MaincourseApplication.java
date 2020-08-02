package com.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.course.mapper")//指定扫描接口与映射配置文件
public class MaincourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaincourseApplication.class, args);
    }

}
