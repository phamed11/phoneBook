package com.PhoneBook2;

import com.PhoneBook2.controller.Start;
import com.PhoneBook2.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PhoneBook2Application implements CommandLineRunner {

  private PhoneBookService phoneBookService;

  @Autowired
  public PhoneBook2Application(PhoneBookService phoneBookService) {
    this.phoneBookService = phoneBookService;
  }

  public static void main(String[] args) {
    SpringApplication.run(PhoneBook2Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    new Start(args, phoneBookService);

  }
}

