package com.PhoneBook.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {

  private String country;
  private String zipCode;
  private String city;
  private String street;

  public Address(String country, String zipCode, String city, String street) {
    this.country = country;
    this.zipCode = zipCode;
    this.city = city;
    this.street = street;
  }

  @Override
  public String toString() {
    return "Address{" +
        "country='" + country + '\'' +
        ", zipCode='" + zipCode + '\'' +
        ", city='" + city + '\'' +
        ", street='" + street + '\'' +
        '}';
  }
}


