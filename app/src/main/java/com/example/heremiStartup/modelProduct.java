package com.example.heremiStartup;

public class modelProduct {

    private int global_med_id;
    private String global_brand_name;
    private String global_generic_name;
    private String med_cat_desc;
    private String class_desc;
    private int total;
    private String image;
    private double min;
    private double max;

    public modelProduct() {
    }

    public modelProduct(int global_med_id, String global_brand_name, String global_generic_name, String med_cat_desc, String class_desc, int total, String image, double min, double max) {
        this.global_med_id = global_med_id;
        this.global_brand_name = global_brand_name;
        this.global_generic_name = global_generic_name;
        this.med_cat_desc = med_cat_desc;
        this.class_desc = class_desc;
        this.total = total;
        this.image = image;
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return "modelProduct{" +
                "global_med_id=" + global_med_id +
                ", global_brand_name='" + global_brand_name + '\'' +
                ", global_generic_name='" + global_generic_name + '\'' +
                ", med_cat_desc='" + med_cat_desc + '\'' +
                ", class_desc='" + class_desc + '\'' +
                ", total=" + total +
                ", image='" + image + '\'' +
                ", min=" + min +
                ", max=" + max +
                '}';
    }

    public int getGlobal_med_id() {
        return global_med_id;
    }

    public void setGlobal_med_id(int global_med_id) {
        this.global_med_id = global_med_id;
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

    public String getClass_desc() {
        return class_desc;
    }

    public void setClass_desc(String class_desc) {
        this.class_desc = class_desc;
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

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}


