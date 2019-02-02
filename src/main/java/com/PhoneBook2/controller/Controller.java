package com.PhoneBook2.controller;

import com.PhoneBook2.service.PhoneBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

public class Controller {

  private PhoneBookService phoneBookService;
  private Logger log = LoggerFactory.getLogger(this.getClass());

  public Controller(PhoneBookService phoneBookService) {
    this.phoneBookService = phoneBookService;
  }

  public void start(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    String attribute = "";
    boolean isTrue = true;
    if (args.length == 0) {
      message();
      System.out.println("What do you want to do? Add parameter");
      attribute = scanner.nextLine();
    }
    while(isTrue) {
      if (attribute.equals("-a")) {
        phoneBookService.createContact();
        System.out.println("Whats next?");
        attribute = scanner.nextLine();
      } else if (attribute.equals("-l")) {
        phoneBookService.displayPhoneBook(phoneBookService.allContactsStored());
        System.out.println("Whats next?");
        attribute = scanner.nextLine();
      } else if (attribute.equals("-exit")) {
        System.exit(0);
      } else {
        System.exit(1);
      }
    }

  }

  public static void message() {
    System.out.println("PhoneBook application");
    System.out.println("=============================");
    System.out.println();
    System.out.println("Command line arguments:");
    System.out.println(" -l   Lists all the contacts\n" +
        " -a   Adds a new contact\n" +
        " -r   Removes an contact\n" +
        " -u   Update a Contact\n" +
        " -exit Exit program\n");
  }
}
