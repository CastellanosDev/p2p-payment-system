package com.fintech.p2p;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class P2pPaymentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(P2pPaymentSystemApplication.class, args);
	}

}
