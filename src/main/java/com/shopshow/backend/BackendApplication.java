package com.shopshow.backend;

import com.shopshow.backend.dao.SellerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class BackendApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BackendApplication.class, args);
		SellerRepository sellerRepository = context.getBean(SellerRepository.class);
		System.out.println("Started!!!!");
	}

}
