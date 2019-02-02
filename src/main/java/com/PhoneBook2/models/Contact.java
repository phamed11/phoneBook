package com.PhoneBook2.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Contact {

  private String title;
  private String firstName;
  private String lastName;
  private String dateOfBirth;
  private List<String> phoneNumber;
  private List<Address> address;

  public Contact(String title, String firstName, String lastName, String dateOfBirth, List<String> phoneNumberList, List<Address> addressList) {
    this.title = title;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.phoneNumber = phoneNumberList;
    this.address = addressList;
  }

  public Contact(String firstName, String lastName, String dateOfBirth, List<String> phoneNumberList, List<Address> addressList) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.phoneNumber = phoneNumberList;
    this.address = addressList;
  }

  @Override
  public String toString() {
    return "Contact{" +
        "title='" + title + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", dateOfBirth=" + dateOfBirth +
        ", phoneNumberList=" + phoneNumber +
        ", addressList=" + address +
        '}';
  }
}
