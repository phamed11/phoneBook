package com.PhoneBook2.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhoneNumber {

  private String phoneNumber;

  public PhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return "PhoneNumber{" +
        "phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}