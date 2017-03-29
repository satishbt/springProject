package com.app.stock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.app.stock.service.FundService;

@SpringBootApplication
public class MoneycontrolApplication {

    public static void main(String[] args) {
	ApplicationContext context = SpringApplication.run(MoneycontrolApplication.class, args);
	FundService fundservice = context.getBean(FundService.class);
    }

    @Component
    public class MyClass {

	@Value("${url}")
	public String url;

    }
}
