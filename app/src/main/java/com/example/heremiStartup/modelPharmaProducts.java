package com.example.heremiStartup;

public class modelPharmaProducts {
    private int med_id;
    private int global_med_id;
    private String global_brand_name;
    private String global_generic_name;
    private String med_cat_desc;
    private String image;
    private double med_qty;
    private double med_price;

    public modelPharmaProducts() {
    }

    public modelPharmaProducts(int med_id, int global_med_id, String global_brand_name, String global_generic_name, String med_cat_desc, String image, double med_qty, double med_price) {
        this.med_id = med_id;
        this.global_med_id = global_med_id;
        this.global_brand_name = global_brand_name;
        this.global_generic_name = global_generic_name;
        this.med_cat_desc = med_cat_desc;
        this.image = image;
        this.med_qty = med_qty;
        this.med_price = med_price;
    }

    @Override
    public String toString() {
        return "modelPharmaProducts{" +
                "med_id=" + med_id +
                ", global_med_id=" + global_med_id +
                ", global_brand_name='" + global_brand_name + '\'' +
                ", global_generic_name='" + global_generic_name + '\'' +
                ", med_cat_desc='" + med_cat_desc + '\'' +
                ", image='" + image + '\'' +
                ", med_qty=" + med_qty +
                ", med_price=" + med_price +
                '}';
    }

    public int getMed_id() {
        return med_id;
    }

    public void setMed_id(int med_id) {
        this.med_id = med_id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getMed_qty() {
        return med_qty;
    }

    public void setMed_qty(double med_qty) {
        this.med_qty = med_qty;
    }

    public double getMed_price() {
        return med_price;
    }

    public void setMed_price(double med_price) {
        this.med_price = med_price;
    }
}
