package com.boberclinic.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Backend entry point.
@SpringBootApplication
public class BoberBackendApplication {

    // Starts Spring Boot on localhost:8080.
    public static void main(String[] args) {
        SpringApplication.run(BoberBackendApplication.class, args);
    }
}
