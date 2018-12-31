package com.blood.band.bloodband;

public class ProductDist {
    private String class_code;
    private String class_name;

    public ProductDist(String class_code, String class_name) {
        this.class_code = class_code;
        this.class_name = class_name;
    }

    public String getID() {
        return class_code;
    }

    public void setID(String class_code) {
        this.class_code = class_code;
    }

    public String getName() {
        return class_name;
    }

    public void setName(String class_name) {
        this.class_name = class_name;
    }
}
