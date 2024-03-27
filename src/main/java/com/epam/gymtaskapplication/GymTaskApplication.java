package com.epam.gymtaskapplication;

import com.epam.gymtaskapplication.dao.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class GymTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymTaskApplication.class, args);
	}

}
