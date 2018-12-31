package com.blood.band.bloodband;

/**
 * Visit website http://www.whats-online.info
 **/

public class Contact {

    //private variables
    int id;
    String donate, fname, fadd, fgrp, femail, fphone;
    byte[] img;

    // Empty constructor
    public Contact() {

    }

    // constructor
    public Contact(int id, String donate, String fname, String fadd, String fgrp, String femail, String fphone, byte[] img) {
        this.id = id;
        this.donate = donate;
        this.fname = fname;
        this.fadd = fadd;
        this.fgrp = fgrp;
        this.femail = femail;
        this.fphone = fphone;
        this.img = img;

    }

    // constructor
    public Contact(String donate, String fname, String fadd, String fgrp, String femail, String fphone, byte[] img) {

        this.donate = donate;
        this.fname = fname;
        this.fadd = fadd;
        this.fgrp = fgrp;
        this.femail = femail;
        this.fphone = fphone;
        this.img = img;

    }

    // getting ID
    public int getID() {
        return this.id;
    }

    // setting id
    public void setID(int id) {
        this.id = id;
    }

    // getting first name
    public String getDATE() {
        return this.donate;
    }

    public String getFName() {
        return this.fname;
    }

    public String getFAdd() {
        return this.fadd;
    }

    public String getFGrp() {
        return this.fgrp;
    }

    public String getFEmail() {
        return this.femail;
    }

    public String getFPhone() {
        return this.fphone;
    }

    // setting first name
    public void setDonate(String donate) {
        this.donate = donate;
    }

    public void setFName(String fname) {
        this.fname = fname;
    }

    public void setFAdd(String fadd) {
        this.fadd = fadd;
    }

    public void setFGrp(String fgrp) {
        this.fgrp = fgrp;
    }

    public void setFEmail(String femail) {
        this.femail = femail;
    }

    public void setFPhone(String fphone) {
        this.fphone = fphone;
    }

    //getting profile pic
    public byte[] getImage() {
        return this.img;
    }

    //setting profile pic

    public void setImage(byte[] b) {
        this.img = b;
    }

}

