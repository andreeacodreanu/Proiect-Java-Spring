package com.springProject.configuration;

import com.springProject.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DBConfiguration {

    private String DriverClassName;
    private String url;
    private String username;
    private String password;

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Profile("dev")
    @Bean
    public String devDatabaseConnection() {
        System.out.println("DEV CONNECTION");
        logger.info("DEV CONNECTION");
        return "DEV CONNECTION";
    }

    @Profile("test")
    @Bean
    public String testDatabaseConnection() {
        System.out.println("TEST CONNECTION");
        logger.info("TEST CONNECTION");
        return "TEST CONNECTION";
    }
}
