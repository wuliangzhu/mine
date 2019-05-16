package com.mye.mine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MineApplication.class, args);
    }

}
