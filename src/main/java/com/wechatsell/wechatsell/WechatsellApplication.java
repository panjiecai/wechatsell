package com.wechatsell.wechatsell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.wechatsell.wechatsell.repository")
public class WechatsellApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatsellApplication.class, args);
    }

}
