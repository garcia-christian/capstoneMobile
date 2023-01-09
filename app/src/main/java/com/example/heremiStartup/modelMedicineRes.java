package com.example.heremiStartup;

public class modelMedicineRes {

    private int c_med_id;
    private String med_name;
    private int med_quantity;

    public modelMedicineRes() {
    }

    @Override
    public String toString() {
        return  med_name;
    }

    public modelMedicineRes(int c_med_id, String med_name, int med_quantity) {
        this.c_med_id = c_med_id;
        this.med_name = med_name;
        this.med_quantity = med_quantity;
    }

    public int getC_med_id() {
        return c_med_id;
    }

    public void setC_med_id(int c_med_id) {
        this.c_med_id = c_med_id;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public int getMed_quantity() {
        return med_quantity;
    }

    public void setMed_quantity(int med_quantity) {
        this.med_quantity = med_quantity;
    }
}
