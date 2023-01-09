package com.example.heremiStartup;

public class modelPharmacy {
    int pharmacy_id;
    String pharmacy_name;
    String pharmacy_location;
    String pharmacy_lat;
    String pharmacy_lng;
    String logo;
    public modelPharmacy() {
    }

    public modelPharmacy(int pharmacy_id, String pharmacy_name, String pharmacy_location, String pharmacy_lat, String pharmacy_lng) {
        this.pharmacy_id = pharmacy_id;
        this.pharmacy_name = pharmacy_name;
        this.pharmacy_location = pharmacy_location;
        this.pharmacy_lat = pharmacy_lat;
        this.pharmacy_lng = pharmacy_lng;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getPharmacy_lat() {
        return pharmacy_lat;
    }

    public void setPharmacy_lat(String pharmacy_lat) {
        this.pharmacy_lat = pharmacy_lat;
    }

    public String getPharmacy_lng() {
        return pharmacy_lng;
    }

    public void setPharmacy_lng(String pharmacy_lng) {
        this.pharmacy_lng = pharmacy_lng;
    }
}
