package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RestaurantOrderingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantOrderingSystemApplication.class, args);
    }

}
