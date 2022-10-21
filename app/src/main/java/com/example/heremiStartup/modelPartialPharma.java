package com.example.heremiStartup;

public class modelPartialPharma {
    int pharmacy_id;
    String pharmacy_name;
    String pharmacy_location;
    String pharmacy_lat;
    String pharmacy_lng;
    String distance;
    double med_price;

    public modelPartialPharma() {
    }

    public modelPartialPharma(int pharmacy_id, String pharmacy_name, String pharmacy_location, String pharmacy_lat, String pharmacy_lng, String distance, double med_price) {
        this.pharmacy_id = pharmacy_id;
        this.pharmacy_name = pharmacy_name;
        this.pharmacy_location = pharmacy_location;
        this.pharmacy_lat = pharmacy_lat;
        this.pharmacy_lng = pharmacy_lng;
        this.distance = distance;
        this.med_price = med_price;
    }

    @Override
    public String toString() {
        return "modelPartialPharma{" +
                "pharmacy_id=" + pharmacy_id +
                ", pharmacy_name='" + pharmacy_name + '\'' +
                ", pharmacy_location='" + pharmacy_location + '\'' +
                ", pharmacy_lat='" + pharmacy_lat + '\'' +
                ", pharmacy_lng='" + pharmacy_lng + '\'' +
                ", distance='" + distance + '\'' +
                ", med_price=" + med_price +
                '}';
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public double getMed_price() {
        return med_price;
    }

    public void setMed_price(double med_price) {
        this.med_price = med_price;
    }
}
