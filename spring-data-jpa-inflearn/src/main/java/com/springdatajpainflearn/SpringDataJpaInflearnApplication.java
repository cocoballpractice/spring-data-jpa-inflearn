package com.springdatajpainflearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringDataJpaInflearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaInflearnApplication.class, args);
    }

}
