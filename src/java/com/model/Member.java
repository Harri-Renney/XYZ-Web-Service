
package com.model;

import java.util.Date;


/**
 *
 * @author BMT
 */
public class Member {
    
    //variables
    private String username;
    private String fullName;
    
    private Address address;
    private Date DOB;
    private Date registration;
    
    private String status;
    private float balance;
  
    //Constructor
    public Member(String username, String fullName, Address address, Date DOB, Date registration, String status, float balance) {
        this.username = username;
        this.fullName = fullName;
        this.DOB=DOB;
        this.address = address;
        this.registration = registration;
        this.status = status;
        this.balance = balance;
    }
    
   

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getBalance() {
        return balance;
    }

    //Getters and Setters
    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public Date getRegistration() {
        return registration;
    }

    public void setRegistration(Date registration) {
        this.registration = registration;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String password) {
        this.fullName = password;
    }
    
    
    
    
    
    
}
