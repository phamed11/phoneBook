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
import java.util.stream.Collectors;


@Service
public class PhoneBookServiceImpl implements PhoneBookService {

  private Logger log = LoggerFactory.getLogger(this.getClass());
  private static final String UPLOADED_FOLDER = System.getProperty("user.home") + "/phonebook/"; //Ony use in production for local machine savings...
  private static final String PHONEBOOK_FILE = "phoneBook.json";
  private static final String LOCAL_FOLDER = "Assets/";
  private static final String JSON_FILE = LOCAL_FOLDER + PHONEBOOK_FILE;
  private static final Path PATH_JSON_FILE = Paths.get(JSON_FILE);

  public void createContactIfNotExists(Contact contact) throws IOException {
    if (ifContactExists(contact)) {
      log.error("Contact already exists!");
    } else {
      saveContacts(contact);
      log.info("New contact created with name: " + contact.getFirstName() + " " + contact.getLastName());
    }
  }

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
    createContactIfNotExists(resultContact);
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
      log.error("Wrong file format or empty file!");
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
      scanner.nextLine();
      for (int i = 0; i < numberOfAddresses; i++) {
        System.out.println("Add details for address no." + (i + 1));
        System.out.print("Enter country: ");
        address.setCountry(scanner.nextLine());
        System.out.print("Enter city: ");
        address.setCity(scanner.nextLine());
        System.out.print("Enter street: ");
        address.setStreet(scanner.nextLine());
        System.out.print("Enter zip code: ");
        address.setZipCode(scanner.nextLine());
        resultAddresses.add(address);
      }
    return resultAddresses;
  }

  private List<String> createPhoneNumbers() {
    Scanner scanner = new Scanner(System.in);
    int numberOfPhoneNumbers = 0;
    boolean isTrue = true;
    List<String> phoneNumberList = new ArrayList<>();
    System.out.print("How many phone numbers do you want to add?: ");
    while (isTrue) {
      try {
        numberOfPhoneNumbers = scanner.nextInt();
        isTrue = false;
      } catch (InputMismatchException e) {
        System.out.print("Wrong format, add number: ");
        scanner.nextLine();
      }
    }
      for (int i = 0; i < numberOfPhoneNumbers; i++) {
        System.out.print("Add phone number(must be 11 numbers) no." + (i + 1) + ": ");
        String number = scanner.next();
        if (number.length() != 11) {
          System.out.println("Phone number must be 11 digits long!");
          i--;
        } else {
          phoneNumberList.add(formatPhoneNumber(number));
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

  private void saveContacts(Contact contacts) throws IOException {
    List<Contact> contactList = new ArrayList<>();
    Type collectionType = new TypeToken<List<Contact>>() {
    }.getType();
    Path path = Paths.get(JSON_FILE);
    if (!Files.exists(path)) {
      contactList.add(contacts);
      String toJsonMany = new Gson().toJson(contactList, collectionType);
      Files.write(path, toJsonMany.getBytes());
      return;
    }
    File file = new File(JSON_FILE);
    if (file.length() == 0) {
      contactList.add(contacts);
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
    if (file.exists() && file.length() != 0) {
      if (phoneBook.size() == 0) {
        log.info("Nothing to display!");
      }
      for (int i = 0; i < phoneBook.size(); i++) {
        if (phoneBook.get(i).getTitle() == null) {
          System.out.println("Contact's name: " + phoneBook.get(i).getFirstName() + " " + phoneBook.get(i).getLastName());
        } else {
          System.out.println("Contact's name: " + phoneBook.get(i).getTitle() + " " + phoneBook.get(i).getFirstName() + " " + phoneBook.get(i).getLastName());
        }
        System.out.println("Contact's birthday: " + phoneBook.get(i).getDateOfBirth());
        for (int j = 0; j < phoneBook.get(i).getPhoneNumber().size(); j++) {
          System.out.println("Contact's phone number no." + (j + 1) + ": " + phoneBook.get(i).getPhoneNumber().get(j));
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
    } else if (file.exists() && file.length() == 0) {
      log.info("File is empty!");
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

  private void saveContactList(List<Contact> allContacts) throws IOException {
    Type collectionType = new TypeToken<List<Contact>>() {
    }.getType();
    String toJsonMany = new Gson().toJson(allContacts, collectionType);
    Files.write(PATH_JSON_FILE, toJsonMany.getBytes());
  }

  private boolean ifContactExists(Contact contact) {
    if (fileNotExistsOrEmpty()) {
      return false;
    }
    List<Contact> allContacts = allContactsStored();
    for (Contact contactCheck : allContacts) {
      if ((contact.getFirstName() + contact.getLastName()).equals((contactCheck.getFirstName() + contactCheck.getLastName()))) {
        return true;
      }
    }
    return false;
  }

  private boolean fileNotExistsOrEmpty() {
    if (!Files.exists(PATH_JSON_FILE)) {
      return true;
    }
    List<Contact> allContacts = allContactsStored();
    if (allContacts == null || allContacts.size() == 0) {
      return true;
    }
    return false;
  }

  public void removeContact(String fullName) throws IOException {
    if (fileNotExistsOrEmpty()) {
      log.error("File not exits or no contacts saved!");
      return;
    }
    List<Contact> allContacts = allContactsStored();
    for (int i = 0; i < allContactsStored().size(); i++) {
      if (fullName.equals(allContactsStored().get(i).getFirstName() + allContactsStored().get(i).getLastName())) {
        log.info("Contact " + allContacts.get(i).getFirstName() + " " + allContacts.get(i).getLastName() + " removed from phonebook!");
        allContacts.remove(allContacts.get(i));
        saveContactList(allContacts);
        return;
      }
    }
    log.error("No contact with that name exits in phone book");
  }

  public List<Contact> findByName(String firstName) {
    if (firstName == null || "".equals(firstName)) {
      log.error("Argument is not applicable  " + firstName);
    }

    List<Contact> foundContacts = allContactsStored().stream()
        .filter(contact -> contact.getFirstName().equals(firstName))
        .collect(Collectors.toList());

    if (foundContacts.size() == 0) {
      log.error("Cannot find contact with name " + firstName);
    }
    return foundContacts;
  }
}









