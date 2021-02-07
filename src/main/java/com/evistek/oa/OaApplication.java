package com.evistek.oa;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude= SecurityAutoConfiguration.class)
public class OaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OaApplication.class, args);
	}

}
