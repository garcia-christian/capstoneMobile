package com.example.heremiStartup;

public class modelOrders {
    private Integer order_id, customer_id ,pharmacy_id,order_status,order_sales_id,discount_type;
    private Double order_totalprice, discount_cost, subtotal;
    private String pharmacy_name;
    public modelOrders() {
    }

    public modelOrders(Integer order_id, Integer customer_id, Integer pharmacy_id, Integer order_status, Integer order_sales_id, Double order_totalprice) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.pharmacy_id = pharmacy_id;
        this.order_status = order_status;
        this.order_sales_id = order_sales_id;
        this.order_totalprice = order_totalprice;
    }

    public modelOrders(Integer customer_id, Integer pharmacy_id, Integer order_status, Integer order_sales_id, Double order_totalprice, Double subtotal, Double discount_cost, Integer discount_type) {
        this.customer_id = customer_id;
        this.pharmacy_id = pharmacy_id;
        this.order_status = order_status;
        this.order_sales_id = order_sales_id;
        this.order_totalprice = order_totalprice;
        this.discount_cost = discount_cost;
        this.subtotal = subtotal;
        this.discount_type = discount_type;
    }

    public modelOrders(Integer order_id, Integer customer_id, Integer pharmacy_id, Integer order_status, Integer order_sales_id, Integer discount_type, Double order_totalprice, Double discount_cost, Double subtotal, String pharmacy_name) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.pharmacy_id = pharmacy_id;
        this.order_status = order_status;
        this.order_sales_id = order_sales_id;
        this.discount_type = discount_type;
        this.order_totalprice = order_totalprice;
        this.discount_cost = discount_cost;
        this.subtotal = subtotal;
        this.pharmacy_name = pharmacy_name;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public Integer getPharmacy_id() {
        return pharmacy_id;
    }

    public void setPharmacy_id(Integer pharmacy_id) {
        this.pharmacy_id = pharmacy_id;
    }

    public Integer getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Integer order_status) {
        this.order_status = order_status;
    }

    public Integer getOrder_sales_id() {
        return order_sales_id;
    }

    public void setOrder_sales_id(Integer order_sales_id) {
        this.order_sales_id = order_sales_id;
    }

    public Integer getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(Integer discount_type) {
        this.discount_type = discount_type;
    }

    public Double getOrder_totalprice() {
        return order_totalprice;
    }

    public void setOrder_totalprice(Double order_totalprice) {
        this.order_totalprice = order_totalprice;
    }

    public Double getDiscount_cost() {
        return discount_cost;
    }

    public void setDiscount_cost(Double discount_cost) {
        this.discount_cost = discount_cost;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getPharmacy_name() {
        return pharmacy_name;
    }

    public void setPharmacy_name(String pharmacy_name) {
        this.pharmacy_name = pharmacy_name;
    }
}
