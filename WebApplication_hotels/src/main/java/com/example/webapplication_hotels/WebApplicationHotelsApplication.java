package com.example.webapplication_hotels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.webapplication_hotels", "Reservation"})
public class WebApplicationHotelsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplicationHotelsApplication.class, args);
    }

}
