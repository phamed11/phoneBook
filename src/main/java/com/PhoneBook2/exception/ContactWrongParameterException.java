package com.PhoneBook2.exception;

public class ContactWrongParameterException extends ContactServiceException {

  public ContactWrongParameterException(String message) {
    super(message);
  }

  public ContactWrongParameterException(String message, Throwable cause) {
    super(message, cause);
  }
}
