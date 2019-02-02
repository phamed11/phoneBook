package com.PhoneBook2.exception;

public class ContactAlreadyExists extends ContactServiceException {

  public ContactAlreadyExists(String message) {
    super(message);
  }

  public ContactAlreadyExists(String message, Throwable cause) {
    super(message, cause);
  }
}
