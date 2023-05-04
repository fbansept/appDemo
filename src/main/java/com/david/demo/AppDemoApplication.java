package com.david.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class AppDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppDemoApplication.class, args);
    }

    @PostConstruct
    public void init() {
         TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
