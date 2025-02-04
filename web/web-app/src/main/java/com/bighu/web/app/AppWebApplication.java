package com.bighu.web.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


//@MapperScan("com.bighu.web.app.mapper")
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.bighu.web.app", "com.bighu.common","com.bighu.model"})
public class AppWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppWebApplication.class, args);
	}

}
