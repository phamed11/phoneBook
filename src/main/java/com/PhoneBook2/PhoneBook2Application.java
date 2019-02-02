package com.PhoneBook2;

import com.PhoneBook2.controller.Controller;
import com.PhoneBook2.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhoneBook2Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PhoneBook2Application.class, args);
	}

	private PhoneBookService phoneBookService;

	@Autowired
	public PhoneBook2Application(PhoneBookService phoneBookService) {
		this.phoneBookService = phoneBookService;
	}

	@Override
	public void run(String... args) throws Exception {

		Controller controller = new Controller(phoneBookService);
		controller.start(args);
	}
}

