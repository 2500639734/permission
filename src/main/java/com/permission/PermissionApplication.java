package com.permission;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.permission.mapper")
@Slf4j
public class PermissionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PermissionApplication.class, args);
	}

}
