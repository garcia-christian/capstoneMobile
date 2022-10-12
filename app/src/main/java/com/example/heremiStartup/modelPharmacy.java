package com.example.heremiStartup;

public class modelPharmacy {
    int pharmacy_id;
    String pharmacy_name;
    String pharmacy_location;

    public modelPharmacy() {
    }

    public int getPharmacy_id() {
        return pharmacy_id;
    }

    public void setPharmacy_id(int pharmacy_id) {
        this.pharmacy_id = pharmacy_id;
    }

    public String getPharmacy_name() {
        return pharmacy_name;
    }

    public void setPharmacy_name(String pharmacy_name) {
        this.pharmacy_name = pharmacy_name;
    }

    public String getPharmacy_location() {
        return pharmacy_location;
    }

    public void setPharmacy_location(String pharmacy_location) {
        this.pharmacy_location = pharmacy_location;
    }

    public modelPharmacy(int pharmacy_id, String pharmacy_name, String pharmacy_location) {
        this.pharmacy_id = pharmacy_id;
        this.pharmacy_name = pharmacy_name;
        this.pharmacy_location = pharmacy_location;
    }
}
