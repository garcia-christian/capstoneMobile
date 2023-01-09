package com.example.heremiStartup;

public class modelDiscount {
   private Integer discount_id;
   private String discount_desc, discount_cost, pharmacy_id;

    public modelDiscount() {
    }

    public modelDiscount(Integer discount_id, String discount_desc, String discount_cost, String pharmacy_id) {
        this.discount_id = discount_id;
        this.discount_desc = discount_desc;
        this.discount_cost = discount_cost;
        this.pharmacy_id = pharmacy_id;
    }

    public Integer getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(Integer discount_id) {
        this.discount_id = discount_id;
    }

    public String getDiscount_desc() {
        return discount_desc;
    }

    public void setDiscount_desc(String discount_desc) {
        this.discount_desc = discount_desc;
    }

    public String getDiscount_cost() {
        return discount_cost;
    }

    public void setDiscount_cost(String discount_cost) {
        this.discount_cost = discount_cost;
    }

    public String getPharmacy_id() {
        return pharmacy_id;
    }

    public void setPharmacy_id(String pharmacy_id) {
        this.pharmacy_id = pharmacy_id;
    }
}
