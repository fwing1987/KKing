package com.kking.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.kking")
@MapperScan("com.kking.*.mapper")
@EnableTransactionManagement
public class AdminApplication {
    static Logger log = LoggerFactory.getLogger(AdminApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(AdminApplication.class, args);

    }

}

