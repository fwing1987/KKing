package com.kking.admin;

import com.kking.dao.service.DnChannelService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication(scanBasePackages = "com.kking")
@MapperScan("com.kking.*.mapper")
public class AdminApplication {
    static Logger log = LoggerFactory.getLogger(AdminApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(AdminApplication.class, args);

    }

}

