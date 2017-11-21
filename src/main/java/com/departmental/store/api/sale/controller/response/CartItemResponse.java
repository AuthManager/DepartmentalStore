package com.departmental.store.api.sale.controller.response;

public class CartItemResponse {

    private String productId;
    private String productName;
    private int soldQuantity;
    private float unitPrice;

    public CartItemResponse(String productId, String productName,float unitPrice, int soldQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.soldQuantity = soldQuantity;
        this.unitPrice = unitPrice;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

}
