package com.weldon.supershop;

import org.springframework.boot.SpringApplication;

public class TestSupershopApplication {

	public static void main(String[] args) {
		SpringApplication.from(SupershopApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
