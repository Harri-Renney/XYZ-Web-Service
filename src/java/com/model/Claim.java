/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.util.Date;

/**
 *
 * @author Owner
 */
public class Claim {
    
    private int id;
    
    private String memId;
    
    private Date date;
    
    private String rationale;
    
    private String status;
    
    private float amount;

    public Claim(int id, String memId, Date date, String rationale, String status, float amount) {
        this.id = id;
        this.memId = memId;
        this.date = date;
        this.rationale = rationale;
        this.status = status;
        this.amount = amount;
    }
    
    public boolean yearPassed() {
        Date currentDate = new Date(); // current date
        long difference = currentDate.getTime() - date.getTime();
        long differenceDays = difference / (1000 * 60 * 60 * 24);

        return differenceDays > 365;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRationale() {
        return rationale;
    }

    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
    
    
    
}
