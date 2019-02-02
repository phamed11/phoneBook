package com.PhoneBook2.service;

import com.PhoneBook2.models.Contact;

import java.io.IOException;
import java.util.List;

public interface PhoneBookService {

  List<Contact> createContact() throws IOException;
  List<Contact> allContactsStored();
}
