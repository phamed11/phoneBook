package com.PhoneBook.exception;

public class ContactServiceException extends RuntimeException {

  public ContactServiceException(String message) {
    super(message);
  }

  public ContactServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
