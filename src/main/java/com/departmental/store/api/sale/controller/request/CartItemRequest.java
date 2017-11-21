package com.departmental.store.api.sale.controller.request;

public class CartItemRequest {

    private String productId;
    private int sellQuantity;

    public String getProductId() {
        return productId;
    }

    public int getSellQuantity() {
        return sellQuantity;
    }
}
