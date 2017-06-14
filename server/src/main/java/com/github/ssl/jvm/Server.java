package com.github.ssl.jvm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Server {
    @GetMapping
    public String root() {
        return "It works!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
