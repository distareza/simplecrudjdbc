package com.example.simplecrudjdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SimplecrudjdbcApplication {

	@RequestMapping("/")
	public String startPage() {
		return "MyMusic API";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SimplecrudjdbcApplication.class, args);
	}

}
