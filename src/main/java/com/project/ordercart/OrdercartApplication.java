package com.project.ordercart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.project.*")
@EntityScan("com.project.entity")
@EnableJpaRepositories("com.project.repository")
public class OrdercartApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdercartApplication.class, args);
		System.out.println("Hello");
	}

}
