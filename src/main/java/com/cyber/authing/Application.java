package com.cyber.authing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.cyber.*"})
@SpringBootApplication
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SpringApplication.run(Application.class, args);
        LOG.info("Application start finished..cost:{}", System.currentTimeMillis() - start);
    }

}
