
package com.model;

import java.util.Random;

/**
 *
 * @author BMT
 */


public class User {

    private String id;
    private String password;
    private String status;
    
    public User()
    { }

    public User(String id, String password, String status) {
        this.id = id;
        this.password = password;
        this.status = status;
    }
    
    public User(User u) {
        this.id = u.id;
        this.password = u.password;
        this.status = u.status;
    }
    
    public static String createPassword() {
        String password = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder builder = new StringBuilder();
        Random rnd = new Random();
        while (builder.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * password.length());
            builder.append(password.charAt(index));
        }
        String finalPass = builder.toString();
        System.out.println(finalPass);
        return finalPass;
    }    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }   
       
    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", password=" + password + ", status=" + status + '}';
    }
}
