package com.example.heremiStartup;

public class modelMedicine {


    private int prod_id;
    private String med_name;
    private int med_quantity;
    private int type_id;

    public modelMedicine(int medID, int prod_id, String med_name, int med_quantity, int type_id) {

        this.prod_id = prod_id;
        this.med_name = med_name;
        this.med_quantity = med_quantity;
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "modelMedicine{" +
                ", prod_id=" + prod_id +
                ", med_name='" + med_name + '\'' +
                ", med_quantity=" + med_quantity +
                ", type_id=" + type_id +
                '}';
    }

    public modelMedicine() {

    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
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

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }
}
