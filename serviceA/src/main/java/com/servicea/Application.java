package com.servicea;

import com.backbase.buildingblocks.registry.client.api.EnableRegistryClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author Davor Sauer
 */
@SpringBootApplication
@EnableRegistryClient
public class Application extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Stating app...");
        SpringApplication.run(Application.class, args);
    }

}
