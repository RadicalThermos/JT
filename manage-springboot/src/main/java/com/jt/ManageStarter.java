package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jt.manage.mapper")
public class ManageStarter {

	public static void main(String[] args) {
		SpringApplication.run(ManageStarter.class, args);
	}
}
