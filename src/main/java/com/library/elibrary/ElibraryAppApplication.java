package com.library.elibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAutoConfiguration
public class ElibraryAppApplication extends ServletInitializer{

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/ELibrary");
		SpringApplication.run(ElibraryAppApplication.class, args);
	}
	
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/ELibrary").allowedOrigins("*");
			}
		};
	}

}
