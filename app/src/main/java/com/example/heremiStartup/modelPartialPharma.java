package com.example.heremiStartup;

public class modelPartialPharma {
    int pharmacy_id;
    String pharmacy_name;
    double med_price;

    public modelPartialPharma(int pharmacy_id, String pharmacy_name, double med_price) {
        this.pharmacy_id = pharmacy_id;
        this.pharmacy_name = pharmacy_name;
        this.med_price = med_price;
    }

    public modelPartialPharma() {
    }

    @Override
    public String toString() {
        return "modelPartialPharma{" +
                "pharmacy_id=" + pharmacy_id +
                ", pharmacy_name='" + pharmacy_name + '\'' +
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

    public double getMed_price() {
        return med_price;
    }

    public void setMed_price(double med_price) {
        this.med_price = med_price;
    }
}
