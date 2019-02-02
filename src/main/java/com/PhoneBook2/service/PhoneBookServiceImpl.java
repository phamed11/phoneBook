package com.PhoneBook2.service;

import com.PhoneBook2.models.Address;
import com.PhoneBook2.models.Contact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
public class PhoneBookServiceImpl implements PhoneBookService {

  private Logger log = LoggerFactory.getLogger(this.getClass());
  private static final String UPLOADED_FOLDER = System.getProperty("user.home") + "/phonebook/";
  private static final String PHONEBOOK_FILE = "phoneBook.json";
  private static final String LOCAL_FOLDER = "Assets/";
  private static final String JSON_FILE = LOCAL_FOLDER + PHONEBOOK_FILE;


  public Contact createContact() throws IOException {
    createDirectoryIfNotExists();
    Contact resultContact = new Contact();
    System.out.println("Create contact: ");
    resultContact.setTitle(createTitle());
    resultContact.setFirstName(createFirstName());
    resultContact.setLastName(createLastName());
    resultContact.setDateOfBirth(createDateOfBirth());
    resultContact.setPhoneNumber(createPhoneNumbers());
    resultContact.setAddress(createAddresses());
    saveContacts(resultContact);
    log.info("New contact created with name: " + resultContact.getFirstName() + " " + resultContact.getLastName());
    return resultContact;
  }

  public List<Contact> allContactsStored() {
    String content = "";
    try {
      content = new String(Files.readAllBytes(Paths.get(JSON_FILE)), "UTF-8");
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
      } else if (title.equals("Mr") || title.equals("Mrs") || title.equals("Ms")) {
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

//  private void createFileIfNotExists() throws IOException {
//    Path path = Paths.get(LOCAL_FOLDER + PHONEBOOK_FILE);
//
//    if (!Files.exists(path)) {
//      Files.createFile(path);
//      log.info("phoneBook file created");
//    } else {
//      log.info("phoneBook file already exists");
//    }
//  }

  private void saveContacts(Contact contacts) throws IOException {
    List<Contact> contactList = new ArrayList<>();
    Type collectionType = new TypeToken<List<Contact>>() {
    }.getType();
    Path path = Paths.get(JSON_FILE);
    if (!Files.exists(path)) {
      String toJsonOne = contactToJson(contacts);
      Contact one = jsonToContact(toJsonOne);
      contactList.add(one);
      String toJsonMany = new Gson().toJson(contactList, collectionType);
      Files.write(path, toJsonMany.getBytes());
      return;
    }
    File file = new File(JSON_FILE);
    if (file.length() == 0 || file.equals(null)) {
      String toJsonOne = contactToJson(contacts);
      Contact one = jsonToContact(toJsonOne);
      contactList.add(one);
      String toJsonMany = new Gson().toJson(contactList, collectionType);
      Files.write(path, toJsonMany.getBytes());
      return;
    }
    contactList = allContactsStored();
    contactList.add(contacts);
    String toJsonMany = new Gson().toJson(contactList, collectionType);
    Files.write(path, toJsonMany.getBytes());
  }

  public void displayPhoneBook(List<Contact> phoneBook) {
    File file = new File(JSON_FILE);
    if (file.exists()) {
      for (int i = 0; i < phoneBook.size(); i++) {
        if (phoneBook.get(i).getTitle() == null) {
          System.out.println("Contact's name: " + phoneBook.get(i).getFirstName() + " " + phoneBook.get(i).getLastName());
        } else {
          System.out.println("Contact's name: " + phoneBook.get(i).getTitle() + " " + phoneBook.get(i).getFirstName() + " " + phoneBook.get(i).getLastName());
        }
        System.out.println("Contact's birthday: " + phoneBook.get(i).getDateOfBirth());
        for (int j = 0; j < phoneBook.get(i).getPhoneNumber().size(); j++) {
          System.out.println("Contact's phonenumber no." + (j + 1) + ": " + phoneBook.get(i).getPhoneNumber().get(j));
        }
        for (int j = 0; j < phoneBook.get(i).getAddress().size(); j++) {
          System.out.println("Contact's address no." + (j + 1) + ":");
          System.out.println("Contact's country: " + phoneBook.get(i).getAddress().get(j).getCountry());
          System.out.println("Contact's city: " + phoneBook.get(i).getAddress().get(j).getCity());
          System.out.println("Contact's street: " + phoneBook.get(i).getAddress().get(j).getStreet());
          System.out.println("Contact's zipcode: " + phoneBook.get(i).getAddress().get(j).getZipCode());
        }

        System.out.println();
      }
    }
  }

  private String contactToJson(Contact contact) {
    String toJsonOne = new Gson().toJson(contact, Contact.class);
    return toJsonOne;
  }

  private Contact jsonToContact(String json) {
    Contact contact = new Gson().fromJson(json, Contact.class);
    return contact;
  }
}








