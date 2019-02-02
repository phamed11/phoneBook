package com.PhoneBook2.service;

import com.PhoneBook2.models.Contact;

import java.io.IOException;
import java.util.List;

public interface PhoneBookService {

  Contact createContact() throws IOException;
  List<Contact> allContactsStored();
  void displayPhoneBook(List<Contact> phoneBook);
  void removeContact(String fullName) throws IOException;
}
