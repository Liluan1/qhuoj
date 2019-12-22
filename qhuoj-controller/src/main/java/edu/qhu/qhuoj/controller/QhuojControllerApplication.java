package edu.qhu.qhuoj.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {"edu.qhu.qhuoj"})
@EnableJpaRepositories(basePackages = "edu.qhu.qhuoj.repository")
@EntityScan(basePackages = "edu.qhu.qhuoj.entity")
public class QhuojControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QhuojControllerApplication.class, args);
    }

}
