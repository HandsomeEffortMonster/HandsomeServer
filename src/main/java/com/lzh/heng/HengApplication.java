package com.lzh.heng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HengApplication {

	public static void main(String[] args) {
		SpringApplication.run(HengApplication.class, args);
	}
}
