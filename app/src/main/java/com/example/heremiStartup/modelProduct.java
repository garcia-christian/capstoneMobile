package com.example.heremiStartup;

public class modelProduct {

    private int global_medicine_id;
    private String global_brand_name;
    private String global_generic_name;
    private String med_cat_desc;
    private int total;
    private String image;

    public modelProduct(int global_medicine_id, String global_brand_name, String global_generic_name, String med_cat_desc, int total, String image) {
        this.global_medicine_id = global_medicine_id;
        this.global_brand_name = global_brand_name;
        this.global_generic_name = global_generic_name;
        this.med_cat_desc = med_cat_desc;
        this.total = total;
        this.image = image;
    }

    public modelProduct() {
    }

    public int getGlobal_medicine_id() {
        return global_medicine_id;
    }

    public void setGlobal_medicine_id(int global_medicine_id) {
        this.global_medicine_id = global_medicine_id;
    }

    public String getGlobal_brand_name() {
        return global_brand_name;
    }

    public void setGlobal_brand_name(String global_brand_name) {
        this.global_brand_name = global_brand_name;
    }

    public String getGlobal_generic_name() {
        return global_generic_name;
    }

    public void setGlobal_generic_name(String global_generic_name) {
        this.global_generic_name = global_generic_name;
    }

    public String getMed_cat_desc() {
        return med_cat_desc;
    }

    public void setMed_cat_desc(String med_cat_desc) {
        this.med_cat_desc = med_cat_desc;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}


