package com.github.kersen.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientCredentialsResourceServerApp {
    public static void main(String[] args) {
        SpringApplication.run(ClientCredentialsResourceServerApp.class, args);
    }
}
