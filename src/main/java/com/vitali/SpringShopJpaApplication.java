package com.vitali;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringShopJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringShopJpaApplication.class, args);
		log.info("app-started");

	}

}
