package com.departmental.store.api.sale.controller.response;

import com.departmental.store.api.product.repository.entity.Product;

public class CartItemResponse {

    private String productId;
    private String productName;
    private int soldQuantity;
    private float unitPrice;

    public CartItemResponse(Product product, int soldQuantity) {
        this.productId = product.getId().toString();
        this.productName = product.getName();
        this.soldQuantity = soldQuantity;
        this.unitPrice = product.getPrice();
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
