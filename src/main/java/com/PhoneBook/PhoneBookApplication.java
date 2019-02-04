package com.PhoneBook;

import com.PhoneBook.controller.Start;
import com.PhoneBook.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PhoneBookApplication implements CommandLineRunner {

  private PhoneBookService phoneBookService;

  @Autowired
  public PhoneBookApplication(PhoneBookService phoneBookService) {
    this.phoneBookService = phoneBookService;
  }

  public static void main(String[] args) {
    SpringApplication.run(PhoneBookApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    new Start(args, phoneBookService);

  }
}

