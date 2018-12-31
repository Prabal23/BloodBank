package com.blood.band.bloodband;

/**
 * Created by Pranto on 9/23/2017.
 */

public class ProductBloodReqReport {
    private String id;
    private String name;
    private String phn;
    private String gender;
    private String mail;
    private String address;
    private String bd;
    private String ld;
    private String grp;
    private String type;
    private String upojela;
    private String upo;

    public ProductBloodReqReport(String id, String name, String phn, String gender, String mail, String address, String bd, String ld, String grp, String type, String upojela, String upo) {
        this.id = id;
        this.name = name;
        this.phn = phn;
        this.gender = gender;
        this.mail = mail;
        this.address = address;
        this.bd = bd;
        this.ld = ld;
        this.grp = grp;
        this.type = type;
        this.upojela = upojela;
        this.upo = upo;
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

    public String getGroup() {
        return grp;
    }

    public String getType() {
        return type;
    }

    public String getUpazila() {
        return upojela;
    }

    public String getUpo() {
        return upo;
    }
}
