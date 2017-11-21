package com.departmental.store.api.sale.controller.response;

public class SaleEntityResponse {

    private int soldQuantity;
    private float totalPrice;

    public SaleEntityResponse(int soldQuantity, float totalPrice) {
        this.soldQuantity = soldQuantity;
        this.totalPrice = totalPrice;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

}
