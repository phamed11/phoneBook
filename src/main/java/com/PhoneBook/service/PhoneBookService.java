package com.PhoneBook.service;

import com.PhoneBook.exception.ContactNotFoundException;
import com.PhoneBook.exception.ContactWrongParameterException;
import com.PhoneBook.models.Contact;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public interface PhoneBookService {

  Contact createContact(Scanner scanner) throws IOException;
  List<Contact> allContactsStored();
  void displayPhoneBook(List<Contact> phoneBook);
  void removeContact(String fullName) throws IOException;
  List<Contact> findByName(String firstName) throws ContactNotFoundException, ContactWrongParameterException;
}
