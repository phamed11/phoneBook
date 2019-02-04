package com.PhoneBook.exception;

public class ContactNotFoundException extends ContactServiceException {

  public ContactNotFoundException(String message) {
    super(message);
  }

  public ContactNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
