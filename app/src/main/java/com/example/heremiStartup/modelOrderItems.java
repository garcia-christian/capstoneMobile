package com.example.heremiStartup;

public class modelOrderItems {
    private Integer  order_id, med_id, med_price, quantity;
    private String global_brand_name, global_generic_name;

    public modelOrderItems() {
    }

    public modelOrderItems(Integer order_id, Integer med_id, Integer med_price, Integer quantity, String global_brand_name, String global_generic_name) {
        this.order_id = order_id;
        this.med_id = med_id;
        this.med_price = med_price;
        this.quantity = quantity;
        this.global_brand_name = global_brand_name;
        this.global_generic_name = global_generic_name;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getMed_id() {
        return med_id;
    }

    public void setMed_id(Integer med_id) {
        this.med_id = med_id;
    }

    public Integer getMed_price() {
        return med_price;
    }

    public void setMed_price(Integer med_price) {
        this.med_price = med_price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
}
