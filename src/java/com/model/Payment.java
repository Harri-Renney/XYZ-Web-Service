/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Owner
 */
public class Payment {
    private int id;
    
    private String memId;
    
    private String typeOfPayment;
    
    private float amount;
    
    private Time time;
    
    private Date date;

    public Payment(int id, String memid, String typeOfPayment, float amount, Time time, Date date) {
        this.id = id;
        this.memId = memid;
        this.typeOfPayment = typeOfPayment;
        this.amount = amount;
        this.time = time;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemid() {
        return memId;
    }

    public void setMemid(String memid) {
        this.memId = memid;
    }

    public String getTypeOfPayment() {
        return typeOfPayment;
    }

    public void setTypeOfPayment(String typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
}
