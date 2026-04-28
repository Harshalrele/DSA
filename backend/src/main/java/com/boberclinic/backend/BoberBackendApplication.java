package com.boberclinic.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This is the main class that starts the Spring Boot backend server.
@SpringBootApplication
public class BoberBackendApplication {

    // Java starts the backend from this main method.
    public static void main(String[] args) {
        // This command starts Spring Boot on localhost:8080.
        SpringApplication.run(BoberBackendApplication.class, args);
    }
}
