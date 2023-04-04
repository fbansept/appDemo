package com.david.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AppDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppDemoApplication.class, args);
    }

}