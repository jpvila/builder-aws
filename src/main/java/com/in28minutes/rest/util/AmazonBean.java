package com.in28minutes.rest.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonBean {
	
	@Bean
	DynamoDBBean carga() {
		return new DynamoDBBean("hola");
	}

}
