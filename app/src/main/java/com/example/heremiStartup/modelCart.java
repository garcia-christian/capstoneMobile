package com.example.heremiStartup;

public class modelCart {
    private int cart_med_id, cart_global_med_id, cart_pharmacy_id, cart_quantity, userID;

    public modelCart() {
    }

    public modelCart(int cart_med_id, int cart_global_med_id, int cart_pharmacy_id, int cart_quantity, int userID) {
        this.cart_med_id = cart_med_id;
        this.cart_global_med_id = cart_global_med_id;
        this.cart_pharmacy_id = cart_pharmacy_id;
        this.cart_quantity = cart_quantity;
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "modelCart{" +
                "cart_med_id=" + cart_med_id +
                ", cart_global_med_id=" + cart_global_med_id +
                ", cart_pharmacy_id=" + cart_pharmacy_id +
                ", cart_quantity=" + cart_quantity +
                ", userID=" + userID +
                '}';
    }

    public int getCart_med_id() {
        return cart_med_id;
    }

    public void setCart_med_id(int cart_med_id) {
        this.cart_med_id = cart_med_id;
    }

    public int getCart_global_med_id() {
        return cart_global_med_id;
    }

    public void setCart_global_med_id(int cart_global_med_id) {
        this.cart_global_med_id = cart_global_med_id;
    }

    public int getCart_pharmacy_id() {
        return cart_pharmacy_id;
    }

    public void setCart_pharmacy_id(int cart_pharmacy_id) {
        this.cart_pharmacy_id = cart_pharmacy_id;
    }

    public int getCart_quantity() {
        return cart_quantity;
    }

    public void setCart_quantity(int cart_quantity) {
        this.cart_quantity = cart_quantity;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
