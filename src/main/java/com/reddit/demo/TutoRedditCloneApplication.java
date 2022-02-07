package com.reddit.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.reddit.demo.config.SwaggerConfiguration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class TutoRedditCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutoRedditCloneApplication.class, args);
	}

}
