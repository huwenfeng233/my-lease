package com.bighu.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.beans.Beans;

@SpringBootTest
class WebAdminApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;
	@Test
	void contextLoads() {
		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames)
		{
			System.out.println(beanDefinitionName);
		}
	}

}
