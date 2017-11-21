package com.departmental.store.api.sale.controller.response;

import java.util.List;

public class SaleResponse {

    private String transactionId;
    private String transactionDate;
    private double salePrice;
    private List<CartItemResponse> items;

    public SaleResponse(String transactionId, String transactionDate, double salePrice, List<CartItemResponse> items) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.salePrice = salePrice;
        this.items = items;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }

}
