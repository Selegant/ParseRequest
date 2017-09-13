package com.example.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by WT on 2017/9/13.
 */
@SpringBootApplication
@RestController
public class HttpApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(HttpApplicationTest.class, args);
    }
}
