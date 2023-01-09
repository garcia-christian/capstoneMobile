package com.example.heremiStartup;

public class modelIndivProduct {
    private int med_id;
    private int global_med_id;
    private String global_brand_name;
    private String global_generic_name;
    private String med_cat_desc;
    private String class_desc;
    private String pharmacy_name;
    private int total;
    private String image;
    private Double med_price;
    private Double pharmacy_lat;
    private Double pharmacy_lng;
    private String distance;
    private Double distancems;

    public modelIndivProduct() {
    }

    public modelIndivProduct(int med_id, int global_med_id, String global_brand_name, String global_generic_name, String med_cat_desc, String class_desc, String pharmacy_name, int total, String image, Double med_price, Double pharmacy_lat, Double pharmacy_lng, String distance, Double distancems) {
        this.med_id = med_id;
        this.global_med_id = global_med_id;
        this.global_brand_name = global_brand_name;
        this.global_generic_name = global_generic_name;
        this.med_cat_desc = med_cat_desc;
        this.class_desc = class_desc;
        this.pharmacy_name = pharmacy_name;
        this.total = total;
        this.image = image;
        this.med_price = med_price;
        this.pharmacy_lat = pharmacy_lat;
        this.pharmacy_lng = pharmacy_lng;
        this.distance = distance;
        this.distancems = distancems;
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

    public String getClass_desc() {
        return class_desc;
    }

    public void setClass_desc(String class_desc) {
        this.class_desc = class_desc;
    }

    public String getPharmacy_name() {
        return pharmacy_name;
    }

    public void setPharmacy_name(String pharmacy_name) {
        this.pharmacy_name = pharmacy_name;
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

    public Double getMed_price() {
        return med_price;
    }

    public void setMed_price(Double med_price) {
        this.med_price = med_price;
    }

    public Double getPharmacy_lat() {
        return pharmacy_lat;
    }

    public void setPharmacy_lat(Double pharmacy_lat) {
        this.pharmacy_lat = pharmacy_lat;
    }

    public Double getPharmacy_lng() {
        return pharmacy_lng;
    }

    public void setPharmacy_lng(Double pharmacy_lng) {
        this.pharmacy_lng = pharmacy_lng;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Double getDistancems() {
        return distancems;
    }

    public void setDistancems(Double distancems) {
        this.distancems = distancems;
    }
}


