package com.example.heremiStartup;

public class modelOrdersItems {
    private Integer order_id, med_id, quantity;

    public modelOrdersItems() {
    }

    public modelOrdersItems(Integer order_id, Integer med_id, Integer quantity) {
        this.order_id = order_id;
        this.med_id = med_id;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
