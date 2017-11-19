package com.departmental.store.api.product.controller.response;

public class ProductResponse {

    private String name;
    private float price;
    private int quantity;

    public ProductResponse(String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
