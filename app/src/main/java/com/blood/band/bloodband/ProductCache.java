package com.blood.band.bloodband;

/**
 * Created by Pranto on 9/23/2017.
 */

public class ProductCache {
    private String id;
    private String name;
    private String phn;
    private String gender;
    private String mail;
    private String address;
    private String bd;
    private String ld;
    private String interest;
    private String count;
    private String grp;
    private String upojela;
    private String rating;

    public ProductCache(String id, String name, String phn, String gender, String mail, String address, String bd, String ld, String interest, String count, String grp, String upojela, String rating) {
        this.id = id;
        this.name = name;
        this.phn = phn;
        this.gender = gender;
        this.mail = mail;
        this.address = address;
        this.bd = bd;
        this.ld = ld;
        this.interest = interest;
        this.count = count;
        this.grp = grp;
        this.upojela = upojela;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String image) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPhn() {
        return phn;
    }

    public String getGender() {
        return gender;
    }

    public String getMail() {
        return mail;
    }

    public String getAddress() {
        return address;
    }

    public String getBirth() {
        return bd;
    }

    public String getLastDonate() {
        return ld;
    }

    public String getInterest() {
        return interest;
    }

    public String getCounter() {
        return count;
    }

    public String getGroup() {
        return grp;
    }

    public String getUpazila() {
        return upojela;
    }

    public String getRating() {
        return rating;
    }
}
