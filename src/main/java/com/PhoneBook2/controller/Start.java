package com.PhoneBook2.controller;

import com.PhoneBook2.service.PhoneBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Scanner;

public class Start {

  private PhoneBookService phoneBookService;
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public Start(String[] args, PhoneBookService phoneBookService) throws IOException {
    this.phoneBookService = phoneBookService;

    Scanner scanner = new Scanner(System.in);
    String attribute = "";
    boolean isTrue = true;
    if (args.length == 0) {
      log.info("PhoneBook app started...");
      message();
      System.out.println("What do you want to do? Add parameter");
      attribute = scanner.nextLine();
    }
    while (isTrue) {
      if (attribute.equals("-a")) {
        phoneBookService.createContact();
        System.out.println("Whats next? use \"-console\" for help!");
        attribute = scanner.nextLine();
      } else if (attribute.equals("-l")) {
        phoneBookService.displayPhoneBook(phoneBookService.allContactsStored());
        System.out.println("Whats next? use \"-console\" for help!");
        attribute = scanner.nextLine();
      } else if (attribute.equals("-exit")) {
        log.info("PhoneBook app exited, goodbye!");
        System.exit(0);
      } else if (attribute.equals("-r")) {
        System.out.println("Which contact do you want to remove? (\"firstnamelastname\"): ");
        String toRemove = scanner.nextLine();
        phoneBookService.removeContact(toRemove);
        System.out.println("Whats next? use \"-console\" for help!");
        attribute = scanner.nextLine();
      } else if (attribute.equals("-fFirstName")) {
        System.out.println("Give first name to find: ");
        String firstName = scanner.nextLine();
        phoneBookService.displayPhoneBook(phoneBookService.findByName(firstName));
        System.out.println("Whats next? use \"-console\" for help!");
        attribute = scanner.nextLine();
      } else if (attribute.equals("-console")) {
        message();
        System.out.println("Whats next? use \"-console\" for help!");
        attribute = scanner.nextLine();
      } else {
        System.out.println("Not recognizable command, try again!");
        message();
        System.out.println("Whats next? use \"-console\" for help!");
        attribute = scanner.nextLine();
      }
    }
  }

  private void message() {
    System.out.println("PhoneBook application");
    System.out.println("=============================");
    System.out.println();
    System.out.println("Command line arguments:");
    System.out.println(" -l   Lists all the contacts\n" +
        " -a   Adds a new contact\n" +
        " -r   Removes an contact\n" +
        " -fFirstName  Finds contact by first name\n" +
        " -u   Updates a contact - not implemented yet!!!\n" +
        " -console  Shows possible commands\n" +
        " -exit Exits program\n");
  }

  private void nextArgument(String attribute, Scanner scanner) {
    System.out.println("Whats next? use \"-console\" for help!");
    attribute = scanner.nextLine();
  }
}
