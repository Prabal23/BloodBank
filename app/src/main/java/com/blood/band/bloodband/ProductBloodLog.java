package com.blood.band.bloodband;

/**
 * Created by Pranto on 9/23/2017.
 */

public class ProductBloodLog {
    private String donid;
    private String name;
    private String date;
    private String problem;
    private String place;
    private String age;
    private String gender;

    public ProductBloodLog(String donid, String name, String date, String problem, String place, String age, String gender) {
        this.donid = donid;
        this.name = name;
        this.date = date;
        this.problem = problem;
        this.place = place;
        this.age = age;
        this.gender = gender;
    }

    public String getDonId() {
        return donid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public String getIssue() {
        return problem;
    }

    public String getPlace() {
        return place;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}
