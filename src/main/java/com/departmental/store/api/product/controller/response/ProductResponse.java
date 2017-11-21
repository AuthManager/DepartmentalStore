package com.departmental.store.api.product.controller.response;

public class ProductResponse {

    private String id;
    private String name;
    private float price;
    private int quantity;

    public ProductResponse(String id, String name, float price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
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
