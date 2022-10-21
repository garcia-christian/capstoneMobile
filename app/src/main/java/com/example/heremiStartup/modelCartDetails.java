package com.example.heremiStartup;

public class modelCartDetails {
        private int  cart_med_id, cart_pharmacy_id, userID, med_qty, med_price;
        private String  global_brand_name, global_generic_name,cart_qty, med_cat_desc, class_desc, image;

    public modelCartDetails() {
    }

    @Override
    public String toString() {
        return "modelCartDetails{" +
                "cart_med_id=" + cart_med_id +
                ", cart_pharmacy_id=" + cart_pharmacy_id +
                ", userID=" + userID +
                ", med_qty=" + med_qty +
                ", med_price=" + med_price +
                ", global_brand_name='" + global_brand_name + '\'' +
                ", global_generic_name='" + global_generic_name + '\'' +
                ", cart_qty='" + cart_qty + '\'' +
                ", med_cat_desc='" + med_cat_desc + '\'' +
                ", class_desc='" + class_desc + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public modelCartDetails(int cart_med_id, int cart_pharmacy_id, int userID, int med_qty, int med_price, String global_brand_name, String global_generic_name, String cart_qty, String med_cat_desc, String class_desc, String image) {
        this.cart_med_id = cart_med_id;
        this.cart_pharmacy_id = cart_pharmacy_id;
        this.userID = userID;
        this.med_qty = med_qty;
        this.med_price = med_price;
        this.global_brand_name = global_brand_name;
        this.global_generic_name = global_generic_name;
        this.cart_qty = cart_qty;
        this.med_cat_desc = med_cat_desc;
        this.class_desc = class_desc;
        this.image = image;
    }

    public int getCart_med_id() {
        return cart_med_id;
    }

    public void setCart_med_id(int cart_med_id) {
        this.cart_med_id = cart_med_id;
    }

    public int getCart_pharmacy_id() {
        return cart_pharmacy_id;
    }

    public void setCart_pharmacy_id(int cart_pharmacy_id) {
        this.cart_pharmacy_id = cart_pharmacy_id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMed_qty() {
        return med_qty;
    }

    public void setMed_qty(int med_qty) {
        this.med_qty = med_qty;
    }

    public int getMed_price() {
        return med_price;
    }

    public void setMed_price(int med_price) {
        this.med_price = med_price;
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

    public String getCart_qty() {
        return cart_qty;
    }

    public void setCart_qty(String cart_qty) {
        this.cart_qty = cart_qty;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
