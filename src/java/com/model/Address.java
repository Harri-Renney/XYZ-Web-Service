package com.model;

/**
 *
 * @author win8.1
 */
public class Address {

    int houseNumber;
    String street;
    String city;
    String county;
    String postCode;

    public Address(int houseNumber, String street, String city, String county, String postCode) {
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.county = county;
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return houseNumber + ", " + street + ", " + city + ", " + county + ", " + postCode;
    }

    
}
