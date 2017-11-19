package com.departmental.store.api.product.controller.request;

public class ProductCreateRequest {

    private String name;
    private long price;
    private int quantity;

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
