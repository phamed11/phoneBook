package com.PhoneBook2.service;

import com.PhoneBook2.models.Address;
import com.PhoneBook2.models.Contact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;

@Service
public class PhoneBookServiceImpl implements PhoneBookService{

  private Logger log = LoggerFactory.getLogger(this.getClass());
  private static final String UPLOADED_FOLDER = System.getProperty("user.home") + "/phonebook/";
  private static final String PHONEBOOK_FILE = "phoneBook.json";
  private static final String LOCAL_FOLDER = "Assets/";


  public List<Contact> createContact() throws IOException {
    createDirectoryIfNotExists();
    Contact resultContact = new Contact();
    System.out.println("Create contact: ");
    resultContact.setTitle(createTitle());
    resultContact.setFirstName(createFirstName());
    resultContact.setLastName(createLastName());
    resultContact.setDateOfBirth(createDateOfBirth());
    resultContact.setPhoneNumber(createPhoneNumbers());
    resultContact.setAddress(createAddresses());
    List<Contact> allContact = new ArrayList<>();
    allContact.add(resultContact);
    saveContacts(allContact);
    log.info("New contact created with name: " + resultContact.getFirstName() + " " + resultContact.getLastName());
    return allContact;
  }

  public List<Contact> allContactsStored() {
    String content = "";
    try {
      content = new String(Files.readAllBytes(Paths.get(LOCAL_FOLDER + PHONEBOOK_FILE)), "UTF-8");
    } catch (NoSuchFileException e) {
      log.error("File is missing");
    } catch (IOException e) {
      e.printStackTrace();
      log.error("Wrong fileformat or empty file!");
    }
    Type collectionType = new TypeToken<List<Contact>>() {
    }.getType();
    List<Contact> allContacts = new Gson().fromJson(content, collectionType);
    return allContacts;
  }

  private String createFirstName() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter first name: ");
    String firstName = scanner.nextLine();
    return firstName;
  }

  private String createLastName() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter last name: ");
    String lastName = scanner.nextLine();
    return lastName;
  }

  private String createDateOfBirth() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter date of birth: ");
    String dateOfBirth = scanner.nextLine();
    return dateOfBirth;
  }

  private String createTitle() {
    Scanner scanner = new Scanner(System.in);
    boolean isTrue = true;
    System.out.print("Enter title: ");
    String title = scanner.nextLine();
    while (isTrue) {
      if (title.equals("")) {
        return null;
      } else if (title.equals("Mr.") || title.equals("Mrs.") || title.equals("Ms.")) {
        isTrue = false;
      } else {
        System.out.print("Title must be either Mr. or Mrs. or Ms. \n Enter title: ");
        title = scanner.nextLine();
      }
    }
    return title;
  }

  private List<Address> createAddresses() {
    Scanner scanner = new Scanner(System.in);
    Address address = new Address();
    int numberOfAddresses = 0;
    boolean isTrue = true;
    List<Address> resultAddresses = new ArrayList<>();
    System.out.print("How many addresses do you want to add?: ");
    while (isTrue) {
      try {
        numberOfAddresses = scanner.nextInt();
        isTrue = false;
      } catch (InputMismatchException e) {
        System.out.print("Wrong format, add number: ");
        scanner.nextLine();
      }
    }
    if (numberOfAddresses == 1) {
      scanner.nextLine();
      System.out.print("Enter country: ");
      address.setCountry(scanner.nextLine());
      System.out.print("Enter city: ");
      address.setCity(scanner.nextLine());
      System.out.print("Enter street: ");
      address.setStreet(scanner.nextLine());
      System.out.print("Enter zipcode: ");
      address.setZipCode(scanner.nextLine());
      resultAddresses.add(address);
      return resultAddresses;
    } else {
      scanner.nextLine();
      for (int i = 0; i < numberOfAddresses; i++) {
        System.out.println("Add details for address no." + (i + 1));
        System.out.print("Enter country: ");
        address.setCountry(scanner.nextLine());
        System.out.print("Enter city: ");
        address.setCity(scanner.nextLine());
        System.out.print("Enter street: ");
        address.setStreet(scanner.nextLine());
        System.out.print("Enter zipcode: ");
        address.setZipCode(scanner.nextLine());
        resultAddresses.add(address);
      }
    }
    return resultAddresses;
  }

  private List<String> createPhoneNumbers() {
    Scanner scanner = new Scanner(System.in);
    String phoneNumber = "";
    int numberOfPhoneNumbers = 0;
    boolean isTrue = true;
    List<String> phoneNumberList = new ArrayList<>();
    System.out.print("How many phonenumbers do you want to add?: ");
    while (isTrue) {
      try {
        numberOfPhoneNumbers = scanner.nextInt();
        isTrue = false;
      } catch (InputMismatchException e) {
        System.out.print("Wrong format, add number: ");
        scanner.nextLine();
      }
    }
    if (numberOfPhoneNumbers == 1) {
      System.out.print("Add phonenumber(must be 10 numbers): ");
      phoneNumberList.add(formatPhoneNumber(scanner.next()));
      return phoneNumberList;
    } else {
      for (int i = 0; i < numberOfPhoneNumbers; i++) {
        System.out.print("Add phonenumber(must be 10 numbers) no." + (i + 1) + ": ");
        phoneNumberList.add(formatPhoneNumber(scanner.next()));
      }
    }
    return phoneNumberList;
  }

  private String formatPhoneNumber(String phoneNumber) {
    String number = phoneNumber.replaceFirst("(\\d)(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3-$4");
    return number;
  }


  private void createDirectoryIfNotExists() throws IOException {
    Path path = Paths.get(LOCAL_FOLDER);

    if (!Files.exists(path)) {

      Files.createDirectory(path);
      log.info("Local directory for phoneBook created");
    } else {
      log.info("Local directory for phoneBook already exists");
    }
  }

  private void saveContacts(List<Contact> contacts) throws IOException {
    Type collectionType = new TypeToken<List<Contact>>() {
    }.getType();
    String toJson = new Gson().toJson(contacts, collectionType);
    Path path = Paths.get(LOCAL_FOLDER + PHONEBOOK_FILE);
    Files.write(path, toJson.getBytes(), APPEND);
  }
}




