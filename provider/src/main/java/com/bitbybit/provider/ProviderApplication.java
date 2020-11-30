package com.bitbybit.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RestController
public class ProviderApplication {
	AtomicInteger num = new AtomicInteger();

	@GetMapping("provider/provider")
	public Integer home() {
		return num.incrementAndGet();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProviderApplication.class, args);
	}

}
