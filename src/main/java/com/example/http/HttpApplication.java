package com.example.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HttpApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(HttpApplication.class, args);
		System.out.println("HelloWorld");
	}
}
