package com.arisen.shenpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude=org.activiti.spring.boot.SecurityAutoConfiguration.class)
@EnableAspectJAutoProxy //Enable Aop
public class ShenpiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShenpiApplication.class, args);
	}

}
